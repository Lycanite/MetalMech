package lycanite.metalmech;

import lycanite.metalmech.tileentity.TileEntityMachine;
import lycanite.metalmech.tileentity.TileEntityMachineElectric;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

public class RecipesBasicMachines {
	public void addRecipes() {
		
		// Basic Machine Recipes:
		try {
			if(!Loader.isModLoaded("Metallurgy3Core")) throw new Exception("Metallurgy 3 Core was not found.");
			
			// ========== Furnaces ==========
			// Lead Furnace:
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.machineBlock, 1, MachineManager.getMetadata("LeadFurnace")),
				new Object[] { "III", "IFI", "III",
				Character.valueOf('F'), new ItemStack(Block.furnaceIdle.blockID, 1, 0),
				Character.valueOf('I'), "ingotLead"
			}));
			
			// Aluminium Furnace:
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.machineBlock, 1, MachineManager.getMetadata("AluminiumFurnace")),
				new Object[] { "III", "IFI", "III",
				Character.valueOf('F'), new ItemStack(MetalMech.machineBlock, 1, MachineManager.getMetadata("LeadFurnace")),
				Character.valueOf('I'), "ingotAluminium"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.machineBlock, 1, MachineManager.getMetadata("AluminiumFurnace")),
				new Object[] { "III", "IFI", "III",
				Character.valueOf('F'), MetalMech.hooks.getItemStack("furnace", 1, 0, "MetallurgyMachines"),
				Character.valueOf('I'), "ingotAluminium"
			}));
			
			// Iron Furnace:
			GameRegistry.addRecipe(new ShapedOreRecipe(
				MetalMech.hooks.getItemStack("furnace", 1, 2, "MetallurgyMachines"),
				new Object[] { "III", "IFI", "III",
				Character.valueOf('F'), new ItemStack(MetalMech.machineBlock, 1, MachineManager.getMetadata("LeadFurnace")),
				Character.valueOf('I'), Item.ingotIron
			}));
			
			// Titanium Furnace:
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.machineBlock, 1, MachineManager.getMetadata("TitaniumFurnace")),
				new Object[] { "III", "IFI", "III",
				Character.valueOf('F'), new ItemStack(MetalMech.machineBlock, 1, MachineManager.getMetadata("AluminiumFurnace")),
				Character.valueOf('I'), "ingotTitanium"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe
				(new ItemStack(MetalMech.machineBlock, 1, MachineManager.getMetadata("TitaniumFurnace")),
				new Object[] { "III", "IFI", "III",
				Character.valueOf('F'), MetalMech.hooks.getItemStack("furnace", 1, 1, "MetallurgyMachines"),
				Character.valueOf('I'), "ingotTitanium"
			}));
			
			// Steel Furnace:
			GameRegistry.addRecipe(new ShapedOreRecipe(
				MetalMech.hooks.getItemStack("furnace", 1, 3, "MetallurgyMachines"),
				new Object[] { "III", "IFI", "III",
				Character.valueOf('F'), new ItemStack(MetalMech.machineBlock, 1, MachineManager.getMetadata("AluminiumFurnace")),
				Character.valueOf('I'), "ingotSteel"
			}));
			
			
			// ========== Crushers ==========
			// Lead Crusher:
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.machineBlockCrusher, 1, MachineManager.getMetadata("LeadCrusher")),
				new Object[] { "III", "IFI", "III",
				Character.valueOf('F'), MetalMech.hooks.getItemStack("crusher", 1, 0, "MetallurgyMachines"),
				Character.valueOf('I'), "ingotLead"
			}));
			
			// Aluminium Crusher:
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.machineBlockCrusher, 1, MachineManager.getMetadata("AluminiumCrusher")),
				new Object[] { "III", "IFI", "III",
				Character.valueOf('F'), new ItemStack(MetalMech.machineBlockCrusher, 1, MachineManager.getMetadata("LeadCrusher")),
				Character.valueOf('I'), "ingotAluminium"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.machineBlockCrusher, 1, MachineManager.getMetadata("AluminiumCrusher")),
				new Object[] { "III", "IFI", "III",
				Character.valueOf('F'), MetalMech.hooks.getItemStack("crusher", 1, 1, "MetallurgyMachines"),
				Character.valueOf('I'), "ingotAluminium"
			}));
			
			// Iron Crusher:
			GameRegistry.addRecipe(new ShapedOreRecipe(
				MetalMech.hooks.getItemStack("crusher", 1, 3, "MetallurgyMachines"),
				new Object[] { "III", "IFI", "III",
				Character.valueOf('F'), new ItemStack(MetalMech.machineBlockCrusher, 1, MachineManager.getMetadata("LeadCrusher")),
				Character.valueOf('I'), Item.ingotIron
			}));
			
			// Titanium Crusher:
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.machineBlockCrusher, 1, MachineManager.getMetadata("TitaniumCrusher")),
				new Object[] { "III", "IFI", "III",
				Character.valueOf('F'), new ItemStack(MetalMech.machineBlockCrusher, 1, MachineManager.getMetadata("AluminiumCrusher")),
				Character.valueOf('I'), "ingotTitanium"
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(
				new ItemStack(MetalMech.machineBlockCrusher, 1, MachineManager.getMetadata("TitaniumCrusher")),
				new Object[] { 	"III", "IFI", "III",
				Character.valueOf('F'), MetalMech.hooks.getItemStack("crusher", 1, 2, "MetallurgyMachines"),
				Character.valueOf('I'), "ingotTitanium"
			}));
			
			// Steel Crusher:
			GameRegistry.addRecipe(new ShapedOreRecipe(
				MetalMech.hooks.getItemStack("crusher", 1, 4, "MetallurgyMachines"),
				new Object[] { 	"III", "IFI", "III",
				Character.valueOf('F'), new ItemStack(MetalMech.machineBlockCrusher, 1, MachineManager.getMetadata("AluminiumCrusher")),
				Character.valueOf('I'), "ingotSteel"
			}));
		}
		catch(Exception e) {
			System.out.println("[MetalMech] The crafting recipes for the Basic Machines could not be added:");
			e.printStackTrace();
		}
	}
}
