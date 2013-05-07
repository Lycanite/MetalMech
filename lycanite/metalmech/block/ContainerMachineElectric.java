package lycanite.metalmech.block;

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
	public TileEntityMachineElectric tileEntity;
	
	
	// Constructor:
	public ContainerMachineElectric(TileEntityMachineElectric tileEntityMachine, InventoryPlayer playerInventory) {
		this.tileEntity = tileEntityMachine;
		drawItemGrids(playerInventory);
		tileEntity.openChest();
	}
	
	
	// Draw Item Grids:
	protected void drawItemGrids(InventoryPlayer playerInventory) {
		drawElectricMachineGrid(this.tileEntity, playerInventory);
        drawPlayerGrid(playerInventory);
	}
	
	
	// Draw Furnace Grid:
	protected void drawElectricMachineGrid(TileEntity tileEntityContainer, InventoryPlayer playerInventory) {
        addSlotToContainer(new Slot((IInventory) tileEntityContainer, 0, 55, 25)); // Input
		addSlotToContainer(new SlotSpecific((TileEntityMachineElectric)tileEntityContainer, 1, 55, 49, IItemElectric.class)); // Battery
        addSlotToContainer(new SlotMachine(playerInventory.player, (IInventory) tileEntityContainer, 2, 108, 25)); // Output
	}
	
	
	// Draw Player Grid:
	protected void drawPlayerGrid(InventoryPlayer playerInventory) {
		int rows = 3;
		int columns = 9;
		for(int row = 0; row < rows; row++) {
			for(int column = 0; column < columns; column++) {
				addSlotToContainer(new Slot(playerInventory, column + row * 9 + 9, 8 + column * 18, 84 + row * 18));
			}
		}
		
		rows = 1;
		columns= 9;
		for(int column = 0; column < columns; column++) {
			this.addSlotToContainer(new Slot(playerInventory, column, 8 + column * 18, 142));
		}
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
	
	
	// Shift Click:
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		ItemStack itemStack = null;
		Slot slot = (Slot)inventorySlots.get(slotIndex);
		
	    if((slot != null) && (slot.getHasStack())) {
	    	ItemStack var4 = slot.getStack();
	    	itemStack = var4.copy();
	    	
	    	if(slotIndex == 2) {
	    		if(!mergeItemStack(var4, 3, 39, true)) return null;
	    		
	    		slot.onSlotChange(var4, itemStack);
	    	}
	    	else if((slotIndex != 0) && (slotIndex != 1)) {
	    		if((var4.getItem() instanceof IItemElectric)) {
	    			if(!mergeItemStack(var4, 1, 2, false)) return null;
	    		}
	    		else if(tileEntity.getProcessedItem(var4) != null) {
	    			if(!mergeItemStack(var4, 0, 1, false)) return null;
	    		}
	    		else if((slotIndex >= 3) && (slotIndex < 30)) {
	    			if(!mergeItemStack(var4, 30, 39, false)) return null;
	    		}
	    		else if((slotIndex >= 30) && (slotIndex < 39) && (!mergeItemStack(var4, 3, 30, false))) return null;
	    	}
	    	else if(!mergeItemStack(var4, 3, 39, false)) { return null; }
	    	
	    	if(var4.stackSize == 0) {
	    		slot.putStack((ItemStack)null);
	    	}
	    	else {
	    		slot.onSlotChanged();
	    	}
		    
	    	if(var4.stackSize == itemStack.stackSize) return null;
	    	
	    	slot.onPickupFromSlot(player, var4);
	    }
	    
	    return itemStack;
	}
}