package lycanite.metalmech.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import lycanite.metalmech.CommonProxy;
import lycanite.metalmech.MetalMech;
import lycanite.metalmech.block.TileEntityMachine;
import lycanite.metalmech.block.TileEntityMachineCrusher;
import lycanite.metalmech.block.TileEntityMachineElectric;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {
	
	// Render ID:
	public static int RENDER_ID = RenderingRegistry.getNextAvailableRenderId();
	
	
	// Register Tile Entities:
	@Override
	public void registerModels() {
		MetalMech.models.put("Crusher", new ModelCrusher());
		MetalMech.models.put("Extractor", new ModelExtractor());
		MetalMech.models.put("Compressor", new ModelCompressor());
	}
	
	
	// Register Tile Entities:
	@Override
	public void registerTileEntities() {
		ClientRegistry.registerTileEntity(TileEntityMachineCrusher.class, "TileEntityMachineCrusher", new RenderMachineCrusher());
		ClientRegistry.registerTileEntity(TileEntityMachineElectric.class, "TileEntityMachineElectric", new RenderMachineElectric());
	}
	
	
	// Register Renderer Handlers:
	@Override
    public void registerRenderInformation() {
		MinecraftForgeClient.registerItemRenderer(MetalMech.machineBlockCrusher.blockID, new ItemRenderingHandler());
		MinecraftForgeClient.registerItemRenderer(MetalMech.machineBlockElectric.blockID, new ItemRenderingHandler());
		RenderingRegistry.registerBlockHandler(new BlockRenderingHandler());
    }
}