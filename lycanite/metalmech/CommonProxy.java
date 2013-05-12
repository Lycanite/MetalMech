package lycanite.metalmech;

import java.io.File;

import lycanite.metalmech.tileentity.TileEntityMachine;
import lycanite.metalmech.tileentity.TileEntityMachineCrusher;
import lycanite.metalmech.tileentity.TileEntityMachineElectric;
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
