package lycanite.metalmech;

import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.common.registry.LanguageRegistry;

public class MachineManager {
	
	// Machine Block IDs:
	public static Map<String, Integer> machineBlockID = new HashMap<String, Integer>();
	public static Map<Integer, String> machineCategory = new HashMap<Integer, String>();
	public static Map<String, Integer> machineSubBlocks = new HashMap<String, Integer>();
	
	// Machine Info:
	public static Map<String, MachineInfo> machines = new HashMap<String, MachineInfo>();
	public static Map<String, Map<Integer, MachineInfo>> machinesMetadata = new HashMap<String, Map<Integer, MachineInfo>>();
	
	
	// ==================================================
	//                   Add Machines:
	// ==================================================
	// Add Machines:
	public static void addMachines() {
		// Coal Furnaces:
		addMachine("LeadFurnace", "leadFurnace", "Lead Furnace", 0, "Furnace");
		addMachine("AluminiumFurnace", "aluminiumFurnace", "Aluminium Furnace", 1, "Furnace");
		addMachine("TitaniumFurnace", "titaniumFurnace", "Titanium Furnace", 2, "Furnace");
		
		// Coal Crushers:
		addMachine("LeadCrusher", "leadCrusher", "Lead Crusher", 0, "Crusher");
		addMachine("AluminiumCrusher", "aluminiumCrusher", "Aluminium Crusher", 1, "Crusher");
		addMachine("TitaniumCrusher", "titaniumCrusher", "Titanium Crusher", 2, "Crusher");
		
		// Electric Machines:
		addMachine("Crusher", "electricCrusher", "Electric Crusher", 0, "Electric");
		addMachine("Extractor", "electricExtractor", "Electric Extractor", 1, "Electric");
		addMachine("Compressor", "electricCompressor", "Electric Compressor", 2, "Electric");
		addMachine("Sawmill", "electricSawmill", "Electric Sawmill", 3, "Electric");
	}
	
	
	// Add Machine:
	public static void addMachine(String type, String name, String title, int metadata, String category) {
		// Generate Info:
		MachineInfo newMachineInfo = new MachineInfo();
		newMachineInfo.type = type;
		newMachineInfo.name = name;
		newMachineInfo.title = title;
		newMachineInfo.metadata = metadata;
		
		// Add String Localization:
		LanguageRegistry.instance().addStringLocalization(name + ".name", title);
		
		// Add To Machines List:
		machines.put(type, newMachineInfo);
		
		// Add Metadata Reference:
		if(machinesMetadata.get(category) == null) {
			machinesMetadata.put(category, new HashMap<Integer, MachineInfo>());
			machineSubBlocks.put(category, 1);
		}
		else
			machineSubBlocks.put(category, getSubBlocks(category) + 1);
		machinesMetadata.get(category).put(metadata, newMachineInfo);
	}
	
	
	// ==================================================
	//                  Machine Blocks
	// ==================================================
	// Set Machine Block ID:
	public static void setMachineBlockID(String category, int blockID) {
		machineBlockID.put(category, blockID);
		machineCategory.put(blockID, category);
	}
	
	
	// Get Machine Block ID:
	public static int getMachineBlockID(String category) {
		return machineBlockID.get(category);
	}
	
	
	// Get Machine Category:
	public static String getCategory(int blockID) {
		return machineCategory.get(blockID);
	}
	
	
	// Get Machine Sub Blocks Amount:
	public static int getSubBlocks(String category) {
		return machineSubBlocks.get(category);
	}
	
	
	// ==================================================
	//                   Machine Info
	// ==================================================
	// Get Machine Name From Type:
	public static String getName(String type) {
		return machines.get(type).name;
	}
	public static String getName(int metadata, String category) {
		return getName(getType(metadata, category));
	}
	
	
	// Get Machine Title From Type:
	public static String getTitle(String type) {
		return machines.get(type).title;
	}
	public static String getTitle(int metadata, String category) {
		return getTitle(getType(metadata, category));
	}
	
	
	// Get Machine Metadata From Type:
	public static int getMetadata(String type) {
		return machines.get(type).metadata;
	}
	
	
	// Get Machine Type From Metadata and Category:
	public static String getType(int metadata, String category) {
		if(machinesMetadata.get(category).get(metadata) != null)
			return machinesMetadata.get(category).get(metadata).type;
		else return machinesMetadata.get(category).get(0).type;
	}
	
	
	// ==================================================
	//                 Machine Properties
	// ==================================================
	// Set Machine Speed:
	public static void setSpeed(String type, int speed) {
		machines.get(type).speed = speed;
	}
	public static void setSpeed(int metadata, String category, int speed) {
		setSpeed(getType(metadata, category), speed);
	}
	
	
	// Get Machine Speed:
	public static int getSpeed(String type) {
		return machines.get(type).speed * 20;
	}
	public static int getSpeed(int metadata, String category) {
		return getSpeed(getType(metadata, category));
	}
	

	// Set Machine Process Modifier:
	public static void setProcessModifier(String type, int processModifier) {
		machines.get(type).processModifier = processModifier;
	}
	public static void setProcessModifier(int metadata, String category, int processModifier) {
		setProcessModifier(getType(metadata, category), processModifier);
	}
	

	// Get Machine Process Modifier:
	public static int getProcessModifier(String type) {
		return machines.get(type).processModifier;
	}
	public static int getProcessModifier(int metadata, String category) {
		return getProcessModifier(getType(metadata, category));
	}
}