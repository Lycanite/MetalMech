package lycanite.metalmech;

import lycanite.metalmech.machine.MachineManager;
import lycanite.metalmech.tileentity.TileEntityMachine;
import lycanite.metalmech.tileentity.TileEntityElectricBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

public class RecipesElectricMachines {
	public void addRecipes() {
		// Electric Machine Recipes:
		try {
			if(!Loader.isModLoaded("Metallurgy3Core")) throw new Exception("Metallurgy 3 Core was not found.");
			
			
			// ========== Electric Furnace ==========
			GameRegistry.addRecipe(new ShapedOreRecipe(
					new ItemStack(MetalMech.machineBlockElectric, 1, MachineManager.getMetadata("Furnace")),
				new Object[] { "III", "ICI", "IMI",
				Character.valueOf('I'), "ingotSteel",
				Character.valueOf('C'), "electricCircuit",
				Character.valueOf('M'), "resistor"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(
					new ItemStack(MetalMech.machineBlockElectric, 1, MachineManager.getMetadata("Furnace")),
				new Object[] { "III", "ICI", "IMI",
				Character.valueOf('I'), "ingotTitanium",
				Character.valueOf('C'), "electricCircuit",
				Character.valueOf('M'), "resistor"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(
				(ItemStack)OreDictionary.getOres("electricFurnace").get(0),
				new Object[] { " C ", " F ", " M ",
				Character.valueOf('F'), MetalMech.hooks.getItemStack("furnace", 1, 3, "MetallurgyMachines"),
				Character.valueOf('C'), "electricCircuit",
				Character.valueOf('M'), "resistor"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(
				(ItemStack)OreDictionary.getOres("electricFurnace").get(0),
				new Object[] { " C ", " F ", " M ",
				Character.valueOf('F'), new ItemStack(MetalMech.machineBlock, 1, MachineManager.getMetadata("TitaniumFurnace")),
				Character.valueOf('C'), "electricCircuit",
				Character.valueOf('M'), "resistor"
			}));
			
			
			// ========== Electric Crusher ==========
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.machineBlockElectric, 1, MachineManager.getMetadata("Crusher")),
				new Object[] { "SSS", "MCM", "LLL",
				Character.valueOf('L'), "ingotLead",
				Character.valueOf('S'), "ingotSteel",
				Character.valueOf('C'), "electricCircuit",
				Character.valueOf('M'), "motor"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.machineBlockElectric, 1, MachineManager.getMetadata("Crusher")),
				new Object[] { "TTT", "MCM", "LLL",
				Character.valueOf('L'), "ingotLead",
				Character.valueOf('T'), "ingotTitanium",
				Character.valueOf('C'), "electricCircuit",
				Character.valueOf('M'), "motor"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.machineBlockElectric, 1, MachineManager.getMetadata("Crusher")),
				new Object[] { " C ", "MRM", "   ",
				Character.valueOf('R'), MetalMech.hooks.getItemStack("crusher", 1, 4, "MetallurgyMachines"),
				Character.valueOf('C'), "electricCircuit",
				Character.valueOf('M'), "motor"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.machineBlockElectric, 1, MachineManager.getMetadata("Crusher")),
				new Object[] { "   ", " C ", "MRM",
				Character.valueOf('R'), MetalMech.hooks.getItemStack("crusher", 1, 4, "MetallurgyMachines"),
				Character.valueOf('C'), "electricCircuit",
				Character.valueOf('M'), "motor"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.machineBlockElectric, 1, MachineManager.getMetadata("Crusher")),
				new Object[] { " C ", "MRM", "   ",
				Character.valueOf('R'), new ItemStack(MetalMech.machineBlockCrusher, 1, MachineManager.getMetadata("TitaniumFurnace")),
				Character.valueOf('C'), "electricCircuit",
				Character.valueOf('M'), "motor"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe
				(new ItemStack(MetalMech.machineBlockElectric, 1, MachineManager.getMetadata("Crusher")),
				new Object[] { "   ", " C ", "MRM",
				Character.valueOf('R'), new ItemStack(MetalMech.machineBlockCrusher, 1, MachineManager.getMetadata("TitaniumFurnace")),
				Character.valueOf('C'), "electricCircuit",
				Character.valueOf('M'), "motor"
			}));
			
			
			// ========== Electric Extractor ==========
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.machineBlockElectric, 1, MachineManager.getMetadata("Extractor")),
				new Object[] { "SCS", "AMA", "LML",
				Character.valueOf('L'), "ingotLead",
				Character.valueOf('A'), "ingotAluminium",
				Character.valueOf('S'), "ingotSteel",
				Character.valueOf('C'), "electricCircuit",
				Character.valueOf('M'), "motor"
			}));
			
			
			// ========== Electric Compressor ==========
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.machineBlockElectric, 1, MachineManager.getMetadata("Compressor")),
				new Object[] { "SCS", "RTM", "LTL",
				Character.valueOf('L'), "ingotLead",
				Character.valueOf('T'), "ingotTitanium",
				Character.valueOf('S'), "ingotSteel",
				Character.valueOf('C'), "electricCircuit",
				Character.valueOf('M'), "motor",
				Character.valueOf('R'), "resistor"
			}));
			
			
			// ========== Electric Sawmill ==========
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.machineBlockElectric, 1, MachineManager.getMetadata("Sawmill")),
				new Object[] { "LIL", "IMI", "PCP",
				Character.valueOf('I'), "ingotSteel",
				Character.valueOf('L'), "logWood",
				Character.valueOf('P'), "plankWood",
				Character.valueOf('C'), "electricCircuit",
				Character.valueOf('M'), "motor"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.machineBlockElectric, 1, MachineManager.getMetadata("Sawmill")),
				new Object[] { "LIL", "IMI", "PCP",
				Character.valueOf('I'), "ingotTitanium",
				Character.valueOf('L'), "logWood",
				Character.valueOf('P'), "plankWood",
				Character.valueOf('C'), "electricCircuit",
				Character.valueOf('M'), "motor"
			}));
			
			
			// ========== Thermal Generator ==========
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.generatorBlockElectric, 1, MachineManager.getMetadata("ThermalGenerator")),
				new Object[] { "ICI", "IMI", "III",
				Character.valueOf('I'), "ingotSteel",
				Character.valueOf('C'), "electricCircuit",
				Character.valueOf('M'), "motor"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.generatorBlockElectric, 1, MachineManager.getMetadata("ThermalGenerator")),
				new Object[] { "ICI", "IMI", "III",
				Character.valueOf('I'), "ingotTitanium",
				Character.valueOf('C'), "electricCircuit",
				Character.valueOf('M'), "motor"
			}));
			
			
			// ========== Battery Array ==========
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.batteryBlockElectric, 1, MachineManager.getMetadata("BatteryArray")),
				new Object[] { "BCB", "BIB", "III",
				Character.valueOf('I'), "ingotSteel",
				Character.valueOf('C'), "basicCircuit",
				Character.valueOf('B'), "battery"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.batteryBlockElectric, 1, MachineManager.getMetadata("BatteryArray")),
				new Object[] { "BCB", "BIB", "III",
				Character.valueOf('I'), "ingotTitanium",
				Character.valueOf('C'), "basicCircuit",
				Character.valueOf('B'), "battery"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.batteryBlockElectric, 1, MachineManager.getMetadata("BatteryArray")),
				new Object[] { " C ", "BIB", "III",
				Character.valueOf('I'), "ingotSteel",
				Character.valueOf('C'), "basicCircuit",
				Character.valueOf('B'), "mediumBattery"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.batteryBlockElectric, 1, MachineManager.getMetadata("BatteryArray")),
				new Object[] { " C ", "BIB", "III",
				Character.valueOf('I'), "ingotTitanium",
				Character.valueOf('C'), "basicCircuit",
				Character.valueOf('B'), "mediumBattery"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.batteryBlockElectric, 1, MachineManager.getMetadata("BatteryArray")),
				new Object[] { " C ", " B ", "III",
				Character.valueOf('I'), "ingotSteel",
				Character.valueOf('C'), "basicCircuit",
				Character.valueOf('B'), "largeBattery"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.batteryBlockElectric, 1, MachineManager.getMetadata("BatteryArray")),
				new Object[] { " C ", " B ", "III",
				Character.valueOf('I'), "ingotTitanium",
				Character.valueOf('C'), "basicCircuit",
				Character.valueOf('B'), "largeBattery"
			}));
		}
		catch(Exception e) {
			System.out.println("[MetalMech] The crafting recipes for the Electric Machines could not be added:");
			e.printStackTrace();
		}
	}
}
