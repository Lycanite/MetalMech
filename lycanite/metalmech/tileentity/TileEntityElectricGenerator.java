package lycanite.metalmech.tileentity;

import java.util.LinkedHashMap;

import universalelectricity.core.vector.Vector2;
import lycanite.metalmech.MetalMech;
import lycanite.metalmech.machine.MachineInfo;
import lycanite.metalmech.machine.MachineInventory;

public class TileEntityElectricGenerator extends TileEntityElectricBase {
	
	// Base Stats:
	public static int baseProcessSpeed = 0;
	public static int baseFuelSpeed = 10;
	public static float baseFuelWarmup = 0.3f;
	public static double baseVoltage = MetalMech.mediumVoltage;
	public static double baseWattCost = 0d;
	public static double baseWattGeneration = 500d;
	public static double baseWattGenerationThreshhold = 0d;
	public static double baseJouleStorage = 0d;
	
	
	// ========== Constructor ===========
	public TileEntityElectricGenerator() {
		inventory =  new LinkedHashMap<String, MachineInventory>() {{
			put("Fuel", new MachineInventory("Fuel", 1, null, new Vector2(55, 49)));
			put("Charge", new MachineInventory("Charge", 1, get("Fuel"), new Vector2(124, 25)));
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
		return "Generator";
	}
}
