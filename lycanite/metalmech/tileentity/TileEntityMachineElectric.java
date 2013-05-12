package lycanite.metalmech.tileentity;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
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
import net.minecraftforge.common.ForgeDirection;
import universalelectricity.core.UniversalElectricity;
import universalelectricity.core.electricity.ElectricityNetwork;
import universalelectricity.core.electricity.ElectricityPack;
import universalelectricity.core.item.ElectricItemHelper;
import universalelectricity.core.item.IItemElectric;
import universalelectricity.core.vector.Vector3;
import universalelectricity.prefab.network.IPacketReceiver;
import lycanite.metalmech.Config;
import lycanite.metalmech.MachineManager;
import lycanite.metalmech.MachineRecipes;
import lycanite.metalmech.MetalMech;
import lycanite.metalmech.PacketHandler;
import lycanite.metalmech.block.BlockMachineBasic;

public class TileEntityMachineElectric extends TileEntityElectric implements IInventory, ISidedInventory, IPacketReceiver {
	
	// Info:
	protected ItemStack[] inventory;
	public int processTime = 0; // How long the input item has been processed for.
	public int timeBase = -1; // How many ticks this machine takes to process an item.
	protected boolean wasActive = false;
	
	
	// Constructor:
	public TileEntityMachineElectric() {
		this.inventory = new ItemStack[3];
	}
	
	
	// Check If Player Can Use (Crashes if in parent):
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}
	
	
	// ==================================================
	//                 Machine Properties
	// ==================================================
	// Get Machine Type:
	public String getType() {
		return MachineManager.getType(getBlockMetadata(), "Electric");
	}
	
	
	// Set Time Base:
	public void setTimeBase() {
		timeBase = MachineManager.getSpeed(getType());
	}
	
	
	// Get Inventory Name:
	public String getInvName() {
		return MachineManager.getTitle(getType());
	}
	
	
	// Get Inventory Size:
	public int getSizeInventory() {
		return this.inventory.length;
	}
	
	// Get Inventory Slot Stack Limit:
	public int getInventoryStackLimit() {
		return 64;
	}
	
	
	// Get Stack In Slot:
	public ItemStack getStackInSlot(int slotIndex) {
		return this.inventory[slotIndex];
	}
	
	
	// Decrease Stack Size:
	public ItemStack decrStackSize(int slotIndex, int amount) {
		ItemStack itemStack = getStackInSlot(slotIndex);
		
		if (this.inventory[slotIndex] != null) {
            ItemStack var3;

            if (this.inventory[slotIndex].stackSize <= amount) {
                var3 = this.inventory[slotIndex];
                this.inventory[slotIndex] = null;
                return var3;
            }
            else {
                var3 = this.inventory[slotIndex].splitStack(amount);

                if (this.inventory[slotIndex].stackSize == 0)
                {
                    this.inventory[slotIndex] = null;
                }

                return var3;
            }
        }
        return null;
	}
	
	
	// Get Stack In Slot On Closing:
	public ItemStack getStackInSlotOnClosing(int slotIndex) {
		ItemStack itemStack = getStackInSlot(slotIndex);
		
		if(itemStack != null) {
			this.inventory[slotIndex] = null;
			return itemStack;
		}
		
		return itemStack;
	}

	
	
	// Set Slot Contents:
	public void setInventorySlotContents(int slotIndex, ItemStack itemStack) {
		this.inventory[slotIndex] = itemStack;
		if(itemStack != null && itemStack.stackSize > getInventoryStackLimit()) {
			itemStack.stackSize = getInventoryStackLimit();
		}
	}
	
	
	// Get Start Inventory Side:
    public int getStartInventorySide(ForgeDirection side) {
        if (side == ForgeDirection.DOWN) return 1;
        if (side == ForgeDirection.UP) return 0;
        return 2;
    }
	
	
	// Get Size Inventory Side:
    public int getSizeInventorySide(ForgeDirection side) {
        return 1;
    }
    
    
    // Is Stack Valid for Slot
    @Override
    public boolean isStackValidForSlot(int slot, ItemStack itemStack) {
        switch(slot) {
        	case 0: // Input
        		return getProcessedItem(itemStack) != null;
        	case 1: // Power
        		return inventory[1] == null && itemStack.getItem() instanceof IItemElectric;
        	default: // Output
        		return false;
        }
    }
	
	
	//==================== Machine Activity ====================
	// Open:
	@Override
	public void openChest() {
		if(!this.worldObj.isRemote)
			PacketHandler.sendPacketToClients(getDescriptionPacket(), this.worldObj, new Vector3(this), 15.0D);
		this.playersUsing++;
	}
	
	
	// Close:
	@Override
	public void closeChest() {
		this.playersUsing--;
	}
	
	
	// Check If Active:
	public boolean isActive() {
        return this.processTime != 0 && canProcess() && !isDisabled();
    }
	
	
	// Get Process Progress Scaled:
	@SideOnly(Side.CLIENT)
	public int getScaledProcessProgress(int scale) {
		if(timeBase == -1) setTimeBase();
		return this.processTime * scale / this.timeBase;
    }
	
	
	// Check If Machine Can Process:
    protected boolean canProcess() {
    	if(this.inventory[0] == null) return false;
    	if(getProcessedItem(this.inventory[0]) == null) return false;
    	if(this.inventory[2] != null) {
    		if(!this.inventory[2].isItemEqual(getProcessedItem(this.inventory[0]))) return false;
    		if(this.inventory[2].stackSize + 1 > 64) return false;
    	}
        return true;
    }
    
    
    // Process Input:
    public void processItem() {
        if (this.canProcess()) {
            ItemStack processedItem = getProcessedItem(this.inventory[0]);
            if(processedItem == null) { return; }
            
            if (this.inventory[2] == null) {
                this.inventory[2] = processedItem.copy();
            }
            else if (this.inventory[2].isItemEqual(processedItem)) {
            	inventory[2].stackSize += processedItem.stackSize;
            }
            
            this.inventory[0].stackSize--;
            
            if (this.inventory[0].stackSize <= 0) {
                this.inventory[0] = null;
            }
        }
    }
    
    
    // Get Processed Item:
    public ItemStack getProcessedItem(ItemStack itemStack) {
    	if(getType() == "Crusher")
    		return MetalMech.hooks.metallurgyGetCrushingResult(itemStack);
    	return MachineRecipes.instance().getResult(getType(), itemStack);
    }
	
	
	// Update Tile Entity:
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		// Charge from Battery:
		wattsReceived += ElectricItemHelper.dechargeItem(this.inventory[1], wattsPerTick, this.getVoltage());
		
		// Process Items:
		if(!canProcess())
			this.processTime = 0;
		else {
	        if(this.wattsReceived >= wattsPerTick) {
	            if(this.processTime == 0) {
	            	if(timeBase == -1) setTimeBase();
	            	this.processTime = timeBase;
	            }
	            else if(this.processTime > 0) {
	            	this.processTime -= 1;
	
	            	if(this.processTime < 1) {
	            		processItem();
	            		this.processTime = 0;
	            	}
	            }
	            else {
	            	this.processTime = 0;
	            }
	        	
		        this.wattsReceived = Math.max(this.wattsReceived - (wattsPerTick / 4), 0);
		    }
		}
        
        if(!this.worldObj.isRemote) {
	    	if((this.ticks % 3L == 0L) && (this.playersUsing > 0)) {
	    		PacketHandler.sendPacketToClients(getDescriptionPacket(), this.worldObj, new Vector3(this), 12);
	    	}
	    	else if(wasActive != isActive()) {
	    		PacketHandler.sendPacketToClients(getDescriptionPacket(), this.worldObj, new Vector3(this), 12);
	    		wasActive = isActive();
	    	}
        }
    }
	
	
	// Get Electric Pack Request:
	public ElectricityPack getRequest() {
	    if(canProcess())
	    	return new ElectricityPack(wattsPerTick / getVoltage(), getVoltage());
	    
	    return new ElectricityPack();
	}
	
	
	// Packet Setup:
	@Override
	public Packet getDescriptionPacket() {
		return PacketHandler.createPacket("MetalMech", this, Short.valueOf(this.facing), Integer.valueOf(this.processTime), Integer.valueOf(this.disabledTicks));
	}
	
	
	// Handle Packet:
	public void handlePacketData(INetworkManager network, int type, Packet250CustomPayload packet, EntityPlayer player, ByteArrayDataInput dataStream) {
		try {
			this.facing = dataStream.readShort();
			this.processTime = dataStream.readInt();
			this.disabledTicks = dataStream.readInt();
			BlockMachineBasic.updateBlockState(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		}
		catch (Exception e) {
			System.out.println("[MetalMech] Problem handling machine update packet.");
			e.printStackTrace();
		}
	}
	
	
	// Read NBT:
	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		this.facing = par1NBTTagCompound.getShort("facing");
		this.processTime = par1NBTTagCompound.getInteger("processTime");
		NBTTagList var2 = par1NBTTagCompound.getTagList("Items");
		this.inventory = new ItemStack[getSizeInventory()];
		
		for (int var3 = 0; var3 < var2.tagCount(); var3++) {
			NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
			byte var5 = var4.getByte("Slot");
			
			if ((var5 >= 0) && (var5 < this.inventory.length)) {
				this.inventory[var5] = ItemStack.loadItemStackFromNBT(var4);
			}
	    }
	}
	
	
	// Write NBT:
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
	    par1NBTTagCompound.setShort("facing", this.facing);
	    par1NBTTagCompound.setInteger("processTime", this.processTime);
	    NBTTagList var2 = new NBTTagList();
	    
	    for (int var3 = 0; var3 < this.inventory.length; var3++) {
	    	if (this.inventory[var3] != null) {
	    		NBTTagCompound var4 = new NBTTagCompound();
	    		var4.setByte("Slot", (byte)var3);
	    		this.inventory[var3].writeToNBT(var4);
	    		var2.appendTag(var4);
	    	}
	    }
	    
	    par1NBTTagCompound.setTag("Items", var2);
	}
	
	
	// Get Input Sides:
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		switch(side) {
			case 0: // Bottom
				return new int[] { 2 };
			case 1: // Top
				return new int[] { 0, 1 };
			default:
				return new int[] { 0 };
		}
	}
	
	
	// Check if Input Item is Valid:
	@Override
	public boolean canInsertItem(int slotID, ItemStack itemstack, int j) {
		return this.isStackValidForSlot(slotID, itemstack);
	}
	
	
	// Check if Slot can Output:
	@Override
	public boolean canExtractItem(int slotID, ItemStack itemstack, int j) {
		return slotID == 2;
	}
}
