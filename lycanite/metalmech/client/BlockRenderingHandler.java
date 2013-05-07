package lycanite.metalmech.client;

import org.lwjgl.opengl.GL11;

import lycanite.metalmech.MetalMech;
import lycanite.metalmech.block.TileEntityMachineElectric;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockRenderingHandler implements ISimpleBlockRenderingHandler {
	
	// Render Inventory Block: TODO Remove if not needed.
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		/*if(block.blockID == MetalMech.machineBlockElectric.blockID) {
			TileEntityMachineElectric tileEntity = new TileEntityMachineElectric();
			tileEntity.setBlockMetadata(metadata);
			TileEntityRenderer.instance.renderTileEntityAt(tileEntity, 0.0D, 0.0D, 0.0D, 0.0F);
		}*/
	}
	
	// Render World Block:
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		return false;
	}
	
	// 3D in Inventory:
	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}
	
	// Get Render ID:
	@Override
	public int getRenderId() {
		return ClientProxy.RENDER_ID;
	}
}
