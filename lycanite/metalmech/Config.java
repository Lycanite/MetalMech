package lycanite.metalmech;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import lycanite.metalmech.machine.MachineManager;

import net.minecraftforge.common.Configuration;

public class Config {
	
	// Feature Control:
	public static boolean furnacesEnabled;
	public static boolean crushersEnabled;
	public static boolean electricMachinesEnabled;
	public static boolean modularMachinesEnabled;
	public static boolean electricGeneratorsEnabled;
	public static boolean modularGeneratorsEnabled;
	public static boolean electricBatteriesEnabled;
	public static boolean modularBatteriesEnabled;
	public static boolean wiresEnabled;
	
	public static boolean componentsEnabled;
	public static boolean platesEnabled;
	public static boolean batteryEnabled;
	public static boolean wrenchEnabled;
	
	public static int componentsID;
	public static int platesID;
	public static int batteryID;
	public static int wrenchID;
	
	public static boolean alternateRecipesEnabled;
	
	public static void init(File configFile) {
		/*
		 * Metal Mechanics creates 2 configuration files. This one for the machines and recipes and a Metallurgy one for the ores, etc.
		 */
		
		// Create Config File:
		//File configFile = new File(MetalMech.proxy.getMinecraftDir() + "/config");
		//configFile.mkdir();
		//File newConfigFile = new File(MetalMech.proxy.getMinecraftDir() + "/config/MetalMechanics.cfg");
		File newConfigFile = new File(configFile, "MetalMechanics.cfg");
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
	    MachineManager.setMachineBlockID("Furnace", config.getBlock("Coal Furnaces", 2555).getInt(2555));
	    MachineManager.setMachineBlockID("Crusher", config.getBlock("Coal Crushers", 2556).getInt(2556));
	    MachineManager.setMachineBlockID("Machine", config.getBlock("Electric Machines", 2557).getInt(2557));
	    MachineManager.setMachineBlockID("Generator", config.getBlock("Electric Generators", 2558).getInt(2558));
	    MachineManager.setMachineBlockID("Battery", config.getBlock("Electric Batteries", 2559).getInt(2559));
	    
	    MachineManager.setMachineBlockID("Wire", config.getBlock("Wires", 2560).getInt(2560));
	    
	    //MachineManager.setMachineBlockID("ModMachine", config.getBlock("Modular Machines", 25661).getInt(2561));
	    //MachineManager.setMachineBlockID("ModGenerator", config.getBlock("Modular Generators", 2562).getInt(2562));
	    //MachineManager.setMachineBlockID("ModGenerator", config.getBlock("Modular Battery Array", 2563).getInt(2563));
	    
		// Item IDs:
	    componentsID = config.getItem("Component Items ID", 25000).getInt(25000);
	    platesID = config.getItem("Metal Plates ID", 25001).getInt(25001);
	    batteryID = config.getItem("Battery ID (Reserves 3 IDs)", 25002).getInt(25002);
	    wrenchID = config.getItem("Wrench ID (Reserves 2 IDs)", 25005).getInt(25005);
	
	    // Features:
	    furnacesEnabled = config.get("Machines", "Enable Furnaces", true).getBoolean(true);
	    crushersEnabled = config.get("Machines", "Enable Crushers", true).getBoolean(true);
	    electricMachinesEnabled = config.get("Machines", "Enable Electric Machines", true).getBoolean(true);
	    modularMachinesEnabled = config.get("Machines", "Enable Modular Machines", true).getBoolean(true);
	    electricGeneratorsEnabled = config.get("Machines", "Enable Electric Generators", true).getBoolean(true);
	    modularGeneratorsEnabled = config.get("Machines", "Enable Modular Generators", true).getBoolean(true);
	    electricBatteriesEnabled = config.get("Machines", "Enable Electric Battery Array", true).getBoolean(true);
	    modularBatteriesEnabled = config.get("Machines", "Enable Modular Battery Array", true).getBoolean(true);
	    wiresEnabled = config.get("Machines", "Enable Wires", true).getBoolean(true);

	    componentsEnabled = config.get("Items", "Enable Component Items", true).getBoolean(true);
	    platesEnabled = config.get("Items", "Enable Metal Plates", true).getBoolean(true);
	    batteryEnabled = config.get("Items", "Enable Batteries", true).getBoolean(true);
	    wrenchEnabled = config.get("Items", "Enable Wrench", true).getBoolean(true);
	    
	    alternateRecipesEnabled = config.get("Universal Electricity", "Enable Alternate Recipes", true).getBoolean(true);
	    
	    // Machine Properties:
	    MachineManager.setSpeed("LeadFurnace", "Furnace", (int)(config.get("Machines", "Lead Furnace Time", 7000).getInt(7000) / 1000.0F));
	    MachineManager.setSpeed("AluminiumFurnace", "Furnace", (int)(config.get("Machines", "Aluminium Furnace Time", 6000).getInt(6000) / 1000.0F));
	    MachineManager.setSpeed("TitaniumFurnace", "Furnace", (int)(config.get("Machines", "Titanium Furnace Time", 5000).getInt(5000) / 1000.0F));

	    MachineManager.setSpeed("LeadCrusher", "Crusher", (int)(config.get("Machines", "Lead Crusher Time", 7000).getInt(7000) / 500.0F));
	    MachineManager.setSpeed("AluminiumCrusher", "Crusher", (int)(config.get("Machines", "Aluminium Crusher Time", 6000).getInt(6000) / 500.0F));
	    MachineManager.setSpeed("TitaniumCrusher", "Crusher", (int)(config.get("Machines", "Titanium Crusher Time", 5000).getInt(5000) / 500.0F));
	    
	    MachineManager.setSpeed("Crusher", "Machine", (int)(config.get("Machines", "Electric Crusher Time", 5000).getInt(5000) / 500));
	    MachineManager.setSpeed("Extractor", "Machine", (int)(config.get("Machines", "Electric Extractor Time", 5000).getInt(5000) / 500));
	    MachineManager.setSpeed("Compressor", "Machine", (int)(config.get("Machines", "Electric Compressor Time", 5000).getInt(5000) / 500));
	    MachineManager.setSpeed("Sawmill", "Machine", (int)(config.get("Machines", "Electric Sawmill Time", 5000).getInt(5000) / 500));
	    MachineManager.setSpeed("Furnace", "Machine", (int)(config.get("Machines", "Electric Furnace Time", 5000).getInt(5000) / 500));

	    MachineManager.setProcessModifier("Crusher", (int)(config.get("Machines", "Crusher Recipe Modifier", 2).getInt(2)));
	    MachineManager.setProcessModifier("Extractor", (int)(config.get("Machines", "Extractor Recipe Modifier", 2).getInt(2)));
	    MachineManager.setProcessModifier("Compressor", (int)(config.get("Machines", "Compressor Recipe Modifier", 2).getInt(2)));
	    MachineManager.setProcessModifier("Sawmill", (int)(config.get("Machines", "Sawmill Recipe Modifier", 2).getInt(2)));
	    MachineManager.setProcessModifier("Furnace", (int)(config.get("Machines", "Furnace Recipe Modifier", 2).getInt(2)));
	    
	    config.save();
	}
}