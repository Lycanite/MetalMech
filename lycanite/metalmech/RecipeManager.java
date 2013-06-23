package lycanite.metalmech;

import lycanite.metalmech.machine.MachineRecipes;
import lycanite.metalmech.tileentity.TileEntityMachine;
import lycanite.metalmech.tileentity.TileEntityElectricBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

public class RecipeManager {
	
	// Recipe Classes:
	private RecipesComponents recipesComponents = new RecipesComponents();
	private RecipesBasicMachines recipesBasicMachines = new RecipesBasicMachines();
	private RecipesElectricMachines recipesElectricMachines = new RecipesElectricMachines();
	private RecipesExtra recipesCraftingExtra = new RecipesExtra();
	
	// Add Recipes:
	public void addRecipes() {
		
		// Add Ore Dictionary Listener:
		MinecraftForge.EVENT_BUS.register(new RecipesOreRegistry());
		
		// Add Recipes:
		recipesComponents.addRecipes();
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
			MachineRecipes.instance().addRecipe("Compressor", new ItemStack(Block.cobblestoneMossy, 1, 0), new ItemStack(Block.stoneBrick, 2, 1));
			MachineRecipes.instance().addRecipe("Compressor", new ItemStack(Block.stoneBrick, 1, 1), new ItemStack(Block.stoneBrick, 1, 0));
			MachineRecipes.instance().addRecipe("Compressor", new ItemStack(Block.sand, 1, 0), new ItemStack(Block.sandStone, 2, 0));
			MachineRecipes.instance().addRecipe("Compressor", new ItemStack(Item.clay, 1, 0), new ItemStack(Item.brick, 2, 0));
			MachineRecipes.instance().addRecipe("Compressor", new ItemStack(Block.netherrack, 1, 0), new ItemStack(Item.netherrackBrick, 2, 0));
			
			MachineRecipes.instance().addRecipe("Compressor", "ingotIron", "plateIron", 1);
			MachineRecipes.instance().addRecipe("Compressor", Item.ingotIron, "plateIron", 1);
			MachineRecipes.instance().addRecipe("Compressor", "ingotCopper", "plateCopper", 1);
			MachineRecipes.instance().addRecipe("Compressor", "ingotTin", "plateTin", 1);
			MachineRecipes.instance().addRecipe("Compressor", "ingotBronze", "plateBronze", 1);
			MachineRecipes.instance().addRecipe("Compressor", "ingotSteel", "plateSteel", 1);
			MachineRecipes.instance().addRecipe("Compressor", "ingotGold", "plateGold", 1);
			MachineRecipes.instance().addRecipe("Compressor", Item.ingotGold, "plateGold", 1);
			MachineRecipes.instance().addRecipe("Compressor", "ingotSilver", "plateSilver", 1);
			MachineRecipes.instance().addRecipe("Compressor", "ingotPlatinum", "platePlatinum", 1);
			MachineRecipes.instance().addRecipe("Compressor", "ingotLead", "plateLead", 1);
			MachineRecipes.instance().addRecipe("Compressor", "ingotAluminium", "plateAluminium", 1);
			MachineRecipes.instance().addRecipe("Compressor", "ingotTitanium", "plateTitanium", 1);

			MachineRecipes.instance().addRecipe("Compressor", "itemTar", "insulation", 2);
			MachineRecipes.instance().addRecipe("Compressor", "itemBitumen", "insulation", 1);
			
			/* Force Metallurgy Bricks over UE plates:
			GameRegistry.addRecipe(new ShapedOreRecipe(
				MetalMech.hooks.getMetallurgyItem("Iron", "brick", 4),
				new Object[] { "II", "II",
				Character.valueOf('I'), Item.ingotIron
			}));
			
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
			
			GameRegistry.addRecipe(new ShapedOreRecipe(
				MetalMech.hooks.getMetallurgyItem("Gold", "brick", 4),
				new Object[] { "II", "II",
				Character.valueOf('I'), Item.ingotGold
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

	
	//=============================================//
	//                 Fuel Items                  //
	//=============================================//
	public static int getItemFuel(ItemStack itemStack) {
		if(itemStack != null) {
    		int itemFuelTime = 0;
    		Item item = itemStack.getItem(); 
    		int itemID = item.itemID;
    		if(item instanceof ItemBlock && Block.blocksList[itemID] != null) {
    			 Block blockName = Block.blocksList[itemID];
    			 if(blockName == Block.woodSingleSlab)
    				 itemFuelTime = 150;
                 if(blockName.blockMaterial == Material.wood)
                	 itemFuelTime = 300;
    		}
    		if(item instanceof ItemTool && ((ItemTool) item).getToolMaterialName().equals("WOOD")) itemFuelTime = 200;
            if(item instanceof ItemSword && ((ItemSword) item).getToolMaterialName().equals("WOOD")) itemFuelTime = 200;
            if(item instanceof ItemHoe && ((ItemHoe) item).getMaterialName().equals("WOOD")) itemFuelTime = 200;
            if(itemID == Item.stick.itemID) itemFuelTime = 100;
            if(itemID == Item.coal.itemID) itemFuelTime = 1600;
            if(itemID == Item.bucketLava.itemID) itemFuelTime = 20000;
            if(itemID == Block.sapling.blockID) itemFuelTime = 100;
            if(itemID == Item.blazeRod.itemID) itemFuelTime = 2400;
            
            if(itemFuelTime > 0) return itemFuelTime * 10;
            
            itemFuelTime = GameRegistry.getFuelValue(itemStack);
            return itemFuelTime * 10;
    	}
    	else
    		return 0;
	}
}
