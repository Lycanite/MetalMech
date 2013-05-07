package lycanite.metalmech;

import java.io.File;

import lycanite.metalmech.block.TileEntityMachine;
import lycanite.metalmech.block.TileEntityMachineCrusher;
import lycanite.metalmech.block.TileEntityMachineElectric;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {
	
	
	// Register Tile Entities:
	public void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityMachineCrusher.class, "TileEntityMachineCrusher");
		GameRegistry.registerTileEntity(TileEntityMachineElectric.class, "TileEntityMachineElectric");
	}
	
	
	// Client Nulls:
    public void registerModels() {
    	// Client only
    }
    public void registerRenderInformation() {
    	// Client only
    }
    
    // Get Minecraft Directory:
    public File getMinecraftDir() {
      return new File(".");
    }
}
