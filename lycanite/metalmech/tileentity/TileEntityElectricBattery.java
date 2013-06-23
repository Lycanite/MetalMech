package lycanite.metalmech.tileentity;

import java.util.EnumSet;
import java.util.LinkedHashMap;

import net.minecraftforge.common.ForgeDirection;

import universalelectricity.core.electricity.ElectricityNetworkHelper;
import universalelectricity.core.vector.Vector2;
import lycanite.metalmech.MetalMech;
import lycanite.metalmech.machine.MachineInventory;

public class TileEntityElectricBattery extends TileEntityElectricBase {
	
	// Base Stats:
	public static int baseProcessSpeed = 0;
	public static int baseFuelSpeed = 0;
	public static float baseFuelWarmup = 6f;
	public static double baseVoltage = MetalMech.mediumVoltage;
	public static double baseWattCost = 500d;
	public static double baseWattGeneration = 10000d;
	public static double baseWattGenerationThreshhold = 0d;
	public static double baseJouleStorage = 5d * 1000000d;
	
	
	// ========== Constructor ===========
	public TileEntityElectricBattery() {
		inventory =  new LinkedHashMap<String, MachineInventory>() {{
			put("Battery", new MachineInventory("Battery", 1,null, new Vector2(24, 49)));
			put("Charge", new MachineInventory("Charge", 1, get("Battery"), new Vector2(24, 25)));
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
		return "Battery";
	}
	
	
	// Can Connect:
	@Override
	public boolean canConnect(ForgeDirection direction) {
		// Input
		if(direction == ForgeDirection.getOrientation(this.facing).getOpposite())
			return true;
		// Outputs
		else if(direction == ForgeDirection.getOrientation(facing))
			return true;
		else if(direction == ForgeDirection.getOrientation(facing).getRotation(ForgeDirection.getOrientation(1)))
			return true;
		else if(direction == ForgeDirection.getOrientation(facing).getRotation(ForgeDirection.getOrientation(1)).getOpposite())
			return true;
		return false;
	}
	
	
	// Input
	protected EnumSet<ForgeDirection> getInputSides() {
		EnumSet<ForgeDirection> inputSides = EnumSet.noneOf(ForgeDirection.class);
		inputSides.add(ForgeDirection.getOrientation(facing));
		inputSides.add(ForgeDirection.getOrientation(facing).getRotation(ForgeDirection.getOrientation(1)));
		inputSides.add(ForgeDirection.getOrientation(facing).getRotation(ForgeDirection.getOrientation(1)).getOpposite());
		return inputSides;
	}
}
