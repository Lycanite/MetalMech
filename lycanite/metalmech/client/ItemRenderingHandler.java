package lycanite.metalmech.client;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;

import lycanite.metalmech.MachineManager;
import lycanite.metalmech.MetalMech;
import lycanite.metalmech.tileentity.TileEntityMachine;
import lycanite.metalmech.tileentity.TileEntityMachineElectric;
import net.minecraft.client.model.ModelBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderingHandler implements IItemRenderer {
	
	// Handle render type:
	@Override
	public boolean handleRenderType(ItemStack itemStack, ItemRenderType type) {
		if(itemStack.itemID == MetalMech.machineBlockCrusher.blockID) return true;
		else if(itemStack.itemID == MetalMech.machineBlockElectric.blockID) return true;
		return false;
	}
	
	
	// Check if should use:
	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}
	
	
	// Render item:
	ModelSawmill modelSawmill = new ModelSawmill(); // TEST XXX
	@Override
	public void renderItem(ItemRenderType type, ItemStack itemStack, Object... data) {
		if(type == ItemRenderType.EQUIPPED)
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		
		GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		GL11.glTranslatef(0, 1.0F, 1.0F);
		GL11.glScalef(1.0F, -1F, -1F);
		
		if(itemStack.itemID == MetalMech.machineBlockCrusher.blockID) {
			String machineRank = MachineManager.getType(itemStack.getItemDamage(), "Crusher");
			GL11.glBindTexture(3553, FMLClientHandler.instance().getClient().renderEngine.getTexture("/mods/" + MetalMech.modid + "/textures/models/crusher/" + machineRank + ".png"));
			MetalMech.models.get("Crusher").renderAll();
		}
		else if(itemStack.itemID == MetalMech.machineBlockElectric.blockID) {
			String machineType = MachineManager.getType(itemStack.getItemDamage(), "Electric");
			if(machineType != "Sawmill") { // XXX
				GL11.glBindTexture(3553, FMLClientHandler.instance().getClient().renderEngine.getTexture("/mods/" + MetalMech.modid + "/textures/models/electric/" + machineType + ".png"));
				MetalMech.models.get(machineType).renderAll();
			}
			else
				modelSawmill.render(MachineManager.getType(itemStack.getItemDamage(), "Electric"), 0f, 0f, 0f, (short)0);
		}
	}
}
