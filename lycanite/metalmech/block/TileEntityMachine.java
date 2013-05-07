package lycanite.metalmech.block;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import universalelectricity.core.vector.Vector3;
import universalelectricity.prefab.network.IPacketReceiver;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lycanite.metalmech.Config;
import lycanite.metalmech.MetalMech;
import lycanite.metalmech.PacketHandler;
import lycanite.metalmech.block.BlockMachineBasic;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
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
import net.minecraftforge.common.ISidedInventory;

public class TileEntityMachine extends TileEntity implements IInventory, ISidedInventory, IPacketReceiver {
	
	// Info:
	private String tileEntityName;
	protected ItemStack[] inventory;
	public short facing = 2;
	public int fuelTime = 0; // How long the current fuel item has powered the machine so far. (furnaceBurnTime)
	public int initFuelTime = 0; // How much time the fuel the current fuel item provided. (currentItemBurnTime)
	public int processTime = 0; // How long input item has been processed for. (furnaceCookTime)
	public int timeBase = 200; // How fast this machine is.
	public boolean wasActive;
	public int metadata; // Used for when in inventory only.
	protected int ticksSinceSync;
	
	// Machine Types:
	 public static enum MachineType {
		NULL (-1),
		FURNACE (0),
		CRUSHER (1);
		
		public int id;
		
		private MachineType(int i) {
			id = i;
		}
	}
		
	// Machine Ranks:
	public static Map<Integer, String> machineRank = new HashMap<Integer, String>();
	public static Map<String, Integer> machineRankMetadata = new HashMap<String, Integer>();
	public static void setMachineRanks() {
		machineRank.put(0, "Lead");
		machineRankMetadata.put("Lead", 0);
		machineRank.put(1, "Aluminium");
		machineRankMetadata.put("Aluminium", 1);
		machineRank.put(2, "Titanium");
		machineRankMetadata.put("Titanium", 2);
	}
	
	
	// Constructor:
	public TileEntityMachine() {
		this.inventory = new ItemStack[3];
	}
	
	
	// Set Metadata:
	public void setMetadata(int newMetadata) {
		setTimeBase(Config.machineSpeed.get(getRankFromMetadata(getRank())));
		this.metadata = newMetadata;
	}
	
	
	// Is Inventroy Name Localized:
	public boolean isInvNameLocalized() {
        return this.tileEntityName != null && this.tileEntityName.length() > 0;
    }
	
	
	// Set Facing:
	public void setFacing(int targetFacing) {
		this.facing = (short)targetFacing;
	}
	
	
	// Set Facing With Update:
	public void setFacingWithUpdate(int targetFacing) {
		this.facing = (short)targetFacing;
		PacketHandler.sendPacketToClients(getDescriptionPacket(), this.worldObj, new Vector3(this), 12.0D);
	}
	
	
	// Get Machine Type:
	public int getType() {
		return MachineType.FURNACE.id;
	}
	
	
	// Get Machine Rank:
	public int getRank() {
		if(this.worldObj != null) {
			return this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
		}
		return this.metadata;
	}
	
	// Machine Type/Rank Lookup:
	public static String getRankFromMetadata(int testMetadata) {
		return machineRank.get(testMetadata);
	}
	public static int getMetadataFromRank(String testRank) {
		return machineRankMetadata.get(testRank);
	}
	
	
	// Set Time Base:
	public void setTimeBase(int newTime) {
		this.timeBase = (int)20.0D * newTime;
	}
	
	
	// Get Inventory Name:
	@Override
	public String getInvName() {
		return MetalMech.machineBlockTitles[this.getBlockMetadata()];
	}
	
	
	// Get Inventory Size:
	@Override
	public int getSizeInventory() {
		return this.inventory.length;
	}
	
