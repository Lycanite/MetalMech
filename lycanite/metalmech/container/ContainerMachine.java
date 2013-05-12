package lycanite.metalmech.container;

import lycanite.metalmech.container.SlotMachine;
import lycanite.metalmech.tileentity.TileEntityMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntity;

public class ContainerMachine extends Container {
	
	// Info:
	protected TileEntityMachine tileEntity;
	
	
	// Constructor:
	public ContainerMachine(TileEntityMachine tileEntityMachine, InventoryPlayer playerInventory) {
		this.tileEntity = tileEntityMachine;
		drawItemGrids(playerInventory);
        tileEntity.openChest();
	}
	
	
	// Draw Item Grids:
	protected void drawItemGrids(InventoryPlayer playerInventory) {
		drawFurnaceGrid(this.tileEntity, playerInventory);
        drawPlayerGrid(playerInventory);
	}
	
	
	// Draw Item Grid:
	protected void drawItemGrid(int startID, int rows, int columns) {
		int slotID = startID;
		for(int row = 0; row < rows; row++) {
			for(int column = 0; column < columns; column++) {
				addSlotToContainer(new Slot(tileEntity, slotID, 9 + column * 18, 9 + row * 18));
				
				slotID++;
			}
		}
	}
	
	
	// Draw Furnace Grid:
	protected void drawFurnaceGrid(TileEntity tileEntityContainer, InventoryPlayer playerInventory) {
		addSlotToContainer(new Slot((IInventory) tileEntityContainer, 0, 56, 17)); // Input
        addSlotToContainer(new Slot((IInventory) tileEntityContainer, 1, 56, 53)); // Fuel
        addSlotToContainer(new SlotMachine(playerInventory.player, (IInventory) tileEntityContainer, 2, 116, 35)); // Output
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
		
		if (slot != null && slot.getHasStack()) {
            ItemStack var5 = slot.getStack();
            itemStack = var5.copy();
            
            if (slotIndex == 2) {
                if (!this.mergeItemStack(var5, 3, 39, true)) {
                    return null;
                }
                
                slot.onSlotChange(var5, itemStack);
            }
            else if (slotIndex != 1 && slotIndex != 0) {
                if (FurnaceRecipes.smelting().getSmeltingResult(var5) != null) {
                    if (!this.mergeItemStack(var5, 0, 1, false)) {
                        return null;
                    }
                }
                else if (TileEntityMachine.isItemFuel(var5)) {
                    if (!this.mergeItemStack(var5, 1, 2, false))
                    {
                        return null;
                    }
                }
                else if (slotIndex >= 3 && slotIndex < 30) {
                    if (!this.mergeItemStack(var5, 30, 39, false)) {
                        return null;
                    }
                }
                else if (slotIndex >= 30 && slotIndex < 39 && !this.mergeItemStack(var5, 3, 30, false)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(var5, 3, 39, false)) {
                return null;
            }
            
            if (var5.stackSize == 0) {
            	slot.putStack((ItemStack)null);
            }
            else {
            	slot.onSlotChanged();
            }
            
            if (var5.stackSize == itemStack.stackSize) {
                return null;
            }
            
            slot.onPickupFromSlot(player, var5);
        }

        return itemStack;
	}
	
	
	// Close Container:
	@Override
	public void onCraftGuiClosed(EntityPlayer entityplayer) {
		super.onCraftGuiClosed(entityplayer);
		this.tileEntity.closeChest();
	}
}