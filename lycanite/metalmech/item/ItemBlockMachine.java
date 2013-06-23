package lycanite.metalmech.item;

import lycanite.metalmech.MetalMech;
import lycanite.metalmech.machine.MachineManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class ItemBlockMachine extends ItemBlock {
	
	// ==================================================
	//                       Setup
	// ==================================================
	// ========== Constructor ==========
	public ItemBlockMachine(int id) {
		super(id);
		setHasSubtypes(true);
	}
	
	
	// ==========  Get Item Name from Metadata ==========
	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
        return MachineManager.getName(itemstack.getItemDamage(), MachineManager.getCategory(itemstack.itemID).name);
    }
	
	
	// ========== Get Metadata ==========
	@Override
	public int getMetadata(int damageValue) {
		return damageValue;
	}
}