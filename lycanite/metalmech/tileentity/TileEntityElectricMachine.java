package lycanite.metalmech.tileentity;

import java.util.LinkedHashMap;

import universalelectricity.core.vector.Vector2;
import lycanite.metalmech.MetalMech;
import lycanite.metalmech.machine.MachineInfo;
import lycanite.metalmech.machine.MachineInventory;

public class TileEntityElectricMachine extends TileEntityElectricBase {
	
	// Base Stats:
	public static int baseProcessSpeed = 1;
	public static int baseFuelSpeed = 0;
	public static float baseFuelWarmup = 6f;
	public static double baseVoltage = MetalMech.mediumVoltage;
	public static double baseWattCost = 480d;
	public static double baseWattGeneration = 0d;
	public static double baseWattGenerationThreshhold = 0.1d;
	public static double baseJouleStorage = 0d;
	
	
	// ========== Constructor ===========
	public TileEntityElectricMachine() {
		inventory = new LinkedHashMap<String, MachineInventory>() {{
			put("Input", new MachineInventory("Input", 1, null, new Vector2(55, 25)));
			put("Output", new MachineInventory("Output", 1, get("Input"), new Vector2(108, 25)));
			put("Battery", new MachineInventory("Battery", 1, get("Output"), new Vector2(55, 49)));
		}};
		setInventoryLookup();
		processSpeed = baseProcessSpeed;
		fuelSpeed = baseFuelSpeed;
		fuelWarmup = baseFuelWarmup;
		voltage = baseVoltage;
		wattCost = baseWattCost;
		wattGeneration = baseWattGeneration;
		wattGenerationThreshhold = baseWattGenerationThreshhold;
		jouleStorage = baseJouleStorage;
	}
	
	
	// Get Machine Category:
	@Override
	public String getCategory() {
		return "Machine";
	}
}
