package lycanite.metalmech;

import lycanite.metalmech.machine.MachineManager;
import lycanite.metalmech.machine.MachineRecipes;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

public class RecipesComponents {
	public void addRecipes() {
		
		//=============================================//
		//             Electric Components             //
		//=============================================//
		try {
			// Electric Circuit:
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.componentItem.itemID, 1, 0),
				new Object[] {"RWR", "WIW", "RWR",
				Character.valueOf('W'), "copperWire",
				Character.valueOf('I'), "ingotBronze",
				Character.valueOf('R'), Item.redstone
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.componentItem.itemID, 1, 0),
				new Object[] {"RWR", "WIW", "RWR",
				Character.valueOf('W'), "copperWire",
				Character.valueOf('I'), "plateBronze",
				Character.valueOf('R'), Item.redstone
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.componentItem.itemID, 1, 0),
				new Object[] {"RWR", "WIW", "RWR",
				Character.valueOf('W'), "copperWire",
				Character.valueOf('I'), "plateAluminium",
				Character.valueOf('R'), Item.redstone
			}));
			
			
			// Modular Circuit:
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.componentItem.itemID, 1, 1),
				new Object[] {"WQW", "WCW", "PPP",
				Character.valueOf('W'), "goldWire",
				Character.valueOf('C'), "electricCircuit",
				Character.valueOf('P'), "plateGold",
				Character.valueOf('Q'), Item.netherQuartz
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.componentItem.itemID, 1, 1),
				new Object[] {"WQW", "WCW", "PPP",
				Character.valueOf('W'), "silverWire",
				Character.valueOf('C'), "electricCircuit",
				Character.valueOf('P'), "plateSilver",
				Character.valueOf('Q'), Item.netherQuartz
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.componentItem.itemID, 1, 1),
				new Object[] {"WQW", "WCW", "PPP",
				Character.valueOf('W'), "goldWire",
				Character.valueOf('C'), "basicCircuit",
				Character.valueOf('P'), "plateGold",
				Character.valueOf('Q'), Item.netherQuartz
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.componentItem.itemID, 1, 1),
				new Object[] {"WQW", "WCW", "PPP",
				Character.valueOf('W'), "silverWire",
				Character.valueOf('C'), "basicCircuit",
				Character.valueOf('P'), "plateSilver",
				Character.valueOf('Q'), Item.netherQuartz
			}));
			
			
			// Motor:
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.componentItem.itemID, 1, 2),
				new Object[] {"WCW", "CSC", "WCW",
				Character.valueOf('W'), "copperWire",
				Character.valueOf('C'), "ingotCopper",
				Character.valueOf('S'), "ingotSteel"
			}));
			GameRegistry.addRecipe(new ShapelessOreRecipe(
				new ItemStack(MetalMech.componentItem.itemID, 1, 2),
				new Object[] {"motor"}
			));
			
			
			// Resistor:
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.componentItem.itemID, 1, 3),
				new Object[] {" L ", "WCW", " L ",
				Character.valueOf('W'), "copperWire",
				Character.valueOf('L'), "ingotLead",
				Character.valueOf('C'), Item.coal
			}));
			
			
			// Insulation:
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.componentItem.itemID, 6, 4),
				new Object[] {"RRR", "   ", "RRR",
				Character.valueOf('R'), "itemTar"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.componentItem.itemID, 6, 4),
				new Object[] {"RRR", "   ", "RRR",
				Character.valueOf('R'), Item.leather
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.componentItem.itemID, 6, 4),
				new Object[] {"RRR", "   ", "RRR",
				Character.valueOf('R'), Block.cloth
			}));
			
			
			// Wires:
			GameRegistry.addRecipe(new ShapelessOreRecipe(
				new ItemStack(MetalMech.wireBlock, 4, MachineManager.getMetadata("CopperWire")),
				new Object[] { "ingotCopper", "insulation" }
			));
			GameRegistry.addRecipe(new ShapelessOreRecipe(
				new ItemStack(MetalMech.wireBlock, 4, MachineManager.getMetadata("TinWire")),
				new Object[] { "ingotTin", "insulation" }
			));
			GameRegistry.addRecipe(new ShapelessOreRecipe(
				new ItemStack(MetalMech.wireBlock, 4, MachineManager.getMetadata("AluminiumWire")),
				new Object[] { "ingotAluminium", "insulation" }
			));
			GameRegistry.addRecipe(new ShapelessOreRecipe(
				new ItemStack(MetalMech.wireBlock, 4, MachineManager.getMetadata("GoldWire")),
				new Object[] { "ingotGold", "insulation" }
			));
			GameRegistry.addRecipe(new ShapelessOreRecipe(
				new ItemStack(MetalMech.wireBlock, 4, MachineManager.getMetadata("GoldWire")),
				new Object[] { Item.ingotGold, "insulation" }
			));
			GameRegistry.addRecipe(new ShapelessOreRecipe(
				new ItemStack(MetalMech.wireBlock, 4, MachineManager.getMetadata("SilverWire")),
				new Object[] { "ingotSilver", "insulation" }
			));
			GameRegistry.addRecipe(new ShapelessOreRecipe(
				new ItemStack(MetalMech.wireBlock, 4, MachineManager.getMetadata("ElectrumWire")),
				new Object[] { "ingotElectrum", "insulation" }
			));
			GameRegistry.addRecipe(new ShapelessOreRecipe(
				new ItemStack(MetalMech.wireBlock, 4, MachineManager.getMetadata("PlatinumWire")),
				new Object[] { "ingotPlatinum", "insulation" }
			));
			GameRegistry.addRecipe(new ShapelessOreRecipe(
				new ItemStack(MetalMech.wireBlock, 4, MachineManager.getMetadata("VyroxeresWire")),
				new Object[] { "ingotVyroxeres", "insulation" }
			));
			GameRegistry.addRecipe(new ShapelessOreRecipe(
				new ItemStack(MetalMech.wireBlock, 4, MachineManager.getMetadata("MidasiumWire")),
				new Object[] { "ingotMidasium", "insulation" }
			));
			GameRegistry.addRecipe(new ShapelessOreRecipe(
				new ItemStack(MetalMech.wireBlock, 4, MachineManager.getMetadata("DesichalkosWire")),
				new Object[] { "ingotDesichalkos", "insulation" }
			));
			
			
			// Small Battery:
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.batteryItem[0]),
				new Object[] {" T ", "TST", "TST",
				Character.valueOf('T'), "ingotTin",
				Character.valueOf('S'), "dustSulfur"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.batteryItem[0]),
				new Object[] {" T ", "TRT", "TCT",
				Character.valueOf('T'), "ingotTin",
				Character.valueOf('C'), Item.coal,
				Character.valueOf('R'), Item.redstone
			}));
			
			
			// Medium Battery:
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.batteryItem[1]),
				new Object[] {" P ", "PSP", "PQP",
				Character.valueOf('P'), "plateTin",
				Character.valueOf('S'), "dustSulfur",
				Character.valueOf('Q'), Item.netherQuartz
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.batteryItem[1]),
				new Object[] {" P ", "PBP", " Q ",
				Character.valueOf('P'), "plateTin",
				Character.valueOf('B'), "battery",
				Character.valueOf('Q'), Item.netherQuartz
			}));
			
			
			// Large Battery:
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.batteryItem[2]),
				new Object[] {" P ", "PQP", "PDP",
				Character.valueOf('P'), "platePlatinum",
				Character.valueOf('Q'), Item.netherQuartz,
				Character.valueOf('D'), Item.diamond
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.batteryItem[2]),
				new Object[] {" P ", "PBP", " D ",
				Character.valueOf('P'), "platePlatinum",
				Character.valueOf('B'), "mediumBattery",
				Character.valueOf('D'), Item.diamond
			}));
		}
		catch(Exception e) {
			System.out.println("[MetalMech] There was a problem with the Components Recipes:");
			System.out.println("Make sure you have Metallurgy Base Metals, Metallurgy Utility Ores installed.");
			e.printStackTrace();
		}
	}
}
