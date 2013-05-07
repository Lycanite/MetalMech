package lycanite.metalmech;

import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TabMetalMech extends CreativeTabs {
	public TabMetalMech(int tabID, String modID) {
		super(tabID, modID);
		LanguageRegistry.instance().addStringLocalization("itemGroup." + modID, MetalMech.name);
	}

	@Override
	public ItemStack getIconItemStack() {
		return new ItemStack(MetalMech.machineBlockElectric, 1, 1);
	}
}