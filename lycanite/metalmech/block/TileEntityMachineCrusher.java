package lycanite.metalmech.block;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import lycanite.metalmech.MetalMech;
import lycanite.metalmech.block.TileEntityMachine.MachineType;

public class TileEntityMachineCrusher extends TileEntityMachine {
	
	// Constructor:
	public TileEntityMachineCrusher() {
		super();
	}
	
	
	// Set Time Base:
	@Override
	public void setTimeBase(int newTime) {
		this.timeBase = (int)40.0D * newTime;
	}
	
	
	// Get Machine Type:
	public int getType() {
		return MachineType.CRUSHER.id;
	}
	
	
	// Get Inventory Name:
	@Override
	public String getInvName() {
		return MetalMech.machineCrusherBlockTitles[this.getBlockMetadata()];
	}
    
    
    // Get Processed Item:
	@Override
    public ItemStack getProcessedItem() {
    	return MetalMech.hooks.metallurgyGetCrushingResult(this.inventory[0]);
    }
}
