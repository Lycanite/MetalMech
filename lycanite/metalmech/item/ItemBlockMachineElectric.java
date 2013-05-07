package lycanite.metalmech.item;

import lycanite.metalmech.MetalMech;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockMachineElectric extends ItemBlock {
	
	// Constructor:
	public ItemBlockMachineElectric(int id) {
		super(id);
		setHasSubtypes(true);
	}
	
	
	// Get item Name from Item Damage:
	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return MetalMech.electricMachineBlockNames[itemstack.getItemDamage()];
	}
	
	
	// Meta Data:
	@Override
	public int getMetadata(int damageValue) {
		return damageValue;
	}
}