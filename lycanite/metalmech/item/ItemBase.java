package lycanite.metalmech.item;

import java.util.List;

import lycanite.metalmech.CommonProxy;
import lycanite.metalmech.MetalMech;



import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBase extends Item {
	
	// Info:
	public String texturePath;
	public int subItems = 1;
	public String[] subItemNames;
	public String[] subItemTitles;
	public boolean oreRegistry = false;
	public Icon iconIndex;
	
	
	// Constructor:
	public ItemBase(int id) {
		super(id - 256);
		setHasSubtypes(true);
	}
	
	
	// Set Sub Item Names:
	public void setSubItemNames(String[] newSubItemNames) {
		this.subItemNames = newSubItemNames;
		this.subItems = subItemNames.length;
	}
	
	
	// Set Sub Item Titles:
	public void setSubItemTitles(String[] newSubItemTitles) {
		this.subItemTitles = newSubItemTitles;
	}
	
	
	// Add To Ore Registry:
	public void addToOreRegistry() {
		this.oreRegistry = true;
	}
	
	
	// Register Item:
	public void registerItem() {
		for(int subItem = 0; subItem < subItems; subItem++) {
			ItemStack item = new ItemStack(itemID, 1, subItem);
			LanguageRegistry.addName(item, subItemTitles[subItem]);
			if(oreRegistry) {
				OreDictionary.registerOre(subItemNames[subItem], new ItemStack(this, 1, 0));
			}
		}
	}
	
	
	// Register Icons:
	public void updateIcons(IconRegister iconRegister) {
		this.iconIndex = iconRegister.registerIcon(MetalMech.modid + ":" + texturePath);
	}
	
	
	// Get Texture From Side and Meta:
	public Icon getIconFromDamage(int metadata) {
		return this.iconIndex;
	}
	
	
	// Sub-Items:
	@SideOnly(Side.CLIENT)
	public void getSubItems(int unknown, CreativeTabs tab, List subItemsList) {
		for (int ix = 0; ix < subItems; ix++) {
			subItemsList.add(new ItemStack(this, 1, ix));
		}
	}
}
