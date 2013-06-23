package lycanite.metalmech.machine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import universalelectricity.core.vector.Vector2;

import lycanite.metalmech.MetalMech;

import net.minecraftforge.client.model.IModelCustom;

public class MachineInfo {
	
	// ========== Details ==========
	public String name = "MachineName";
	public String blockName = "machineType";
	public String title = "Machine Title";
	public int metadata = 0;
	public MachineCategory category;
	
	
	// ========== Machine Stats ==========
	public int processTimeInit = 5000; // How long the machine must process for.
	public int processModifier = 2; // Changes how much the process outputs. Used by recipes.
	public int voltage = MetalMech.mediumVoltage;
	
	
	// ========== Wire Stats ==========
	public double resistance = 0.05;
	public double capacity = 200;
	
	
	// ========== Model ==========
	public IModelCustom model;
	public IModelCustom[] subModels;
	public String texture;
	
	
	// ========== Constructor ==========
	public MachineInfo(String setName, String setBlockName, String setTitle) {
		name = setName;
		blockName = setBlockName;
		title = setTitle;
	}
	
	
	// ========== Inventory ==========
	public static Map<Integer, Object[]> getInventoryLookup(Map<String, MachineInventory> inventory) {
		Map<Integer, Object[]> inventoryLookup = new HashMap<Integer, Object[]>();
		for(MachineInventory inventoryValue : inventory.values())
			for(int i = 0; i < inventoryValue.getSize(); i++) {
				inventoryLookup.put(inventoryValue.slotIndexStart + i, new Object[] {inventoryValue.name, i});
			}
		return inventoryLookup;
	}
	
	
	// ========== Update Stats ==========
	public void updateStats() {
		// Wire Stats:
		if(category.name == "Wire") {
			if(name == "CopperWire") {
				resistance = 0.1;
				capacity = MetalMech.mediumCapacity;
				voltage = MetalMech.mediumVoltage;
			}
			else if(name == "TinWire" || name == "AluminiumWire") {
				resistance = 0.01;
				capacity = MetalMech.lowCapacity;
				voltage = MetalMech.lowVoltage;
			}
			else if(name == "GoldWire" || name == "SilverWire") {
				resistance = 0.05;
				capacity = MetalMech.mediumCapacity;
				voltage = MetalMech.mediumVoltage;
			}
			else if(name == "ElectrumWire" || name == "PlatinumWire") {
				resistance = 0.1;
				capacity = MetalMech.highCapacity;
				voltage = MetalMech.highVoltage;
			}
			else if(name == "MidasiumWire" || name == "VyroxeresWire") {
				resistance = 0.1;
				capacity = MetalMech.extremeCapacity;
				voltage = MetalMech.extremeVoltage;
			}
			else if(name == "DesichalkosWire") {
				resistance = 0.2;
				capacity = Double.MAX_VALUE;
				voltage = Integer.MAX_VALUE;
			}
		}
	}
}
