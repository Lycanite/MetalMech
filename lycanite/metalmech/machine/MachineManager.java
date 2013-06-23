package lycanite.metalmech.machine;

import java.util.HashMap;
import java.util.Map;

import net.minecraftforge.client.model.IModelCustom;

import lycanite.metalmech.client.IModelRender;

import cpw.mods.fml.common.registry.LanguageRegistry;

public class MachineManager {
	
	// Machine Info:
	public static Map<String, MachineCategory> categories = new HashMap<String, MachineCategory>();
	public static Map<Integer, String> categoriesBlockID = new HashMap<Integer, String>();
	public static Map<String, String> categoriesTypeSearch = new HashMap<String, String>();
	
	// ==================================================
	//                   Add Machines:
	// ==================================================
	// Add Machines:
	public static void addMachines() {
		// Coal Furnaces:
		addMachine("LeadFurnace", "leadFurnace", "Lead Furnace", "Furnace");
		addMachine("AluminiumFurnace", "aluminiumFurnace", "Aluminium Furnace", "Furnace");
		addMachine("TitaniumFurnace", "titaniumFurnace", "Titanium Furnace", "Furnace");
		
		// Coal Crushers:
		addMachine("LeadCrusher", "leadCrusher", "Lead Crusher", "Crusher");
		addMachine("AluminiumCrusher", "aluminiumCrusher", "Aluminium Crusher", "Crusher");
		addMachine("TitaniumCrusher", "titaniumCrusher", "Titanium Crusher", "Crusher");
		
		// Electric Machines:
		addMachine("Crusher", "electricCrusher", "Electric Crusher", "Machine");
		addMachine("Extractor", "electricExtractor", "Electric Extractor", "Machine");
		addMachine("Compressor", "electricCompressor", "Electric Compressor", "Machine");
		addMachine("Sawmill", "electricSawmill", "Electric Sawmill", "Machine");
		addMachine("Furnace", "electricFurnace", "Electric Furnace", "Machine");
		
		// Generators:
		addMachine("ThermalGenerator", "thermalGenerator", "Thermal Generator", "Generator");
		
		// Batteries:
		addMachine("BatteryArray", "batteryArray", "Battery Array", "Battery");
		
		// Wires:
		addMachine("CopperWire", "copperWire", "Copper Wire", "Wire"); // MV Lossy
		
		addMachine("TinWire", "tinWire", "Tin Wire", "Wire"); // LV
		addMachine("AluminiumWire", "aluminiumWire", "Aluminium Wire", "Wire");
		
		addMachine("GoldWire", "goldWire", "Gold Wire", "Wire"); // MV
		addMachine("SilverWire", "silverWire", "Silver Wire", "Wire");
		
		addMachine("ElectrumWire", "electrumWire", "Electrum Wire", "Wire"); // HV
		addMachine("PlatinumWire", "platinumWire", "Platinum Wire", "Wire");
		
		addMachine("VyroxeresWire", "vyroxeresWire", "Vyroxeres Wire", "Wire"); // EV
		addMachine("MidasiumWire", "midasiumWire", "Midasium Wire", "Wire");
		
		addMachine("DesichalkosWire", "desichalkosWire", "Desichalkos Wire", "Wire"); // UV
	}
	
	
	// Add Machine:
	public static void addMachine(String name, String blockName, String title, String category) {
		// Generate Machine Info:
		MachineInfo newMachineInfo = new MachineInfo(name, blockName, title);
		
		// Add String Localisation:
		LanguageRegistry.instance().addStringLocalization(name + ".name", title);
		
		// Add To Category:
		if(categories.get(category) == null)
			addMachineCategory(category);
		categoriesTypeSearch.put(name, category);
		categories.get(category).addMachine(name, newMachineInfo);
	}
	
	
	// ==================================================
	//                  Machine Blocks
	// ==================================================
	// Set Machine Block ID:
	public static void addMachineCategory(String categoryName) {
		MachineCategory category = new MachineCategory();
		category.name = categoryName;
		categories.put(categoryName, category);
	}
	
	
	// Set Machine Block ID:
	public static void setMachineBlockID(String category, int blockID) {
		categoriesBlockID.put(blockID, category);
		categories.get(category).blockID = blockID;
		
	}
	
	
	// Get Machine Block ID:
	public static int getMachineBlockID(String category) {
		return categories.get(category).blockID;
	}
	
	
	// Get Machine Category:
	public static MachineCategory getCategory(int blockID) {
		return categories.get(categoriesBlockID.get(blockID));
	}
	public static MachineCategory getCategory(String type) {
		return categories.get(categoriesTypeSearch.get(type));
	}
	
	
	// Get Machine Sub Blocks Amount:
	public static int getSubBlocks(String category) {
		return categories.get(category).machineCount;
	}
	
	
	// ==================================================
	//                   Machine Info
	// ==================================================
	// ========== Get Info ==========
	public static MachineInfo getMachineInfo(String category, int metadata) {
		return categories.get(category).getMachine(metadata);
	}
	public static MachineInfo getMachineInfo(String category, String type) {
		return categories.get(category).getMachine(type);
	}
	public static MachineInfo getMachineInfo(int blockID, int metadata) {
		return getMachineInfo(categoriesBlockID.get(blockID), metadata);
	}
	public static MachineInfo getMachineInfo(String type) {
		return getMachineInfo(categoriesTypeSearch.get(type), type);
	}
	
