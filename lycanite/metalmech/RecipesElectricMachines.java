package lycanite.metalmech;

import lycanite.metalmech.block.TileEntityMachine;
import lycanite.metalmech.block.TileEntityMachineElectric;
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
			if(!Loader.isModLoaded("BasicComponents")) throw new Exception("Basic Components (Universal Electricity) was not found.");
			
			
			// ========== Electric Furnace ==========
			GameRegistry.addRecipe(new ShapedOreRecipe(
				(ItemStack)OreDictionary.getOres("electricFurnace").get(0),
				new Object[] { "TTT", "TCT", "TMT",
				Character.valueOf('T'), "ingotTitanium",
				Character.valueOf('C'), "basicCircuit",
				Character.valueOf('M'), "motor"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(
				(ItemStack)OreDictionary.getOres("electricFurnace").get(0),
				new Object[] { " C ", " F ", " M ",
				Character.valueOf('F'), MetalMech.hooks.getItemStack("furnace", 1, 3, "MetallurgyMachines"),
				Character.valueOf('C'), "basicCircuit",
				Character.valueOf('M'), "motor"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(
				(ItemStack)OreDictionary.getOres("electricFurnace").get(0),
				new Object[] { " C ", " F ", " M ",
				Character.valueOf('F'), new ItemStack(MetalMech.machineBlock, 1, TileEntityMachine.getMetadataFromRank("Titanium")),
				Character.valueOf('C'), "basicCircuit",
				Character.valueOf('M'), "motor"
			}));
			
			
			// ========== Electric Crusher ==========
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.machineBlockElectric, 1, TileEntityMachineElectric.getTypeFromString("Crusher")),
				new Object[] { "SSS", "MCM", "LLL",
				Character.valueOf('L'), "ingotLead",
				Character.valueOf('S'), "ingotSteel",
				Character.valueOf('C'), "basicCircuit",
				Character.valueOf('M'), "motor"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.machineBlockElectric, 1, TileEntityMachineElectric.getTypeFromString("Crusher")),
				new Object[] { "TTT", "MCM", "LLL",
				Character.valueOf('L'), "ingotLead",
				Character.valueOf('T'), "ingotTitanium",
				Character.valueOf('C'), "basicCircuit",
				Character.valueOf('M'), "motor"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.machineBlockElectric, 1, TileEntityMachineElectric.getTypeFromString("Crusher")),
				new Object[] { " C ", "MRM", "   ",
				Character.valueOf('R'), MetalMech.hooks.getItemStack("crusher", 1, 4, "MetallurgyMachines"),
				Character.valueOf('C'), "basicCircuit",
				Character.valueOf('M'), "motor"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.machineBlockElectric, 1, TileEntityMachineElectric.getTypeFromString("Crusher")),
				new Object[] { "   ", " C ", "MRM",
				Character.valueOf('R'), MetalMech.hooks.getItemStack("crusher", 1, 4, "MetallurgyMachines"),
				Character.valueOf('C'), "basicCircuit",
				Character.valueOf('M'), "motor"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.machineBlockElectric, 1, TileEntityMachineElectric.getTypeFromString("Crusher")),
				new Object[] { " C ", "MRM", "   ",
				Character.valueOf('R'), new ItemStack(MetalMech.machineBlockCrusher, 1, TileEntityMachine.getMetadataFromRank("Titanium")),
				Character.valueOf('C'), "basicCircuit",
				Character.valueOf('M'), "motor"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe
				(new ItemStack(MetalMech.machineBlockElectric, 1, TileEntityMachineElectric.getTypeFromString("Crusher")),
				new Object[] { "   ", " C ", "MRM",
				Character.valueOf('R'), new ItemStack(MetalMech.machineBlockCrusher, 1, TileEntityMachine.getMetadataFromRank("Titanium")),
				Character.valueOf('C'), "basicCircuit",
				Character.valueOf('M'), "motor"
			}));
			
			
			// ========== Electric Extractor ==========
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.machineBlockElectric, 1, TileEntityMachineElectric.getTypeFromString("Extractor")),
				new Object[] { "SCS", "AMA", "LML",
				Character.valueOf('L'), "ingotLead",
				Character.valueOf('A'), "ingotAluminium",
				Character.valueOf('S'), "ingotSteel",
				Character.valueOf('C'), "basicCircuit",
				Character.valueOf('M'), "motor"
			}));
			
			
			// ========== Electric Compressor ==========
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.machineBlockElectric, 1, TileEntityMachineElectric.getTypeFromString("Compressor")),
				new Object[] { "SCS", "MTM", "LTL",
				Character.valueOf('T'), "ingotTitanium",
				Character.valueOf('S'), "ingotSteel",
				Character.valueOf('C'), "basicCircuit",
				Character.valueOf('M'), "motor"
			}));
		}
		catch(Exception e) {
			System.out.println("[MetalMech] The crafting recipes for the Electric Machines could not be added:");
			e.printStackTrace();
		}
	}
}
