package lycanite.metalmech.client;

import lycanite.metalmech.MetalMech;
import lycanite.metalmech.machine.MachineInfo;
import lycanite.metalmech.machine.MachineManager;
import lycanite.metalmech.tileentity.TileEntityElectricBase;
import lycanite.metalmech.tileentity.TileEntityWire;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;

import net.minecraft.client.model.ModelBase;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

public class ModelWire extends ModelBase {

	public ModelWire() {
		
	}
	
	private void renderModel(IModelCustom model) {
		model.renderAll();
	}
	
	public void render(Object object, double x, double y, double z, boolean[] connections) {
		// Get Texture:
		MachineInfo machineInfo;
		String textureState = "";
		if(object instanceof TileEntityWire) {
			machineInfo = ((TileEntityWire)object).getInfo();
			// textureState can change here
		}
		else
			machineInfo = MachineManager.getMachineInfo(((String)object));
		String type = machineInfo.name;
		String category = machineInfo.category.name;
		
		// Load Models and Texture:
		if(machineInfo.model == null) {
			machineInfo.model = AdvancedModelLoader.loadModel("/mods/" + MetalMech.modid + "/models/WireCenter.obj");
			machineInfo.subModels = new IModelCustom[1];
			machineInfo.subModels[0] = AdvancedModelLoader.loadModel("/mods/" + MetalMech.modid + "/models/WireCable.obj");
			
			try {
				machineInfo.texture = "/mods/" + MetalMech.modid + "/textures/blocks/wires/" + type;
			}
			catch(Exception e) {
				System.out.println("[MetalMech] WARNING: Could not find a texture for " + type + " using the CopperWire texture instead.");
				machineInfo.texture = "/mods/" + MetalMech.modid + "/textures/blocks/wires/CopperWire";
			}
		}
		
		// Render Models:
		String renderTexture = machineInfo.texture + textureState + ".png";
		renderModel(machineInfo.model, renderTexture, x, y, z, (short)0, 1f, 0f, 0f);
		if(connections[0]) // Bottom
			renderModel(machineInfo.subModels[0], renderTexture, x, y, z, (short)90, 1f, 0f, 0f);
		if(connections[1]) // Top
			renderModel(machineInfo.subModels[0], renderTexture, x, y, z, (short)90, -1f, 0f, 0f);
		if(connections[2]) // Front
			renderModel(machineInfo.subModels[0], renderTexture, x, y, z, (short)180, 0f, 1f, 0f);
		if(connections[3]) // Back
			renderModel(machineInfo.subModels[0], renderTexture, x, y, z, (short)0, 0f, 1f, 0f);
		if(connections[4]) // Left
			renderModel(machineInfo.subModels[0], renderTexture, x, y, z, (short)90, 0f, -1f, 0f);
		if(connections[5]) // Right
			renderModel(machineInfo.subModels[0], renderTexture, x, y, z, (short)90, 0f, 1f, 0f);
	}
	
	public void renderModel(IModelCustom model, String texture, double x, double y, double z, short rotation, float axisX, float axisY, float axisZ) {
		// Start Positioning:
		GL11.glPushMatrix();
		
		// Position Model:
		GL11.glTranslatef((float)x + 0.5f, (float)y + 0.5f, (float)z + 0.5f);
		
		// Scale Model:
		GL11.glScalef(0.5f, 0.5f, 0.5f);
		
		// Rotate Model:
		GL11.glRotatef(rotation, axisX, axisY, axisZ);
		
		// Render Model:
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);
		renderModel(model);
		
		// Pop Matrix:
		GL11.glPopMatrix();
	}
}