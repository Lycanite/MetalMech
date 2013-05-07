package lycanite.metalmech;

import lycanite.metalmech.block.TileEntityMachine;
import lycanite.metalmech.block.TileEntityMachineElectric;
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
	private RecipesCraftingExtra recipesCraftingExtra = new RecipesCraftingExtra();
	
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
	}
	
	
	//=============================================//
	//              Extractor Recipes              //
	//=============================================//
	public void addExtractorRecipes() {
		try {
			MachineRecipes.instance().addRecipe("Extractor", new ItemStack(Block.plantRed, 1, 0), new ItemStack(Item.dyePowder, 8, 1));
			MachineRecipes.instance().addRecipe("Extractor", new ItemStack(Block.plantYellow, 1, 0), new ItemStack(Item.dyePowder, 8, 11));
			MachineRecipes.instance().addRecipe("Extractor", new ItemStack(Block.cactus, 1, 0), new ItemStack(Item.dyePowder, 8, 2));
			
			MachineRecipes.instance().addRecipe("Extractor", "itemBitumen", MetalMech.hooks.getItemStack("tar", "MetallurgyMetals"), 6);
			MachineRecipes.instance().addRecipe("Extractor", "dustSulfur", new ItemStack(Item.lightStoneDust, 2, 2));
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
			MachineRecipes.instance().addRecipe("Compressor", new ItemStack(Block.cobblestone, 1, 0), new ItemStack(Block.stoneBrick, 4, 0));
			MachineRecipes.instance().addRecipe("Compressor", new ItemStack(Block.cobblestoneMossy, 1, 0), new ItemStack(Block.stoneBrick, 4, 2));
			MachineRecipes.instance().addRecipe("Compressor", new ItemStack(Block.stoneBrick, 4, 1), new ItemStack(Block.stoneBrick, 1, 0));
			MachineRecipes.instance().addRecipe("Compressor", new ItemStack(Block.sand, 1, 0), new ItemStack(Block.sandStone, 4, 0));
			MachineRecipes.instance().addRecipe("Compressor", new ItemStack(Item.clay, 1, 0), new ItemStack(Item.brick, 4, 0));
			MachineRecipes.instance().addRecipe("Compressor", new ItemStack(Block.netherrack, 1, 0), new ItemStack(Item.netherrackBrick, 4, 0));
			
			// TODO Duplication Issue
			/*MachineRecipes.instance().addRecipe("Compressor","ingotCopper", MetalMech.hooks.getItemStack("itemCopperPlate", "BasicComponents"));
			MachineRecipes.instance().addRecipe("Compressor","ingotTin", MetalMech.hooks.getItemStack("itemTinPlate", "BasicComponents"));
			MachineRecipes.instance().addRecipe("Compressor","ingotBronze", MetalMech.hooks.getItemStack("itemBronzePlate", "BasicComponents"));
			MachineRecipes.instance().addRecipe("Compressor","ingotSteel", MetalMech.hooks.getItemStack("itemSteelPlate", "BasicComponents"));*/
			
			// Force Metallurgy Bricks over UE plates:
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
			}));
		}
		catch(Exception e) {
			System.out.println("[MetalMech] All Compressor Recipes could not be added:");
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
