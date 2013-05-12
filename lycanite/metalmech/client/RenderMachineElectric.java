package lycanite.metalmech.client;

import lycanite.metalmech.MetalMech;
import lycanite.metalmech.tileentity.TileEntityMachineElectric;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderMachineElectric extends TileEntitySpecialRenderer {
	
	// Render:
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTick) {
		renderModelAt((TileEntityMachineElectric)tileEntity, x, y, z, partialTick);
	}
	
	
	// Render Model:
	ModelSawmill modelSawmill = new ModelSawmill(); // TEST XXX
	public void renderModelAt(TileEntityMachineElectric tileEntity, double x, double y, double z, float partialTick) {
		
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
		
		// Get Model:
		String type = tileEntity.getType();
		
		// Sawmill Test: XXX
		if(type == "Sawmill") {
			ModelSawmill modelSawmill = new ModelSawmill();
			if(rotation == 90) rotation = -90;
			else if(rotation == -90) rotation = 90;
			modelSawmill.render(tileEntity, x, y, z, rotation);
			return;
		}
		
		IModelRender model = MetalMech.models.get(type);
		
		if(tileEntity.isActive())
			bindTextureByName("/mods/" + MetalMech.modid + "/textures/models/electric/" + type + "Active.png");
		else
			bindTextureByName("/mods/" + MetalMech.modid + "/textures/models/electric/" + type + ".png");
		
		GL11.glPushMatrix();
		GL11.glEnable(32826);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float)x, (float)y + 1.0F, (float)z + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		
		GL11.glRotatef(rotation, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -0.5F + 0.0F, -0.5F);
		
		model.renderAll();
		
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
}
