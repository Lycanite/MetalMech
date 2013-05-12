package lycanite.metalmech.item;

import lycanite.metalmech.MachineManager;
import lycanite.metalmech.MetalMech;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class ItemBlockMachine extends ItemBlock {
	
	// Constructor:
	public ItemBlockMachine(int id) {
		super(id);
		setHasSubtypes(true);
	}
	
	
	// Get item Name from Item Damage:
	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
        return MachineManager.getName(itemstack.getItemDamage(), MachineManager.getCategory(itemstack.itemID));
    }
	
	
	// Meta Data:
	@Override
	public int getMetadata(int damageValue) {
		return damageValue;
	}
}