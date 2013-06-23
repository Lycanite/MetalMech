package lycanite.metalmech.container;

import lycanite.metalmech.RecipeManager;
import lycanite.metalmech.machine.MachineInventory;
import lycanite.metalmech.tileentity.TileEntityElectricBase;
import universalelectricity.core.item.IItemElectric;
import universalelectricity.prefab.SlotSpecific;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntity;

public class ContainerMachineElectric extends Container {
	
	// Info:
	public TileEntityElectricBase tileEntity;
	public int playerSlotsStart = 0;
	public int playerSlotsInventory = 0;
	public int playerSlotsStop = 0;
	
	
	// Constructor:
	public ContainerMachineElectric(TileEntityElectricBase tileEntityMachine, InventoryPlayer playerInventory) {
		this.tileEntity = tileEntityMachine;
		drawItemGrids(playerInventory);
		tileEntity.openChest();
	}
	
	
	// Draw Item Grids:
	protected void drawItemGrids(InventoryPlayer playerInventory) {
		drawElectricMachineGrid(tileEntity, playerInventory);
        drawPlayerGrid(playerInventory);
	}
	
	
	// Draw Furnace Grid:
	protected void drawElectricMachineGrid(TileEntityElectricBase tileEntity, InventoryPlayer playerInventory) {
		for(MachineInventory inventoryValue : tileEntity.inventory.values()) {
			for(Slot slot : inventoryValue.getSlots(tileEntity, playerInventory)) {
				addSlotToContainer(slot);
				playerSlotsStart++;
			}
		}
	}
	
	
	// Draw Player Grid:
	protected void drawPlayerGrid(InventoryPlayer playerInventory) {
		int rows = 3;
		int columns = 9;
		playerSlotsStop = playerSlotsStart + (rows * columns);
		playerSlotsInventory = playerSlotsStop;
		for(int row = 0; row < rows; row++) {
			for(int column = 0; column < columns; column++) {
				addSlotToContainer(new Slot(playerInventory, column + row * 9 + 9, 8 + column * 18, 84 + row * 18));
			}
		}
		
		rows = 1;
		columns= 9;
		playerSlotsStop += rows * columns;
		for(int column = 0; column < columns; column++) {
			this.addSlotToContainer(new Slot(playerInventory, column, 8 + column * 18, 142));
		}
	}
	
	
	// Check Slot Range:
	public boolean checkSlotRange(String slotName, int slotIndex) {
		if(tileEntity.inventory.get(slotName) == null) return false;
		if(slotIndex >= tileEntity.inventory.get(slotName).slotIndexStart && slotIndex < tileEntity.inventory.get(slotName).slotIndexStart + tileEntity.inventory.get(slotName).getSize())
			return true;
		return false;
	}
	
	
	// Close Container:
	@Override
	public void onCraftGuiClosed(EntityPlayer entityplayer) {
		super.onCraftGuiClosed(entityplayer);
		this.tileEntity.closeChest();
	}
	
	
	// Can Interact With:
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tileEntity.isUseableByPlayer(player);
	}
	
	
	// Shift Click: (Returns an ItemStack of what's leftover when automatically inserting items.)
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotClicked) {
		ItemStack itemStack = null;
		Slot slot = (Slot)inventorySlots.get(slotClicked);
		
	    if((slot != null) && (slot.getHasStack())) {
	    	ItemStack slotStack = slot.getStack();
	    	itemStack = slotStack.copy();
	    	
	    	// If Output is Clicked
	    	if(checkSlotRange("Output", slotClicked)) {
	    		if(!mergeItemStack(slotStack, playerSlotsStart, playerSlotsStop, false)) return null;
	    		slot.onSlotChange(slotStack, itemStack);
	    	}
	    	
	    	// If player inventory is Clicked
	    	else if(slotClicked >= playerSlotsStart && slotClicked < playerSlotsStop) {
	    		boolean mergedToMachineSlot = false;
	    		for(MachineInventory inventoryValue : tileEntity.inventory.values()) {
	    			if(inventoryValue.stackValidContainer(slotStack)) {
	    				if(!mergeItemStack(slotStack, inventoryValue.slotIndexStart, inventoryValue.slotIndexStart + inventoryValue.getSize(), false)) return null;
	    				mergedToMachineSlot = true;
	    				break;
	    			}
	    			else if(inventoryValue.name == "Input" && tileEntity.getProcessedItem(slotStack) != null) {
	    				if(!mergeItemStack(slotStack, inventoryValue.slotIndexStart, inventoryValue.slotIndexStart + inventoryValue.getSize(), false)) return null;
	    				mergedToMachineSlot = true;
	    				break;
	    			}
	    		}
	    		if(!mergedToMachineSlot) {
	    			if(slotClicked < playerSlotsInventory) // Merge to Hotbar
		    			if(!mergeItemStack(slotStack, playerSlotsInventory, playerSlotsStop, false)) return null;
		    		else if(slotClicked >= playerSlotsInventory && slotClicked < playerSlotsStop) // Merge to Inventory
		    			if(!mergeItemStack(slotStack, playerSlotsStart, playerSlotsInventory, false)) return null;
	    		}
	    	}
	    	
	    	// If Other Machine Slots are Clicked
	    	else if(!mergeItemStack(slotStack, playerSlotsStart, playerSlotsStop, false)) return null;
	    	
	    	if(slotStack.stackSize == 0)
	    		slot.putStack((ItemStack)null);
	    	else
	    		slot.onSlotChanged();
		    
	    	if(slotStack.stackSize == itemStack.stackSize) return null;
	    	
	    	slot.onPickupFromSlot(player, slotStack);
	    }
	    
	    return itemStack;
	}
}