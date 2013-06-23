package lycanite.metalmech.item;

import lycanite.metalmech.MetalMech;
import net.minecraft.item.ItemStack;

public class ItemBattery extends ItemElectric {
	
	// Info:
	public int batteryType = 0;
	public static String[] baseItemNames = {
		"smallBattery", "mediumBattery", "largeBattery"
	};
	public static String[] baseNames = {
		"SmallBattery", "MediumBattery", "LargeBattery"
	};
	public static String[] baseTitles = {
		"Small Battery", "Medium Battery", "Large Battery"
	};
	public static String[] baseOreNames = {
		"battery", "advancedBattery", "eliteBattery"
	};
	public static double[] baseJouleStorage = {
		1 * 1000000, 10 * 1000000, 100 * 1000000
	};
	public static double[] baseVoltage = {
		MetalMech.mediumVoltage, MetalMech.highVoltage, MetalMech.extremeVoltage
	};
	
	// ==================================================
	//                       Setup
	// ==================================================
	// ========== Constructor ==========
	public ItemBattery(int id, String setTexturePath, int setBatteryType) {
		super(id, setTexturePath);
		batteryType = setBatteryType;
		itemNames[0] = baseItemNames[batteryType];
		names[0] = baseNames[batteryType];
		titles[0] = baseTitles[batteryType];
		oreNames[0] = baseOreNames[batteryType];
		oreRegistry = true;
	}
	
	
	// ==================================================
	//                    Electricity
	// ==================================================
	// ========== Get Amps Stored ==========
	@Override
	public double getJouleStorage(ItemStack itemStack) {
		return baseJouleStorage[batteryType];
	}
	
	
	// ========== Get Voltage ==========
	@Override
	public double getVoltage(ItemStack itemStack) {
		return baseVoltage[batteryType];
	}
}
