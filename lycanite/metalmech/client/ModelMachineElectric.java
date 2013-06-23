package lycanite.metalmech.client;

import lycanite.metalmech.MetalMech;
import lycanite.metalmech.machine.MachineManager;
import lycanite.metalmech.tileentity.TileEntityElectricBase;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;

import net.minecraft.client.model.ModelBase;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class ModelMachineElectric extends ModelBase {

	public ModelMachineElectric() {
		
	}
	
	private void renderModel(IModelCustom model) {
		model.renderAll();
	}
	
	public void render(Object object, double x, double y, double z, short rotation) {
		// Start Positioning:
		GL11.glPushMatrix();
		
		// Position Model:
		GL11.glTranslatef((float)x + 0.5f, (float)y + 0.5f, (float)z + 0.5f);
		
		// Scale Model:
		GL11.glScalef(0.5f, 0.5f, 0.5f);
		
		// Rotate Model:
		if(object instanceof TileEntityElectricBase)
			GL11.glRotatef(rotation, 0.0F, 1.0F, 0.0F);
		else
			GL11.glRotatef(180f, 1.0F, 0.0F, 0.0F);
		
		// Bind Texture:
		String type = "";
		String category = "";
		String texture = "";
		if(object instanceof TileEntityElectricBase) {
			type = ((TileEntityElectricBase)object).getType();
			category = ((TileEntityElectricBase)object).getCategory();
			if(((TileEntityElectricBase)object).isActive())
				texture += "Active";
			if(((TileEntityElectricBase)object).isReceiving())
				texture += "Charge";
		}
		else {
			type = ((String[])object)[0];
			category = ((String[])object)[1];
		}
		
		// Load Model and Texture:
		if(MachineManager.getModel(type) == null) {
			try {
				MachineManager.setModel(type, AdvancedModelLoader.loadModel("/mods/" + MetalMech.modid + "/models/" + type + ".obj"));
				MachineManager.setTexture(type, "/mods/" + MetalMech.modid + "/textures/blocks/machines/" + type);
			}
			catch(Exception e) {
				System.out.println("[MetalMech] WARNING: Could not find a model for " + type + " using a placeholder model instead.");
				MachineManager.setModel(type, AdvancedModelLoader.loadModel("/mods/" + MetalMech.modid + "/models/MachineElectricBase.obj"));
				MachineManager.setTexture(type, "/mods/" + MetalMech.modid + "/textures/blocks/machines/MachineElectricBase");
			}
		}
		
		// Render Model:
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(MachineManager.getTexture(type) + texture + ".png");
		renderModel(MachineManager.getModel(type));
		
		// Pop this matrix from the stack.
		GL11.glPopMatrix();
		
	}
}