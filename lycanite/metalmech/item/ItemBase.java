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
	public int subItems = 1;
	public String[] itemNames = { "itemBase" };
	public String[] names = { "ItemBase" };
	public String[] titles = { "Item Base" };
	public String[] oreNames = { "baseItem" };
	public boolean oreRegistry = false;
	
	// Visual:
	public String texturePath;
	public Icon[] itemIcons = new Icon[1];
	
	
	// ==================================================
	//                       Setup
	// ==================================================
	// ========== Constructor ==========
	public ItemBase(int id, String setTexturePath) {
		super(id - 256);
		setHasSubtypes(true);
		texturePath = setTexturePath;
		setCreativeTab(MetalMech.creativeTab);
	}
	
	
	// ========== Register Item ==========
	public void registerItem() {
		for(int i = 0; i < subItems; i++) {
			ItemStack itemStack = new ItemStack(itemID, 1, i);
			LanguageRegistry.addName(itemStack, titles[i]);
			if(oreRegistry) {
				OreDictionary.registerOre(itemNames[i], itemStack);
				OreDictionary.registerOre(oreNames[i], itemStack);
			}
		}
	}
	
	
	// ==========  Get Item Name from Metadata ==========
	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
        return titles[itemstack.getItemDamage()];
    }
	
	
	// ========== Sub-Items ==========
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int unknown, CreativeTabs tab, List subItemsList) {
		for(int i = 0; i < subItems; i++)
			subItemsList.add(new ItemStack(this, 1, i));
	}
	
	
	// ==================================================
	//                      Visuals
	// ==================================================
	// ========== Register Icons ==========
	@Override
	public void registerIcons(IconRegister iconRegister) {
		itemIcons = new Icon[subItems];
		for(int i = 0; i < subItems; i++)
			itemIcons[i] = iconRegister.registerIcon(MetalMech.modid + ":" + texturePath + itemNames[i]);
	}
	
	
	// ========== Get Texture From Side and Metadata ==========
	public Icon getIconFromDamage(int metadata) {
		if(metadata > subItems - 1)
			return itemIcons[0];
		return itemIcons[metadata];
	}
}
