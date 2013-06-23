package lycanite.metalmech;

import java.io.File;

import lycanite.metalmech.tileentity.TileEntityElectricBattery;
import lycanite.metalmech.tileentity.TileEntityElectricMachine;
import lycanite.metalmech.tileentity.TileEntityMachine;
import lycanite.metalmech.tileentity.TileEntityMachineCrusher;
import lycanite.metalmech.tileentity.TileEntityElectricBase;
import lycanite.metalmech.tileentity.TileEntityElectricGenerator;
import lycanite.metalmech.tileentity.TileEntityWire;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {
	
	
	// Register Tile Entities:
	public void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityMachineCrusher.class, "TileEntityMachineCrusher");
		GameRegistry.registerTileEntity(TileEntityElectricMachine.class, "TileEntityElectricMachine");
		GameRegistry.registerTileEntity(TileEntityElectricGenerator.class, "TileEntityElectricGenerator");
		GameRegistry.registerTileEntity(TileEntityElectricBattery.class, "TileEntityElectricBattery");
		GameRegistry.registerTileEntity(TileEntityWire.class, "TileEntityWire");
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
