package lycanite.metalmech;

import lycanite.metalmech.tileentity.TileEntityMachine;
import lycanite.metalmech.tileentity.TileEntityMachineElectric;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

public class RecipeManager {
	
	// Recipe Classes:
	private RecipesBasicMachines recipesBasicMachines = new RecipesBasicMachines();
	private RecipesElectricMachines recipesElectricMachines = new RecipesElectricMachines();
	private RecipesExtra recipesCraftingExtra = new RecipesExtra();
	
	// Add Recipes:
	public void addRecipes() {
		
		// Add Ore Dictionary Listener:
		MinecraftForge.EVENT_BUS.register(new RecipesOreRegistry());
		
		// Add Recipes:
		recipesBasicMachines.addRecipes();
		recipesElectricMachines.addRecipes();
		recipesCraftingExtra.addRecipes();
		addExtractorRecipes();
		addCompressorRecipes();
		addSawmillRecipes();
	}
	
	
	//=============================================//
	//              Extractor Recipes              //
	//=============================================//
	public void addExtractorRecipes() {
		try {
			MachineRecipes.instance().addRecipe("Extractor", new ItemStack(Block.plantRed, 1, 0), new ItemStack(Item.dyePowder, 4, 1));
			MachineRecipes.instance().addRecipe("Extractor", new ItemStack(Block.plantYellow, 1, 0), new ItemStack(Item.dyePowder, 4, 11));
			MachineRecipes.instance().addRecipe("Extractor", new ItemStack(Block.cactus, 1, 0), new ItemStack(Item.dyePowder, 4, 2));
			
			MachineRecipes.instance().addRecipe("Extractor", "itemBitumen", MetalMech.hooks.getItemStack("tar", "MetallurgyMetals"), 2);
			MachineRecipes.instance().addRecipe("Extractor", "dustSulfur", new ItemStack(Item.lightStoneDust, 2, 0));
		}
		catch(Exception e) {
			System.out.println("[MetalMech] All Extractor Recipes could not be added:");
			e.printStackTrace();
		}
	}

	
	//=============================================//
	//             Compressor Recipes              //
	//=============================================//
	public void addCompressorRecipes() {
		try {
			MachineRecipes.instance().addRecipe("Compressor", new ItemStack(Block.cobblestone, 1, 0), new ItemStack(Block.stoneBrick, 2, 0));
			MachineRecipes.instance().addRecipe("Compressor", new ItemStack(Block.cobblestoneMossy, 1, 0), new ItemStack(Block.stoneBrick, 2, 2));
			MachineRecipes.instance().addRecipe("Compressor", new ItemStack(Block.stoneBrick, 1, 1), new ItemStack(Block.stoneBrick, 1, 0));
			MachineRecipes.instance().addRecipe("Compressor", new ItemStack(Block.sand, 1, 0), new ItemStack(Block.sandStone, 2, 0));
			MachineRecipes.instance().addRecipe("Compressor", new ItemStack(Item.clay, 1, 0), new ItemStack(Item.brick, 2, 0));
			MachineRecipes.instance().addRecipe("Compressor", new ItemStack(Block.netherrack, 1, 0), new ItemStack(Item.netherrackBrick, 2, 0));
			
			// TODO Duplication Issue
			/*MachineRecipes.instance().addRecipe("Compressor","ingotCopper", MetalMech.hooks.getItemStack("itemCopperPlate", "BasicComponents"));
			MachineRecipes.instance().addRecipe("Compressor","ingotTin", MetalMech.hooks.getItemStack("itemTinPlate", "BasicComponents"));
			MachineRecipes.instance().addRecipe("Compressor","ingotBronze", MetalMech.hooks.getItemStack("itemBronzePlate", "BasicComponents"));
			MachineRecipes.instance().addRecipe("Compressor","ingotSteel", MetalMech.hooks.getItemStack("itemSteelPlate", "BasicComponents"));*/
			
			/* Force Metallurgy Bricks over UE plates:
			GameRegistry.addRecipe(new ShapedOreRecipe(
				MetalMech.hooks.getMetallurgyItem("Copper", "brick", 4),
				new Object[] { "II", "II",
				Character.valueOf('I'), "ingotCopper"
			}));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(
				MetalMech.hooks.getMetallurgyItem("Tin", "brick", 4),
				new Object[] { "II", "II",
				Character.valueOf('I'), "ingotTin"
			}));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(
				MetalMech.hooks.getMetallurgyItem("Bronze", "brick", 4),
				new Object[] { "II", "II",
				Character.valueOf('I'), "ingotBronze"
			}));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(
				MetalMech.hooks.getMetallurgyItem("Steel", "brick", 4),
				new Object[] { "II", "II",
				Character.valueOf('I'), "ingotSteel"
			}));*/
		}
		catch(Exception e) {
			System.out.println("[MetalMech] All Compressor Recipes could not be added:");
			e.printStackTrace();
		}
	}
	
	
	//=============================================//
	//              Sawmill Recipes              //
	//=============================================//
	public void addSawmillRecipes() {
		try {
			// Logs to Planks:
			MachineRecipes.instance().addRecipe("Sawmill", new ItemStack(Block.wood, 1, 0), new ItemStack(Block.planks, 6, 0));
			MachineRecipes.instance().addRecipe("Sawmill", new ItemStack(Block.wood, 1, 1), new ItemStack(Block.planks, 6, 1));
			MachineRecipes.instance().addRecipe("Sawmill", new ItemStack(Block.wood, 1, 2), new ItemStack(Block.planks, 6, 2));
			MachineRecipes.instance().addRecipe("Sawmill", new ItemStack(Block.wood, 1, 3), new ItemStack(Block.planks, 6, 3));
			
			// Planks to Sticks:
			MachineRecipes.instance().addRecipe("Sawmill", new ItemStack(Block.planks, 1, 0), new ItemStack(Item.stick, 4, 0));
			MachineRecipes.instance().addRecipe("Sawmill", new ItemStack(Block.planks, 1, 1), new ItemStack(Item.stick, 4, 0));
			MachineRecipes.instance().addRecipe("Sawmill", new ItemStack(Block.planks, 1, 2), new ItemStack(Item.stick, 4, 0));
			MachineRecipes.instance().addRecipe("Sawmill", new ItemStack(Block.planks, 1, 3), new ItemStack(Item.stick, 4, 0));
			MachineRecipes.instance().addRecipe("Sawmill", "woodPlank", new ItemStack(Item.stick, 4, 0));
			MachineRecipes.instance().addRecipe("Sawmill", "plankWood", new ItemStack(Item.stick, 4, 0));
			
			// Furniture to Planks:
			MachineRecipes.instance().addRecipe("Sawmill", new ItemStack(Block.workbench, 1, 0), new ItemStack(Block.planks, 2, 0));
			MachineRecipes.instance().addRecipe("Sawmill", "craftingtable", new ItemStack(Block.planks, 2, 0));
			MachineRecipes.instance().addRecipe("Sawmill", "craftingTable", new ItemStack(Block.planks, 2, 0));
			MachineRecipes.instance().addRecipe("Sawmill", new ItemStack(Item.doorWood, 1, 0), new ItemStack(Block.planks, 4, 0));
			MachineRecipes.instance().addRecipe("Sawmill", new ItemStack(Block.trapdoor, 1, 0), new ItemStack(Block.planks, 2, 0));
			MachineRecipes.instance().addRecipe("Sawmill", new ItemStack(Block.chest, 1, 0), new ItemStack(Block.planks, 6, 0));
			MachineRecipes.instance().addRecipe("Sawmill", new ItemStack(Block.ladder, 1, 0), new ItemStack(Item.stick, 2, 0));
			MachineRecipes.instance().addRecipe("Sawmill", new ItemStack(Block.fence, 1, 0), new ItemStack(Item.stick, 2, 0));
			MachineRecipes.instance().addRecipe("Sawmill", new ItemStack(Block.fenceGate, 1, 0), new ItemStack(Block.planks, 1, 0));
			
			// Misc:
			MachineRecipes.instance().addRecipe("Sawmill", new ItemStack(Item.reed, 1, 0), new ItemStack(Item.paper, 2, 0));
		}
		catch(Exception e) {
			System.out.println("[MetalMech] All Sawmill Recipes could not be added:");
			e.printStackTrace();
		}
	}

	
	//=============================================//
	//            Ore Registry Listener            //
	//=============================================//
	public void oreRegistryListener(String name, ItemStack ore) {
		return;
	}
}
