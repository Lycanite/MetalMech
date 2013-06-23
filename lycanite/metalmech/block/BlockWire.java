package lycanite.metalmech.block;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lycanite.metalmech.MetalMech;
import lycanite.metalmech.client.ClientProxy;
import lycanite.metalmech.item.ItemBase;
import lycanite.metalmech.item.ItemBlockMachine;
import lycanite.metalmech.machine.MachineManager;
import lycanite.metalmech.tileentity.TileEntityWire;
import universalelectricity.core.block.IConductor;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class BlockWire extends BlockBase {
	
	// Visuals:
	Icon[] blockIcons;

	// =========== Constructor ==========
	public BlockWire(int id, String texture) {
		super(id, texture, Material.cloth);
		setBlockBounds(0.30F, 0.30F, 0.30F, 0.70F, 0.70F, 0.70F);
		setHardness(0.5f);
		setResistance(0.2F);
		Block.setBurnProperties(blockID, 30, 60);
		setStepSound(soundClothFootstep);
		setCreativeTab(MetalMech.creativeTab);
		addToOreRegistry();
	}
	
	
	// ========== Block Setup ==========
	// Register:
	@Override
	public void registerBlock() {
		Item.itemsList[this.blockID] = new ItemBlockMachine(blockID - 256);
		for(int itemMeta = 0; itemMeta < MachineManager.getCategory(blockID).machineCount; itemMeta++)
			if(oreRegistry)
				OreDictionary.registerOre(MachineManager.getMachineInfo(blockID, itemMeta).blockName, new ItemStack(blockID, 1, itemMeta));
	}
	
	
	// On Placement:
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if(tileEntity instanceof IConductor)
			((IConductor)tileEntity).updateAdjacentConnections();
	}
	
	
	// Get Sub Blocks (For Creative Tabs):
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(int unknown, CreativeTabs tab, List subItems) {
		for(int itemMeta = 0; itemMeta < MachineManager.getCategory(blockID).machineCount; itemMeta++) {
			subItems.add(new ItemStack(this, 1, itemMeta));
		}
	}
	
	
	// ========== Block Behaviour ==========
	// On Neighbour Update:
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int blockID){
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if(tileEntity instanceof IConductor)
			((IConductor)tileEntity).updateAdjacentConnections();
	}
	
	
	// TileEntity:
	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		return new TileEntityWire();
	}
	
	
	// ========== Block Visuals ==========
	// Render as Block:
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	
	// Is Opaque:
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	
	// Render Type:
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType() {
		return ClientProxy.RENDER_ID;
	}
	
	
	// Register Icons:
	@Override
	public void registerIcons(IconRegister iconRegister) {
		int subBlocks = MachineManager.getCategory(blockID).machineCount;
		Icon[] setBlockIcons = new Icon[subBlocks];
		for(int i = 0; i < subBlocks; i++)
			setBlockIcons[i] = iconRegister.registerIcon(MetalMech.modid + ":" + texturePath + MachineManager.getMachineInfo(blockID, i).name);
		blockIcons = setBlockIcons;
	}
	
	
	// Get Texture From Side and Meta:
	@Override
	public Icon getIcon(int side, int metadata) {
		return blockIcons[metadata];
	}
}
