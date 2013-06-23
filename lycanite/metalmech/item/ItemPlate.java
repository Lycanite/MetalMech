package lycanite.metalmech.item;

public class ItemPlate extends ItemBase {
	
	// Info:
	public static String[] baseItemNames = {
		"ironPlate", "copperPlate", "tinPlate", "bronzePlate", "steelPlate", "goldPlate", "silverPlate", "platinumPlate", "leadPlate", "aluminiumPlate", "titaniumPlate"
	};
	public static String[] baseNames = {
		"IronPlate", "CopperPlate", "TinPlate", "BronzePlate", "SteelPlate", "GoldPlate", "SilverPlate", "PlatinumPlate", "LeadPlate", "AluminiumPlate", "TitaniumPlate"
	};
	public static String[] baseTitles = {
		"Iron Plate", "Copper Plate", "Tin Plate", "Bronze Plate", "Steel Plate", "Gold Plate", "Silver Plate", "Platinum Plate", "Lead Plate", "Aluminium Plate", "Titanium Plate"
	};
	public static String[] baseOreNames = {
		"plateIron", "plateCopper", "plateTin", "plateBronze", "plateSteel", "plateGold", "plateSilver", "platePlatinum", "plateLead", "plateAluminium", "plateTitanium"
	};
	
	// ==================================================
	//                       Setup
	// ==================================================
	// ========== Constructor ==========
	public ItemPlate(int id, String setTexturePath) {
		super(id, setTexturePath);
		itemNames = baseItemNames;
		names = baseNames;
		titles = baseTitles;
		subItems = baseItemNames.length;
		oreNames = baseOreNames;
		oreRegistry = true;
	}
}
