package lycanite.metalmech;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.minecraftforge.common.Configuration;

public class Config {
	// IDs:
	public static int furnaceID;
	public static int crusherID;
	public static int electricCrusherID;
	  
	// Features:
	public static boolean furnacesEnabled;
	public static boolean crushersEnabled;
	public static boolean electricMachinesEnabled;
	public static boolean alternateRecipesEnabled;
	
	// Machine Properties:
	public static Map<String, Integer> machineSpeed = new HashMap<String, Integer>();
	public static Map<String, Integer> electricMachineSpeed = new HashMap<String, Integer>();
	
	public static void init() {
		// Metal Mechanics creates 2 configuration files. This one fir the machines and recipes and a Metallurgy one for the ores, etc.
		
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
	    furnaceID = config.getBlock("Furnace", 2555).getInt(2555);
	    crusherID = config.getBlock("Crusher", 2556).getInt(2556);
	    electricCrusherID = config.getBlock("Electric Machines", 2557).getInt(2557);
	
	    // Features:
	    furnacesEnabled = config.get("Machines", "Enable Furnaces", true).getBoolean(true);
	    crushersEnabled = config.get("Machines", "Enable Crushers", true).getBoolean(true);
	    electricMachinesEnabled = config.get("Machines", "Enable Electric Crushers", true).getBoolean(true);
	    alternateRecipesEnabled = config.get("Universal Electricity", "Enable Alternate Recipes", true).getBoolean(true);
	    
	    // Machine Properties:
	    machineSpeed.put("Lead", (int)(config.get("Machines", "Bronze Speed", 7000).getInt(7000) / 1000.0F));
	    machineSpeed.put("Aluminium", (int)(config.get("Machines", "Iron Speed", 6000).getInt(6000) / 1000.0F));
	    machineSpeed.put("Titanium", (int)(config.get("Machines", "Titanium Speed", 5000).getInt(5000) / 1000.0F));
	    
	    electricMachineSpeed.put("Crusher", (int)(config.get("Machines", "Electric Crusher Speed", 5000).getInt(5000) / 1000));
	    electricMachineSpeed.put("Extractor", (int)(config.get("Machines", "Electric Extractor Speed", 5000).getInt(5000) / 1000));
	    electricMachineSpeed.put("Compressor", (int)(config.get("Machines", "Electric Compressor Speed", 5000).getInt(5000) / 1000));
	
	    config.save();
	}
}