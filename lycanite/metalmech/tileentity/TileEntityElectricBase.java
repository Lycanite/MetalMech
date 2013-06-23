package lycanite.metalmech.tileentity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import universalelectricity.core.UniversalElectricity;
import universalelectricity.core.electricity.ElectricityNetwork;
import universalelectricity.core.electricity.ElectricityPack;
import universalelectricity.core.item.ElectricItemHelper;
import universalelectricity.core.item.IItemElectric;
import universalelectricity.core.vector.Vector3;
import universalelectricity.prefab.network.IPacketReceiver;
import lycanite.metalmech.Config;
import lycanite.metalmech.MetalMech;
import lycanite.metalmech.PacketHandler;
import lycanite.metalmech.RecipeManager;
import lycanite.metalmech.block.BlockMachineBasic;
import lycanite.metalmech.item.ItemElectric;
import lycanite.metalmech.machine.MachineInfo;
import lycanite.metalmech.machine.MachineInventory;
import lycanite.metalmech.machine.MachineManager;
import lycanite.metalmech.machine.MachineRecipes;

public class TileEntityElectricBase extends TileEntityElectric implements IInventory, ISidedInventory, IPacketReceiver {
	
	// Info:
	public MachineInfo machineInfo;
	protected boolean wasActive = false;
	
	// Base Stats:
	public static int baseProcessSpeed = 1;
	public static int baseFuelSpeed = 0;
	public static float baseFuelWarmup = 6f;
	public static double baseVoltage = MetalMech.mediumVoltage;
	public static double baseWattCost = 500d;
	public static double baseWattGeneration = 0d;
	public static double baseWattGenerationThreshhold = 0.1d;
	public static double baseJouleStorage = 0d;
	
	// Stats:
	public int processSpeed = baseProcessSpeed; // How quickly the machine processes an item. +Speed Circuit
	public int processTime = -1; // How long this machine has been processing an item for.
	public int fuelSpeed = baseFuelSpeed; // How quickly the machine burns through fuel. -Efficiency Circuit / +Speed Circuit
	public int fuelInit = 0; // The initial amount of fuel provided by an item.
	public int fuelTime = 0; // How long the fuel item has been burnt for.
	public float fuelWarmup = baseFuelWarmup; // How quickly the machine warms up for operation. +Efficiency Circuit
	
	
	// Inventory:
	public Map<String, MachineInventory> inventory;
	public Map<Integer, Object[]> inventoryLookup;
	
	
	// ==================================================
	//                 Machine Properties
	// ==================================================
	// Get Machine Type:
	public MachineInfo getInfo() {
		if(machineInfo == null)
			return MachineManager.getMachineInfo(getCategory(), getBlockMetadata());
		else
			return machineInfo;
	}
	
	
	// Get Machine Category:
	public String getCategory() {
		return "Machine";
	}
	
	
	// Get Machine Metadata:
	public String getType() {
		return getInfo().name;
	}
	
	
	// Get Machine Metadata:
	public int getMetadata() {
		return MachineManager.getMetadata(getType());
	}
	
	
	// Get Process Time Target:
	public int getProcessTimeTarget() {
		return getInfo().processTimeInit;
	}
	
	
	// ==================================================
	//                 Machine Behaviour
	// ==================================================
	// ========== Update Tile Entity ==========
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		// Charge from Electric Item:
		if(wattCost > 0 && (processSpeed <= 0 || (processSpeed > 0 && canProcess())))
			drainBattery(getStackInSlot("Battery"));
		
		// Discharge to Electric Item:
		if(wattGeneration > 0 && inventory.containsKey("Charge"))
			chargeItem(getStackInSlot("Charge"));
		
		// Run Main Activities:
		if(!isDisabled())
			if(processSpeed > 0)
				runProcess();
			else if(wattCost > 0)
				runCharge();
			if(wattGeneration > 0)
				runGenerate();
        
