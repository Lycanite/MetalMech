package lycanite.metalmech.machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lycanite.metalmech.RecipeManager;
import lycanite.metalmech.container.SlotMachine;
import lycanite.metalmech.tileentity.TileEntityElectricBase;
import universalelectricity.core.item.IItemElectric;
import universalelectricity.core.vector.Vector2;
import universalelectricity.prefab.SlotSpecific;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class MachineInventory {
	
	// ========== Info ==========
	public String name = "InventoryName";
	public MachineInventory previousInventory;
	public int slotIndexStart = 0;
	
	// ========== Inventory ==========
	public ItemStack[] items;
	
	// ========== Slots ==========
	public Vector2 slotPos = new Vector2(0, 0);
	

	// ========== Constructor ==========
	public MachineInventory(String setName, int size, MachineInventory setPreviousInventory, Vector2 setSlotPos) {
		name = setName;
		items = new ItemStack[size];
		if(setPreviousInventory != null) previousInventory = setPreviousInventory;
		updateSlotIndex();
		slotPos = setSlotPos;
	}
	

	// ========== Inventory Info ==========
	public int getSize() {
		return items.length;
	}
	
	public boolean stackValid(ItemStack itemStack) {
		if(name == "Battery" && itemStack.getItem() instanceof IItemElectric)
			return true;
		else if(name == "Fuel" && RecipeManager.getItemFuel(itemStack) > 0)
			return true;
		return false;
	}
	
	public int getEmptySlot() {
		for(int i = 0; i < items.length; i++)
			if(items[i] == null) {
				return i;
			}
		return -1;
	}
	
	public boolean stackValidContainer(ItemStack itemStack) {
		if(name == "Battery" && itemStack.getItem() instanceof IItemElectric)
			return true;
		else if(name == "Charge" && itemStack.getItem() instanceof IItemElectric)
			return true;
		else if(name == "Fuel" && RecipeManager.getItemFuel(itemStack) > 0)
			return true;
		return false;
	}
	
	
	// ========== Slots ==========
	public void updateSlotIndex() {
		if(previousInventory != null)
			slotIndexStart = previousInventory.slotIndexStart + previousInventory.getSize();
		else
			slotIndexStart = 0;
	}
	
	public List<Slot> getSlots(TileEntityElectricBase tileEntity, InventoryPlayer playerInventory) {
		List<Slot> slots = new ArrayList<Slot>();
		for(int i = 0; i < items.length; i++) {
			if(name == "Output")
				slots.add(new SlotMachine(playerInventory.player, (IInventory)tileEntity, slotIndexStart + i, (int)slotPos.x, (int)slotPos.y));
			if(name == "Battery")
				slots.add(new SlotSpecific((TileEntityElectricBase)tileEntity, slotIndexStart + i, (int)slotPos.x, (int)slotPos.y, IItemElectric.class));
			else
				slots.add(new Slot((IInventory)tileEntity, slotIndexStart + i, (int)slotPos.x, (int)slotPos.y));
      }
		return slots;
	}
	
	
	// ========== NBT ==========
	public void readNBTTagList(NBTTagList tagList) {
		for(int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound tagCompund = (NBTTagCompound)tagList.tagAt(i);
			byte slotIndex = tagCompund.getByte("Slot");
			if((slotIndex >= 0) && (slotIndex < items.length)) {
				items[slotIndex] = ItemStack.loadItemStackFromNBT(tagCompund);
			}
	    }
	}
	
	public NBTTagList getNBTTagList() {
		NBTTagList tagList = new NBTTagList();
	    for(int i = 0; i < items.length; i++) {
	    	if(items[i] != null) {
	    		NBTTagCompound tagCompound = new NBTTagCompound();
	    		tagCompound.setByte("Slot", (byte)i);
	    		items[i].writeToNBT(tagCompound);
	    		tagList.appendTag(tagCompound);
	    	}
	    }
	    return tagList;
	}
}
