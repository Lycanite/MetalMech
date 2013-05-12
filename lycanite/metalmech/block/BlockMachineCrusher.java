package lycanite.metalmech.block;

import java.util.List;


import lycanite.metalmech.CommonProxy;
import lycanite.metalmech.MetalMech;
import lycanite.metalmech.client.ClientProxy;
import lycanite.metalmech.item.ItemBlockMachine;
import lycanite.metalmech.tileentity.TileEntityMachineCrusher;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMachineCrusher extends BlockMachineBasic {
	
	// Info:
	private int renderId = RenderingRegistry.getNextAvailableRenderId();
	
	// Constructor
	public BlockMachineCrusher(int id, String texture) {
		super(id, texture);
	}
	
	
	// Tile Entity:
	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		TileEntityMachineCrusher tileEntity = new TileEntityMachineCrusher();
		tileEntity.setMetadata(metadata);
		return tileEntity;
	}
	
	
	// Block Rendering:
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	
	// Is Opaque:
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	
	// Get Render Type:
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType() {
		return ClientProxy.RENDER_ID;
	}
}