		// Update Network:
        if(!this.worldObj.isRemote) {
	    	if((this.ticks % 3L == 0L)) { //&& ((this.playersUsing > 0) || powerChanged())) {
	    		PacketHandler.sendPacketToClients(getDescriptionPacket(), this.worldObj, new Vector3(this), 12);
	    	}
	    	else if(wasActive != isActive()) {
	    		PacketHandler.sendPacketToClients(getDescriptionPacket(), this.worldObj, new Vector3(this), 12);
	    		wasActive = isActive();
	    	}
        }
    }
	
	
	// Run Process:
	public void runProcess() {
		if(!canProcess()) processTime = 0;
		else if(sufficientWatts()) {
            if(processTime == 0) {
            	processTime = getProcessTimeTarget();
            }
            else if(processTime > 0) {
            	processTime -= processSpeed;
            	if(processTime < 1) {
            		processItem();
            		processTime = 0;
            	}
            }
            else processTime = 0;
	        wattsReceived = Math.max(wattsReceived - (wattCost / 4), 0);
	    }
    }
	
	
	// Run Charge:
	public void runCharge() {
		if(!needsCharge())
			wattsReceived = 0;
    }
	
	
	// Run Generate:
	public void runGenerate() {
		if(fuelSpeed > 0) {
			if(fuelTime > 0) {
				fuelTime -= fuelSpeed;
				if(hasOutputConnection() || checkChargeSlot())
					wattsGenerated = Math.min(wattsGenerated + Math.min((wattsGenerated * 0.005 + fuelWarmup), 5), wattGeneration);
			}
			if(getStackInSlot("Fuel") != null && (hasOutputConnection() || checkChargeSlot())) {
				if(hasFuel() && fuelTime <= 0) {
					fuelInit = getFuelTime();
					fuelTime = fuelInit;
					decrStackSize("Fuel", 1);
				}
			}
			if((!hasOutputConnection() && !checkChargeSlot()) || fuelTime <= 0)
				wattsGenerated = Math.max(wattsGenerated - 8, 0);
		}
		else if(!this.worldObj.isRemote)
			if(hasOutputConnection())
				wattsGenerated = Math.min(requestedWatts, Math.min(getAmpsStored(), wattGeneration));
			else
				wattsGenerated = 0;
		updateProducing();
    }
	
	
	// ========== Check Charge Slot ==========
	public boolean checkChargeSlot() {
		if(getStackInSlot("Charge") != null)
			if(getStackInSlot("Charge").getItem() instanceof ItemElectric)
				return ((ItemElectric)getStackInSlot("Charge").getItem()).needsCharge();
		return false;
	}


	// ========== Usable ==========
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}
    

	// ========== Open ==========
	@Override
	public void openChest() {
		if(!this.worldObj.isRemote)
			PacketHandler.sendPacketToClients(getDescriptionPacket(), this.worldObj, new Vector3(this), 15.0D);
		this.playersUsing++;
	}
	
	
	// ========== Close ==========
	@Override
	public void closeChest() {
		this.playersUsing--;
	}
	
	
	// ========== Is Active ==========
	public boolean isActive() {
        if(processSpeed > 0)
        	return processTime > 0 && canProcess() && !isDisabled();
        else if(wattGeneration > 0)
        	return wattsGenerated > 0 && !isDisabled();
        else
        	return false;
    }
	
	
	// ========== Is Receiving ==========
	public boolean isReceiving() {
        if(jouleStorage > 0)
        	return prevAmpsStored < ampsStored && !isDisabled();
        else
        	return false;
    }
	
	
	// ==================================================
	//                     Electricity
	// ==================================================
	//========== Get Request ==========
	@Override
	public ElectricityPack getRequest() {
		if(wattCost > 0)
		    if(processSpeed > 0 && canProcess())
		    	return new ElectricityPack(wattCost, getVoltage() / getVoltage());
		    else if(jouleStorage > 0)
				return new ElectricityPack((jouleStorage - getAmpsStored()) / getVoltage(), getVoltage());
	    return new ElectricityPack();
	}
	
	
	// ==================================================
	//                       GUI
	// ==================================================
	// ========== Get Process Progress Scaled ==========
	@SideOnly(Side.CLIENT)
	public int getScaledProcessProgress(int scale) {
		return processTime * scale / getProcessTimeTarget();
    }
	
	
	// ========== Get Fuel Time Scaled ==========
	@SideOnly(Side.CLIENT)
	public int getScaledFuelTime(int scale) {
		if(fuelInit != 0)
			return fuelTime * scale / fuelInit;
		else if(fuelTime > 0)
			return scale;
		else
			return 0;
    }
	
	
	// ==================================================
	//                    Processing
	// ==================================================
	// ========== Can Process ==========
    protected boolean canProcess() {
    	if(getStackInSlot("Input") == null) return false;
    	if(getProcessedItem(getStackInSlot("Input")) == null) return false;
    	if(getStackInSlot("Output") != null) {
    		if(!getStackInSlot("Output").isItemEqual(getProcessedItem(getStackInSlot("Input")))) return false;
    		if(getStackInSlot("Output").stackSize + 1 > 64) return false;
    	}
        return true;
    }
    
    
    // ========== Process Item ==========
    public void processItem() {
        if(canProcess()) {
            ItemStack processedItem = getProcessedItem(getStackInSlot("Input"));
            if(processedItem == null) { return; }
            
            if(getStackInSlot("Output") == null) {
            	setInventorySlotContents("Output", processedItem.copy());
            }
            else if(getStackInSlot("Output").isItemEqual(processedItem)) {
            	getStackInSlot("Output").stackSize += processedItem.stackSize;
            }
            
            getStackInSlot("Input").stackSize--;
            if(getStackInSlot("Input").stackSize <= 0) {
            	setInventorySlotContents("Input", null);
            }
        }
    }
    
    
    // ========== Get Processed Item ==========
    public ItemStack getProcessedItem(ItemStack itemStack) {
    	if(getType() == "Furnace")
    		return FurnaceRecipes.smelting().getSmeltingResult(itemStack);
    	if(getType() == "Crusher")
    		return MetalMech.hooks.metallurgyGetCrushingResult(itemStack);
    	return MachineRecipes.instance().getResult(getType(), itemStack);
    }
	
	
	// ==================================================
	//                     Inventory
	// ==================================================
    // ========== Set inventory Lookup ==========
    public void setInventoryLookup() {
	    Map<Integer, Object[]> newInventoryLookup = new HashMap<Integer, Object[]>();
		for(MachineInventory inventoryValue : inventory.values())
			for(int i = 0; i < inventoryValue.getSize(); i++) {
				newInventoryLookup.put(inventoryValue.slotIndexStart + i, new Object[] {inventoryValue.name, i});
			}
		inventoryLookup = newInventoryLookup;
    }
    
	// ========== Get Inventory Name =========
	public String getInvName() {
		return getInfo().title;
	}
	
	// ========== Lookup Inventory Name ==========
	public String lookupSlotName(int slotLookupID) {
		return (String)inventoryLookup.get(slotLookupID)[0];
	}
	
	// ========== Lookup Inventory Slot ==========
	public int lookupSlotIndex(int slotLookupID) {
		return (Integer)inventoryLookup.get(slotLookupID)[1];
	}
	
	// ========== Get Inventory Item ==========
	public ItemStack getStackInSlot(String slotName, int slotIndex) {
		if(inventory.get(slotName) != null)
			return inventory.get(slotName).items[slotIndex];
		return null;
	}
	public ItemStack getStackInSlot(String slotName) {
		return getStackInSlot(slotName, 0);
	}
	@Override
	public ItemStack getStackInSlot(int slotIndexID) {
		return getStackInSlot(lookupSlotName(slotIndexID), lookupSlotIndex(slotIndexID));
	}
	
	// ========== Set Inventory Item ==========
	public void setInventorySlotContents(String slotName, int slotIndex, ItemStack itemStack) {
		if(inventory.get(slotName) == null) return;
		inventory.get(slotName).items[slotIndex] = itemStack;
		if(itemStack != null && itemStack.stackSize > getInventoryStackLimit()) {
			itemStack.stackSize = getInventoryStackLimit();
		}
	}
	public void setInventorySlotContents(String slotName, ItemStack itemStack) {
		setInventorySlotContents(slotName, 0, itemStack);
	}
	@Override
	public void setInventorySlotContents(int slotLookupID, ItemStack itemStack) {
		setInventorySlotContents(lookupSlotName(slotLookupID), lookupSlotIndex(slotLookupID), itemStack);
	}
	
	
	// ========== Get Inventory Size ==========
	@Override
	public int getSizeInventory() {
		int inventorySize = 0;
		for(MachineInventory inventoryValue : inventory.values()) {
			inventorySize += inventoryValue.getSize();
		}
		return inventorySize;
	}
	
	
	// ========== Get Inventory Slot Stack Limit ==========
	public int getInventoryStackLimit() {
		return 64;
	}
	
	
	// ========== Decrease Inventory Item Size ==========
	public ItemStack decrStackSize(String slotName, int slotIndex, int amount) {
		ItemStack itemStack = getStackInSlot(slotIndex);
		
		if(getStackInSlot(slotName, slotIndex) != null) {
            ItemStack returnStack;

            if(getStackInSlot(slotName, slotIndex).stackSize <= amount) {
            	returnStack = getStackInSlot(slotName, slotIndex);
            	setInventorySlotContents(slotName, slotIndex, null);
                return returnStack;
            }
            else {
            	returnStack = getStackInSlot(slotName, slotIndex).splitStack(amount);
                if(getStackInSlot(slotName, slotIndex).stackSize == 0 )
                	setInventorySlotContents(slotName, slotIndex, null);
                return returnStack;
            }
        }
        return null;
	}
	
	public ItemStack decrStackSize(String slotName, int amount) {
		return decrStackSize(slotName, 0, amount);
	}
	
	@Override
	public ItemStack decrStackSize(int slotLookupID, int amount) {
		return decrStackSize(lookupSlotName(slotLookupID), lookupSlotIndex(slotLookupID), amount);
	}
	
	
	// ========== Get Stack In Slot On Closing ==========
	public ItemStack getStackInSlotOnClosing(String slotName, int slotIndex) {
		ItemStack itemStack = getStackInSlot(slotName, slotIndex);
		if(itemStack != null) {
			setInventorySlotContents(slotName, slotIndex, null);
			return itemStack;
		}
		return itemStack;
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int slotLookupID) {
		return getStackInSlotOnClosing(lookupSlotName(slotLookupID), lookupSlotIndex(slotLookupID));
	}
    
    
    // ========== Is Stack Valid for Slot ==========
    public boolean isStackValidForSlot(String slotName, ItemStack itemStack) {
    	if(getProcessedItem(itemStack) != null)
    		return true;
    	return inventory.get(slotName).stackValid(itemStack);
    }
	
	@Override
	public boolean isStackValidForSlot(int slotLookupID, ItemStack itemStack) {
		return isStackValidForSlot(lookupSlotName(slotLookupID), itemStack);
	}
    
    
    // ========== Has Fuel Item ==========
    public boolean hasFuel() {
    	return getFuelTime() > 0;
    }
    
    
    // ========== Get Fuel Time ==========
    public int getFuelTime() {
    	return RecipeManager.getItemFuel(getStackInSlot("Fuel"));
    }
	
	
	// ========== Get Input Sides ========== TODO Add support for resizing.
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		switch(side) {
			case 0: // Bottom
				if(inventory.get("Battery") != null && inventory.get("Fuel") != null)
					return new int[] { inventory.get("Battery").slotIndexStart, inventory.get("Fuel").slotIndexStart };
				else if(inventory.get("Battery") != null)
					return new int[] { inventory.get("Battery").slotIndexStart };
				else if(inventory.get("Fuel") != null)
					return new int[] { inventory.get("Fuel").slotIndexStart };
			case 1: // Top
				if(inventory.get("Output") != null)
					return new int[] { inventory.get("Output").slotIndexStart };
			default: // Sides
				if(inventory.get("Input") != null)
					return new int[] { inventory.get("Input").slotIndexStart };
		}
		return new int[0];
	}
	
	
	// ========== Check if Input Item is Valid ==========
	@Override
	public boolean canInsertItem(int slotID, ItemStack itemstack, int j) {
		return isStackValidForSlot(slotID, itemstack);
	}
	
	
	// ========== Check If Slot Can Output ==========
	@Override
	public boolean canExtractItem(int slotID, ItemStack itemstack, int j) {
		return slotID == 2;
	}
	
	
	// ==================================================
	//              Network Packets and NBT
	// ==================================================
	// ========== Packet Setup ==========
	@Override
	public Packet getDescriptionPacket() {
		List<Object> packetData = new ArrayList<Object>();
		packetData.add(Short.valueOf(facing));
		packetData.add(disabledTicks);
		if(processSpeed > 0)
			packetData.add(Integer.valueOf(processTime));
		if(wattCost > 0)
			packetData.add((int)getVoltsReceived());
		if(fuelSpeed > 0) {
			packetData.add(Integer.valueOf(fuelTime));
			packetData.add(Integer.valueOf(fuelInit));
		}
		if(wattGeneration > 0)
			packetData.add((int)getWattsGenerated());
		if(jouleStorage > 0)
			packetData.add((int)getAmpsStored());
		
		return PacketHandler.createPacket("MetalMech", this, packetData.toArray());
	}
	
	
	// ========== Handle Packet ==========
	public void handlePacketData(INetworkManager network, int type, Packet250CustomPayload packet, EntityPlayer player, ByteArrayDataInput dataStream) {
		try {
			setFacing(dataStream.readShort());
			disabledTicks = dataStream.readInt();
			if(processSpeed > 0)
				processTime = dataStream.readInt();
			if(wattCost > 0)
				setVoltsReceived(dataStream.readInt());
			if(fuelSpeed > 0) {
				fuelTime = dataStream.readInt();
				fuelInit = dataStream.readInt();
			}
			if(wattGeneration > 0)
				setWattsGenerated(dataStream.readInt());
			if(jouleStorage > 0)
				setAmpsStored(dataStream.readInt());
			BlockMachineBasic.updateBlockState(worldObj, xCoord, yCoord, zCoord);
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
		catch (Exception e) {
			System.out.println("[MetalMech] Problem handling machine update packet for " + getType());
			e.printStackTrace();
		}
	}
	
	
	// ========== Read NBT ==========
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		facing = tagCompound.getShort("facing");
		processTime = tagCompound.getInteger("processTime");
		fuelTime = tagCompound.getInteger("fuelTime");
		fuelInit = tagCompound.getInteger("fuelInit");
		setWattsGenerated(tagCompound.getDouble("wattsGenerated"));
		setAmpsStored(tagCompound.getDouble("ampsStored"));
		
		for(String inventoryKey : inventory.keySet())
			inventory.get(inventoryKey).readNBTTagList(tagCompound.getTagList("Items" + inventoryKey));
	}
	
	
	// ========== Write NBT ==========
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
	    par1NBTTagCompound.setShort("facing", this.facing);
		par1NBTTagCompound.setInteger("processTime", processTime);
		par1NBTTagCompound.setInteger("fuelTime", fuelTime);
		par1NBTTagCompound.setInteger("fuelInit", fuelInit);
		par1NBTTagCompound.setDouble("wattsGenerated", getWattsGenerated());
		par1NBTTagCompound.setDouble("ampsStored", getAmpsStored());
	    
	    for(String inventoryKey : inventory.keySet())
	    	par1NBTTagCompound.setTag("Items" + inventoryKey, inventory.get(inventoryKey).getNBTTagList());
	}
}
