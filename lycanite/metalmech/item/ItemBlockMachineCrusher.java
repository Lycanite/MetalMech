package lycanite.metalmech.item;

import lycanite.metalmech.MetalMech;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockMachineCrusher extends ItemBlock {
	
	// Constructor:
	public ItemBlockMachineCrusher(int id) {
		super(id);
		setHasSubtypes(true);
	}
	
	
	// Get item Name from Item Damage:
	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return MetalMech.machineCrusherBlockNames[itemstack.getItemDamage()];
	}
	
	
	// Meta Data:
	@Override
	public int getMetadata(int damageValue) {
		return damageValue;
	}
}