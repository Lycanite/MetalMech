package lycanite.metalmech.item;

public class ItemComponent extends ItemBase {
	
	// Info:
	public static String[] baseItemNames = {
		"electricCircuit", "modularCircuit", "motor", "resistor", "insulation"
	};
	public static String[] baseNames = {
		"Electric Circuit", "ModularCircuit", "Motor", "Resistor", "Insulation"
	};
	public static String[] baseTitles = {
		"Electric Circuit", "Modular Circuit", "Motor", "Resistor", "Insulation"
	};
	public static String[] baseOreNames = {
		"basicCircuit", "advancedCircuit", "motor", "resistor", "insulation"
	};
	
	// ==================================================
	//                       Setup
	// ==================================================
	// ========== Constructor ==========
	public ItemComponent(int id, String setTexturePath) {
		super(id, setTexturePath);
		itemNames = baseItemNames;
		names = baseNames;
		titles = baseTitles;
		subItems = baseItemNames.length;
		oreNames = baseOreNames;
		oreRegistry = true;
	}
}
