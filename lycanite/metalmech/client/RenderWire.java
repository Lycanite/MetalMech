package lycanite.metalmech.client;

import lycanite.metalmech.MetalMech;
import lycanite.metalmech.tileentity.TileEntityWire;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderWire extends TileEntitySpecialRenderer {
	
	// Render:
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTick) {
		renderModelAt((TileEntityWire)tileEntity, x, y, z, partialTick);
	}
	
	
	// Render Model:
	public void renderModelAt(TileEntityWire tileEntity, double x, double y, double z, float partialTick) {
		// Get Rotation: (Bottom, Top, Front, Back, Left, Right)
		boolean[] connections = tileEntity.getVisualConnections();
		
		// Render Model:
		ModelWire modelWire = new ModelWire();
		modelWire.render(tileEntity, x, y, z, connections);
		return;
	}
}
