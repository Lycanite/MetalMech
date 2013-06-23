package lycanite.metalmech.client;

import lycanite.metalmech.MetalMech;
import lycanite.metalmech.tileentity.TileEntityElectricBase;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderMachineElectric extends TileEntitySpecialRenderer {
	
	// Render:
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTick) {
		renderModelAt((TileEntityElectricBase)tileEntity, x, y, z, partialTick);
	}
	
	
	// Render Model:
	public void renderModelAt(TileEntityElectricBase tileEntity, double x, double y, double z, float partialTick) {
		
		// Get Rotation:
		short rotation = 0;
		switch(tileEntity.getFacing()) {
			case 2:
				rotation = 180;
				break;
			case 3:
				rotation = 0;
				break;
			case 4:
				rotation = 90;
				break;
			case 5:
				rotation = -90;
				break;
		}
		
		// Render Model:
		ModelMachineElectric modelMachineElectric = new ModelMachineElectric();
		if(rotation == 90) rotation = -90;
		else if(rotation == -90) rotation = 90;
		modelMachineElectric.render(tileEntity, x, y, z, rotation);
		return;
	}
}
