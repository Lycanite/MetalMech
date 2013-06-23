package lycanite.metalmech.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import lycanite.metalmech.MetalMech;
import lycanite.metalmech.machine.MachineManager;

public class TileEntityMachineCrusher extends TileEntityMachine {
	
	// Constructor:
	public TileEntityMachineCrusher() {
		super();
	}
	
	
	// Get Machine Type:
	public String getType() {
		return MachineManager.getType(this.metadata, this.getCategory());
	}
	
	
	// Get Machine Type:
	@Override
	public String getCategory() {
		return "Crusher";
	}
    
    
    // Get Processed Item:
	@Override
    public ItemStack getProcessedItem() {
		return MetalMech.hooks.metallurgyGetCrushingResult(this.inventory[0]);
    }
}
