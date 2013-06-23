package lycanite.metalmech.block;

import java.util.List;
import java.util.Random;

import lycanite.metalmech.CommonProxy;
import lycanite.metalmech.MetalMech;
import lycanite.metalmech.item.ItemBlockMachine;



import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBase extends BlockContainer {
	
	// Info:
	public boolean oreRegistry = false;
	
	// Textures:
	public int metadata;
	public String texturePath;
	
	
	// ========== Constructor ==========
	public BlockBase (int id, String texture, Material material) {
		super(id, material);
        texturePath = texture;
	}
	
	
	// Register:
	public void registerBlock() {
		Item.itemsList[this.blockID] = new ItemBlockMachine(blockID - 256);
	}
	
	
	// ========== Block Details ==========
	// Add To Ore Registry:
	public void addToOreRegistry() {
		oreRegistry = true;
	}
	
	
	// Get Sub Blocks (For Creative Tabs):
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int unknown, CreativeTabs tab, List subItems) {
		for(int itemMeta = 0; itemMeta < 1; itemMeta++) {
			subItems.add(new ItemStack(this, 1, itemMeta));
		}
	}
	
	
	// ========== Block Behaviour ==========
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
	
	
	// ========== Create Tile Entity ==========
	@Override
	public TileEntity createNewTileEntity(World world) {
		return null;
	}
	
	
	// ========== Block Visuals ==========
	// Register Icons:
	@Override
	public void registerIcons(IconRegister iconRegister) {
		blockIcon = iconRegister.registerIcon(MetalMech.modid + ":" + texturePath);
	}
	
	
	// Get Texture From Side and Meta:
	public Icon getIcon(int side, int metadata) {
		return blockIcon;
	}
	
	
	// Render Type:
	@Override
	public boolean renderAsNormalBlock() {
		return true;
	}
}