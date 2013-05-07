package lycanite.metalmech.block;

import java.util.List;
import java.util.Random;

import lycanite.metalmech.CommonProxy;
import lycanite.metalmech.MetalMech;



import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBase extends Block {
	
	// Info:
	public int subBlocks = 1;
	public String[] subBlockNames;
	public String[] subBlockTitles;
	public int metadata;
	public String texturePath;
	public boolean oreRegistry = false;
	
	
	// Constructor:
	public BlockBase (int id, String texture, Material material) {
		super(id, material);
        this.texturePath = texture;
	}
	
	
	// Set Sub Block Names:
	public void setSubBlockNames(String[] newSubBlockNames) {
		this.subBlockNames = newSubBlockNames;
		this.subBlocks = subBlockNames.length;
	}
	
	
	// Set Sub Block Titles:
	public void setSubBlockTitles(String[] newSubBlockTitles) {
		this.subBlockTitles = newSubBlockTitles;
	}
	
	
	// Add To Ore Registry:
	public void addToOreRegistry() {
		this.oreRegistry = true;
	}
	
	
	// Register Block:
	public void registerBlock() {
		for(int subBlock = 0; subBlock < subBlocks; subBlock++) {
			LanguageRegistry.instance().addStringLocalization("null." + subBlockNames[subBlock] + ".name", subBlockTitles[subBlock]);
			if(oreRegistry) {
				OreDictionary.registerOre(subBlockNames[subBlock], new ItemStack(this, 1, subBlock));
			}
		}
	}
	
	
	// Drop ID:
	@Override
	public int idDropped(int par1, Random random, int zero) {
        return this.blockID;
	}
	
	
	// Drop Meta:
	@Override
	public int damageDropped(int metadata) {
		return metadata;
	}
	
	
	// Register Icons:
	@Override
	public void registerIcons(IconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon(MetalMech.modid + ":" + texturePath);
	}
	
	
	// Get Texture From Side and Meta:
	public Icon getBlockTextureFromSideAndMetadata(int side, int metadata) {
		return this.blockIcon;
	}
	
	
	// Get Sub Blocks (For Creative Tabs):
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int unknown, CreativeTabs tab, List subItems) {
		for(int itemMeta = 0; itemMeta < subBlocks; itemMeta++) {
			subItems.add(new ItemStack(this, 1, itemMeta));
		}
	}
}