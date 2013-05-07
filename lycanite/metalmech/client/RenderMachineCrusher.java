package lycanite.metalmech.client;

import lycanite.metalmech.MetalMech;
import lycanite.metalmech.block.TileEntityMachine;
import lycanite.metalmech.block.TileEntityMachine.MachineType;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderMachineCrusher extends TileEntitySpecialRenderer {
	
	// Render:
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float rot) {
		renderModelAt((TileEntityMachine)tileEntity, x, y, z, rot);
	}
	
	
	// Render Model:
	public void renderModelAt(TileEntityMachine tileEntity, double x, double y, double z, float rot) {
		float offset = 0.0F;
		int facing = 5;
		facing = tileEntity.facing;
		
		// Get Model:
		String rank = "";
		IModelRender model = MetalMech.models.get("Crusher");
		
		if(tileEntity.getType() == MachineType.CRUSHER.id) {
			rank = TileEntityMachine.getRankFromMetadata(tileEntity.getRank());
		}
		
		if (tileEntity.isActive())
			bindTextureByName("/mods/" + MetalMech.modid + "/textures/models/crusher/" + rank + "Active.png");
		else {
			bindTextureByName("/mods/" + MetalMech.modid + "/textures/models/crusher/" + rank + ".png");
		}
		
		GL11.glPushMatrix();
		GL11.glEnable(32826);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float)x, (float)y + 1.0F, (float)z + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		short var11 = 0;
		
		if (facing == 2) {
			var11 = 180;
		}
		
		if (facing == 3) {
			var11 = 0;
		}
		
		if (facing == 4) {
			var11 = 90;
		}
		
		if (facing == 5) {
			var11 = -90;
		}
		
		GL11.glRotatef(var11, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -0.5F + offset, -0.5F);
		
		model.renderAll();
		
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
}
