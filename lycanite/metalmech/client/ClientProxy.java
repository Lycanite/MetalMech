package lycanite.metalmech.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import lycanite.metalmech.CommonProxy;
import lycanite.metalmech.MetalMech;
import lycanite.metalmech.tileentity.TileEntityElectricBattery;
import lycanite.metalmech.tileentity.TileEntityElectricMachine;
import lycanite.metalmech.tileentity.TileEntityMachine;
import lycanite.metalmech.tileentity.TileEntityMachineCrusher;
import lycanite.metalmech.tileentity.TileEntityElectricBase;
import lycanite.metalmech.tileentity.TileEntityElectricGenerator;
import lycanite.metalmech.tileentity.TileEntityWire;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {
	
	// Render ID:
	public static int RENDER_ID = RenderingRegistry.getNextAvailableRenderId();
	
	
	// Register Old Models:
	@Override
	public void registerModels() {
		MetalMech.models.put("Crusher", new ModelCrusher());
	}
	
	
	// Register Tile Entities:
	@Override
	public void registerTileEntities() {
		ClientRegistry.registerTileEntity(TileEntityMachineCrusher.class, "TileEntityMachineCrusher", new RenderMachineCrusher());
		ClientRegistry.registerTileEntity(TileEntityElectricMachine.class, "TileEntityElectricMachine", new RenderMachineElectric());
		ClientRegistry.registerTileEntity(TileEntityElectricGenerator.class, "TileEntityElectricGenerator", new RenderMachineElectric());
		ClientRegistry.registerTileEntity(TileEntityElectricBattery.class, "TileEntityElectricBattery", new RenderMachineElectric());
		ClientRegistry.registerTileEntity(TileEntityWire.class, "TileEntityWire", new RenderWire());
	}
	
	
	// Register Renderer Handlers:
	@Override
    public void registerRenderInformation() {
		MinecraftForgeClient.registerItemRenderer(MetalMech.machineBlockCrusher.blockID, new ItemRenderingHandler());
		MinecraftForgeClient.registerItemRenderer(MetalMech.machineBlockElectric.blockID, new ItemRenderingHandler());
		MinecraftForgeClient.registerItemRenderer(MetalMech.generatorBlockElectric.blockID, new ItemRenderingHandler());
		MinecraftForgeClient.registerItemRenderer(MetalMech.batteryBlockElectric.blockID, new ItemRenderingHandler());
		MinecraftForgeClient.registerItemRenderer(MetalMech.wireBlock.blockID, new ItemRenderingHandler());
		RenderingRegistry.registerBlockHandler(new BlockRenderingHandler());
    }
}