	// Get Machine Name From Type:
	public static String getName(String type) {
		return getCategory(type).machines.get(type).name;
	}
	public static String getName(int metadata, String category) {
		return getName(getType(metadata, category));
	}
	
	
	// Get Machine Title From Type:
	public static String getTitle(String type) {
		return getCategory(type).machines.get(type).title;
	}
	public static String getTitle(int metadata, String category) {
		return getTitle(getType(metadata, category));
	}
	
	
	// Get Machine Metadata From Type:
	public static int getMetadata(String type) {
		return getCategory(type).machines.get(type).metadata;
	}
	
	
	// Get Machine Type From Metadata and Category:
	public static String getType(int metadata, String category) {
		if(categories.get(category).getMachine(metadata) != null)
			return categories.get(category).getMachine(metadata).name;
		else return categories.get(category).getMachine(0).name;
	}
	
	
	// ==================================================
	//                 Machine Properties
	// ==================================================
	// Set Machine Speed:
	public static void setSpeed(String type, String category, int speed) {
		getCategory(type).machines.get(type).processTimeInit = speed * 20;
	}
	

	// Set Machine Process Modifier:
	public static void setProcessModifier(String type, int processModifier) {
		getCategory(type).machines.get(type).processModifier = processModifier;
	}
	public static void setProcessModifier(int metadata, String category, int processModifier) {
		setProcessModifier(getType(metadata, category), processModifier);
	}
	

	// Get Machine Process Modifier:
	public static int getProcessModifier(String type) {
		return getCategory(type).machines.get(type).processModifier;
	}
	public static int getProcessModifier(int metadata, String category) {
		return getProcessModifier(getType(metadata, category));
	}
	
	
	// ==================================================
	//                 Machine Models
	// ==================================================
	// Set Machine Model:
	public static void setModel(String type, IModelCustom model) {
		getCategory(type).machines.get(type).model = model;
	}
	public static void setModel(int metadata, String category, IModelCustom model) {
		setModel(getType(metadata, category), model);
	}
	
	
	// Get Machine Model:
	public static IModelCustom getModel(String type) {
		return getCategory(type).machines.get(type).model;
	}
	public static IModelCustom getModel(int metadata, String category) {
		return getModel(getType(metadata, category));
	}
	
	
	// Set Machine Texture:
	public static void setTexture(String type, String texture) {
		getCategory(type).machines.get(type).texture = texture;
	}
	public static void setTexture(int metadata, String category, String texture) {
		setTexture(getType(metadata, category), texture);
	}
	
	
	// Get Machine Texture:
	public static String getTexture(String type) {
		return getCategory(type).machines.get(type).texture;
	}
	public static String getTexture(int metadata, String category) {
		return getTexture(getType(metadata, category));
	}
}