package lycanite.metalmech;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.minecraftforge.common.Configuration;

public class Config {
	
	// Feature Control:
	public static boolean furnacesEnabled;
	public static boolean crushersEnabled;
	public static boolean electricMachinesEnabled;
	public static boolean alternateRecipesEnabled;
	
	public static void init() {
		/*
		 * Metal Mechanics creates 2 configuration files. This one for the machines and recipes and a Metallurgy one for the ores, etc.
		 */
		
		// Create Config File:
		File configFile = new File(MetalMech.proxy.getMinecraftDir() + "/config");
		configFile.mkdir();
		File newConfigFile = new File(MetalMech.proxy.getMinecraftDir() + "/config/MetalMechanics.cfg");
	    try {
	    	newConfigFile.createNewFile();
	    	System.out.println("[MetalMech] Successfully created/read configuration file");
	    }
		catch (IOException e) {
	    	System.out.println("[MetalMech] Could not create configuration file:");
	    	System.out.println(e);
		}
	    
	    // Load Config File:
		Configuration config = new Configuration(newConfigFile);
		config.load();
	    
		// Block IDs:
	    MachineManager.setMachineBlockID("Furnace", config.getBlock("Coal Furnace", 2555).getInt(2555));
	    MachineManager.setMachineBlockID("Crusher", config.getBlock("Coal Crusher", 2556).getInt(2556));
	    MachineManager.setMachineBlockID("Electric", config.getBlock("Electric Machines", 2557).getInt(2557));
	
	    // Features:
	    furnacesEnabled = config.get("Machines", "Enable Furnaces", true).getBoolean(true);
	    crushersEnabled = config.get("Machines", "Enable Crushers", true).getBoolean(true);
	    electricMachinesEnabled = config.get("Machines", "Enable Electric Crushers", true).getBoolean(true);
	    alternateRecipesEnabled = config.get("Universal Electricity", "Enable Alternate Recipes", true).getBoolean(true);
	    
	    // Machine Properties:
	    MachineManager.setSpeed("LeadFurnace", (int)(config.get("Machines", "Lead Furnace Speed", 7000).getInt(7000) / 1000.0F));
	    MachineManager.setSpeed("AluminiumFurnace", (int)(config.get("Machines", "Aluminium Furnace Speed", 6000).getInt(6000) / 1000.0F));
	    MachineManager.setSpeed("TitaniumFurnace", (int)(config.get("Machines", "Titanium Furnace Speed", 5000).getInt(5000) / 1000.0F));

	    MachineManager.setSpeed("LeadCrusher", (int)(config.get("Machines", "Lead Crusher Speed", 7000).getInt(7000) / 500.0F));
	    MachineManager.setSpeed("AluminiumCrusher", (int)(config.get("Machines", "Aluminium Crusher Speed", 6000).getInt(6000) / 500.0F));
	    MachineManager.setSpeed("TitaniumCrusher", (int)(config.get("Machines", "Titanium Crusher Speed", 5000).getInt(5000) / 500.0F));
	    
	    MachineManager.setSpeed("Crusher", (int)(config.get("Machines", "Electric Crusher Speed", 5000).getInt(5000) / 500));
	    MachineManager.setSpeed("Extractor", (int)(config.get("Machines", "Electric Extractor Speed", 5000).getInt(5000) / 500));
	    MachineManager.setSpeed("Compressor", (int)(config.get("Machines", "Electric Compressor Speed", 5000).getInt(5000) / 500));
	    MachineManager.setSpeed("Sawmill", (int)(config.get("Machines", "Electric Sawmill Speed", 5000).getInt(5000) / 500));

	    MachineManager.setProcessModifier("Crusher", (int)(config.get("Machines", "Crusher Processing Modifier", 2).getInt(2)));
	    MachineManager.setProcessModifier("Extractor", (int)(config.get("Machines", "Extractor Processing Modifier", 2).getInt(2)));
	    MachineManager.setProcessModifier("Compressor", (int)(config.get("Machines", "Compressor Processing Modifier", 2).getInt(2)));
	    MachineManager.setProcessModifier("Sawmill", (int)(config.get("Machines", "Sawmill Processing Modifier", 2).getInt(2)));
	    
	    config.save();
	}
}