	// Get Inventory Slot Stack Limit:
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}
	
	
	// Get Stack In Slot:
	@Override
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
	@Override
    public int getStartInventorySide(ForgeDirection side) {
        if (side == ForgeDirection.DOWN) return 1;
        if (side == ForgeDirection.UP) return 0;
        return 2;
    }
	
	
	// Get Size Inventory Side:
    @Override
    public int getSizeInventorySide(ForgeDirection side) {
        return 1;
    }
    
    
    // Is Stack Valid for Slot
    @Override
    public boolean isStackValidForSlot(int slot, ItemStack itemStack) {
        switch(slot) {
        	case 0: // Input
        		return true;
        	case 1: // Power
        		return isItemFuel(itemStack);
        	default: // Output
        		return false;
        }
    }
	
	
	//==================== Machine Activity ====================
	// Check If Player Can Use:
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}
	
	
	// Open and Close:
	public void openChest() { return; }
	public void closeChest() { return; }
	
	
	// Check If Active:
	public boolean isActive() {
        return this.fuelTime > 0;
    }
	
	
	// Get Process Progress Scaled:
	@SideOnly(Side.CLIENT)
	public int getScaledProcessProgress(int scale) {
        return this.processTime * scale / this.timeBase;
    }
	
	
	// Get Process Progress Scaled:
	@SideOnly(Side.CLIENT)
	public int getScaledRemainingFuelTime(int scale) {
        if (this.initFuelTime == 0) {
            this.initFuelTime = this.timeBase;
        }
        return this.fuelTime * scale / this.initFuelTime;
    }
	
	
	// Check If Machine Can Process:
    protected boolean canProcess() {
        if (this.inventory[0] == null) {
            return false;
        }
        else {
            ItemStack processOutput = getProcessedItem();
            if (processOutput == null) return false;
            if (this.inventory[2] == null) return true;
            if (!this.inventory[2].isItemEqual(processOutput)) return false;
            int result = inventory[2].stackSize + processOutput.stackSize;
            return (result <= getInventoryStackLimit() && result <= processOutput.getMaxStackSize());
        }
    }
    
    
    // Process Input:
    public void processItem() {
        if (this.canProcess()) {
            ItemStack processedItem = getProcessedItem();
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
    public ItemStack getProcessedItem() {
    	return FurnaceRecipes.smelting().getSmeltingResult(this.inventory[0]);
    }
    
    
    // Get Item Fuel Time:
    public static int getItemFuelTime(ItemStack itemStack) {
        if (itemStack == null) {
            return 0;
        }
        else {
            int itemID = itemStack.getItem().itemID;
            Item item = itemStack.getItem();
            
            if (itemStack.getItem() instanceof ItemBlock && Block.blocksList[itemID] != null) {
                Block blockName = Block.blocksList[itemID];
                
                if (blockName == Block.woodSingleSlab) {
                    return 150;
                }
                
                if (blockName.blockMaterial == Material.wood) {
                    return 300;
                }
            }

            if (item instanceof ItemTool && ((ItemTool) item).getToolMaterialName().equals("WOOD")) return 200;
            if (item instanceof ItemSword && ((ItemSword) item).getToolMaterialName().equals("WOOD")) return 200; // was func_77825_f now getToolMaterialName
            if (item instanceof ItemHoe && ((ItemHoe) item).func_77842_f().equals("WOOD")) return 200;
            if (itemID == Item.stick.itemID) return 100;
            if (itemID == Item.coal.itemID) return 1600;
            if (itemID == Item.bucketLava.itemID) return 20000;
            if (itemID == Block.sapling.blockID) return 100;
            if (itemID == Item.blazeRod.itemID) return 2400;
            return GameRegistry.getFuelValue(itemStack);
        }
    }
    
    
    // Check If Item Is Fuel:
    public static boolean isItemFuel(ItemStack itemStack) {
        return getItemFuelTime(itemStack) > 0;
    }
	
	
	// Update Entity:
	public void updateEntity() {
        boolean hasFuelTime = this.fuelTime > 0;
        boolean stateChange = false;
        
        if ((++this.ticksSinceSync % 80 == 0) && (!this.worldObj.isRemote)) {
        	PacketHandler.sendPacketToClients(getDescriptionPacket(), this.worldObj, new Vector3(this), 12.0D);
        }
        
        if(!this.worldObj.isRemote) {
        	if(this.fuelTime > 0) {
        		this.fuelTime -= 2; // Double Fuel Consumption
            	stateChange = true;
        	}
        	
            if(this.fuelTime == 0 && this.canProcess()) { // Get initial fuel.
                this.initFuelTime = this.fuelTime = getItemFuelTime(this.inventory[1]);
                if(this.fuelTime > 0) {
                	stateChange = true;
                    
                    if(this.inventory[1] != null) {
                        this.inventory[1].stackSize--;

                        if(this.inventory[1].stackSize == 0) {
                            this.inventory[1] = this.inventory[1].getItem().getContainerItemStack(inventory[1]);
                        }
                    }
                }
            }
            
            if(this.isActive() && this.canProcess()) {
                this.processTime++;
                if (this.processTime == this.timeBase) {
                    this.processTime = 0;
                    this.processItem();
                    stateChange = true;
                }
            }
            else {
                this.processTime = 0;
            }
            
            if(hasFuelTime != this.fuelTime > 0) {
            	stateChange = true;
                BlockMachineBasic.updateBlockState(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
                this.blockMetadata = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
            }
        }
        
        if(stateChange) {
        	this.onInventoryChanged();
        	PacketHandler.sendPacketToClients(getDescriptionPacket(), this.worldObj, new Vector3(this), 12.0D);
        }
    }
	
	
	//==================== Machine Information ====================
	// Read NBT:
    @Override
	public void readFromNBT(NBTTagCompound nbtTag) {
        super.readFromNBT(nbtTag);
        this.facing = nbtTag.getShort("facing");
        this.fuelTime = nbtTag.getShort("fuelTime");
        this.initFuelTime = nbtTag.getShort("initFuelTime");
        this.processTime = nbtTag.getShort("processTime");
        this.timeBase = nbtTag.getShort("timeBase");
        this.ticksSinceSync = 20;
        
        NBTTagList tagList = nbtTag.getTagList("Items");
        inventory = new ItemStack[this.getSizeInventory()];
        for (int slot = 0; slot < tagList.tagCount(); slot++) {
            NBTTagCompound tag = (NBTTagCompound)tagList.tagAt(slot);
            byte tagByte = tag.getByte("Slot");
            if (tagByte >= 0 && tagByte < this.inventory.length) {
                this.inventory[tagByte] = ItemStack.loadItemStackFromNBT(tag);
            }
        }
    }
	
	
	// Write NBT:
    @Override
	public void writeToNBT(NBTTagCompound nbtTag) {
        super.writeToNBT(nbtTag);
        nbtTag.setShort("facing", (short)this.facing);
        nbtTag.setShort("fuelTime", (short)this.fuelTime);
        nbtTag.setShort("initFuelTime", (short)this.initFuelTime);
        nbtTag.setShort("processTime", (short)this.processTime);
        nbtTag.setShort("timeBase", (short)this.timeBase);
        
        NBTTagList tagList = new NBTTagList();
        for (int slot = 0; slot < this.inventory.length; slot++) {
            if (this.inventory[slot] != null) {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setByte("Slot", (byte)slot);
                this.inventory[slot].writeToNBT(tag);
                tagList.appendTag(tag);
            }
        }
        nbtTag.setTag("Items", tagList);
    }
    
    
    // Machine Packet Type:
    public enum MachinePacketType {
		NULL (-1),
		FACING (0),
		FUEL_TIME (1),
		PROCESS_TIME (2),
		TIME_BASE (3);
		
		public int id;
		
		private MachinePacketType(int i) {
			id = i;
		}
	}
	
	
	// Packet Setup:
	@Override
	public Packet getDescriptionPacket() {
		return PacketHandler.createPacket("MetalMech", this, Short.valueOf(this.facing), Integer.valueOf(this.processTime), Integer.valueOf(this.fuelTime));
	}
    
    
    // Handle Packet:
    public void handlePacketData(INetworkManager network, int type, Packet250CustomPayload packet, EntityPlayer player, ByteArrayDataInput dataStream) {
    	try {
	    	this.facing = dataStream.readShort();
			this.processTime = dataStream.readInt();
			this.fuelTime = dataStream.readInt();
			BlockMachineBasic.updateBlockState(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		}
		catch(Exception e) {
			System.out.println("[MetalMech] Problem handling machine update packet.");
			e.printStackTrace();
		}
    }
}