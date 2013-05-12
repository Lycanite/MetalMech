package lycanite.metalmech.client;

import lycanite.metalmech.MetalMech;
import lycanite.metalmech.tileentity.TileEntityMachineElectric;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;

import net.minecraft.client.model.ModelBase;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class ModelSawmill extends ModelBase implements IModelRender {
	private IModelCustom model = AdvancedModelLoader.loadModel("/mods/" + MetalMech.modid + "/models/Electric/" + "Sawmill" + ".obj");

	public ModelSawmill() {
		
	}
	
	private void render() {
		model.renderAll();
	}
	
	public void render(Object object, double x, double y, double z, short rotation) {
		GL11.glPushMatrix();
		
		// Position Model:
		GL11.glTranslatef((float)x + 0.5f, (float)y + 0.5f, (float)z + 0.5f);
		
		// Scale Model:
		GL11.glScalef(0.5f, 0.5f, 0.5f);
		
		// Rotate Model:
		if(object instanceof TileEntityMachineElectric)
			GL11.glRotatef(rotation, 0.0F, 1.0F, 0.0F);
		else
			GL11.glRotatef(180f, 1.0F, 0.0F, 0.0F);
		
		// Bind Texture:
		String texture = "";
		if(object instanceof TileEntityMachineElectric) {
			texture = ((TileEntityMachineElectric)object).getType();
			if(((TileEntityMachineElectric)object).isActive())
				texture += "Active";
		}
		else
			texture = ((String)object);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture("/mods/" + MetalMech.modid + "/textures/models/electric/" + texture + ".png");
		
		// Render Model:
		this.render();
		
		// Pop this matrix from the stack.
		GL11.glPopMatrix();
		
	}

	@Override
	public void renderAll() {
		// TODO Auto-generated method stub
	}
}