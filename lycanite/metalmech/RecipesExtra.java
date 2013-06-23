package lycanite.metalmech;

import lycanite.metalmech.machine.MachineRecipes;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

public class RecipesExtra {
	public void addRecipes() {
		
		//=============================================//
		//             Utility Ore Recipes             //
		//=============================================//
		try {
			// Bronze Dust:
			GameRegistry.addRecipe(new ShapelessOreRecipe(
				MetalMech.hooks.getMetallurgyItem("Bronze","dust", 4),
				"dustCopper",
				"dustTin",
				"dustSulfur"
			));
			GameRegistry.addRecipe(new ShapelessOreRecipe(
				MetalMech.hooks.getMetallurgyItem("Bronze","dust", 4),
				"dustCopper",
				"dustTin",
				"dustLead"
			));
			GameRegistry.addRecipe(new ShapelessOreRecipe(
				MetalMech.hooks.getMetallurgyItem("Bronze","dust", 6),
				"dustCopper",
				"dustTin",
				"dustSulfur",
				"dustLead"
			));

			// Steel Dust:
			GameRegistry.addRecipe(new ShapelessOreRecipe(
				MetalMech.hooks.getMetallurgyItem("Steel","dust", 4),
				"dustIron",
				"dustManganese",
				"itemBitumen"
			));
			GameRegistry.addRecipe(new ShapelessOreRecipe(
				MetalMech.hooks.getMetallurgyItem("Steel","dust", 4),
				"dustIron",
				"dustManganese",
				"dustTungsten"
			));
			GameRegistry.addRecipe(new ShapelessOreRecipe(
				MetalMech.hooks.getMetallurgyItem("Steel","dust", 6),
				"dustIron",
				"dustManganese",
				"itemBitumen",
				"dustTungsten"
			));
		}
		catch(Exception e) {
			System.out.println("[MetalMech] The alternative crafting recipes for Metallurgy could not be added:");
			System.out.println("Make sure you have Metallurgy Base Metals and Metallurgy Utility Ores installed.");
			e.printStackTrace();
		}
		
		
		//=============================================//
		//               Basic Components              //
		//=============================================//
		/*try {
			GameRegistry.addRecipe(new ShapedOreRecipe(
				MetalMech.hooks.getItemStack("itemBattery", 1, 0, "BasicComponents"),
				new Object[] {" T ", "TST", "TST",
				Character.valueOf('T'), "ingotTin",
				Character.valueOf('S'), "dustSulfur"
			}));
			
			GameRegistry.addRecipe(new ShapedOreRecipe(
				MetalMech.hooks.getItemStack("itemCircuit", 1, 1, "BasicComponents"),
				new Object[] {"RRR", "CEC", "RRR",
				Character.valueOf('R'), "redstone",
				Character.valueOf('E'), "emerald",
				Character.valueOf('C'), "basicCircuit"
			}));
			
			if(!Loader.isModLoaded("ElectricExpansion")) {
				GameRegistry.addRecipe(new ShapedOreRecipe(
					MetalMech.hooks.getItemStack("blockCopperWire", 6, 0, "BasicComponents"),
					new Object[] {"TCT", "TCT", "TCT",
					Character.valueOf('T'), "itemTar",
					Character.valueOf('C'), "ingotCopper"
				}));
				GameRegistry.addRecipe(new ShapelessOreRecipe(
					MetalMech.hooks.getItemStack("blockCopperWire", 6, 1, "BasicComponents"),
					new Object[] {"itemTar",
					"uninsulatedCopperWire"
				}));
			}
		}
		catch(Exception e) {
			System.out.println("[MetalMech] The alternative crafting recipes for Basic Components could not be added:");
			System.out.println("Make sure you have Metallurgy Base Metals, Metallurgy Utility Ores and Basic Components installed.");
			e.printStackTrace();
		}*/
		
		
		//=============================================//
		//                  Mekanism                   //
		//=============================================//
		try {
			if(!Loader.isModLoaded("Mekanism")) throw new Exception("Mekanism was not found.");

			GameRegistry.addRecipe(new ShapedOreRecipe(
				MetalMech.hooks.getItemStack("EnrichedAlloy", 1, 0, "Mekanism"),
				new Object[] {" B ", "BIB", " B ",
				Character.valueOf('B'), "itemBitumen",
				Character.valueOf('I'), Item.ingotIron
			}));
			GameRegistry.addRecipe(new ShapedOreRecipe(
				MetalMech.hooks.getItemStack("EnrichedAlloy", 1, 0, "Mekanism"),
				new Object[] {" B ", "BIB", " B ",
				Character.valueOf('B'), "dustPotash",
				Character.valueOf('I'), "ingotAluminium"
			}));
			

			GameRegistry.addRecipe(new ShapelessOreRecipe(
				MetalMech.hooks.getItemStack("Dust", 2, 2, "Mekanism"),
				new Object[] {
					"dustPlatinum",
					"dustSulfur"
				}
			));
		}
		catch(Exception e) {}
		
		
		//=============================================//
		//              Electric Expansion             //
		//=============================================//
		/*try {
			if(!Loader.isModLoaded("ElectricExpansion")) throw new Exception("Electric Expansion was not found.");
			
			// Wire Insulation:
			GameRegistry.addRecipe(new ShapedOreRecipe(
				MetalMech.hooks.getItemStack("itemParts", 6, 6, "ElectricExpansionItems"),
				new Object[] {"T T", "T T", "T T",
				Character.valueOf('T'), "itemTar"
			}));
			MachineRecipes.instance().addRecipe("Compressor", "itemTar", MetalMech.hooks.getItemStack("itemParts", 1, 6, "ElectricExpansionItems"));
		}
		catch(Exception e) {}*/
		
		
		//=============================================//
		//                Fluid Mechanics              //
		//=============================================//
		try {
			if(!Loader.isModLoaded("Fluid_Mechanics")) throw new Exception("Fluid Mechanics was not found.");
			
			// Pipes:
			GameRegistry.addRecipe(new ShapelessOreRecipe(
				MetalMech.hooks.getItemStack("blockPipe", 1, 14, "FluidMech"),
				new Object[] { "itemTar",
				MetalMech.hooks.getItemStack("itemParts", 1, 0, "FluidMech")
			}));
			GameRegistry.addRecipe(new ShapelessOreRecipe(
				MetalMech.hooks.getItemStack("blockPipe", 1, 15, "FluidMech"),
				new Object[] { "itemTar",
				MetalMech.hooks.getItemStack("itemParts", 1, 1, "FluidMech")
			}));
			
			// Tank:
			GameRegistry.addRecipe(new ShapedOreRecipe(
				MetalMech.hooks.getItemStack("blockTank", 1, 4, "FluidMech"),
				new Object[] {	"WGW", "TUT", "WGW",
				Character.valueOf('W'), Block.wood,
				Character.valueOf('G'), Block.glass,
				Character.valueOf('T'), "itemTar",
				MetalMech.hooks.getItemStack("itemParts", 1, 6, "FluidMech"),
			}));
			
			// Container:
			GameRegistry.addRecipe(new ShapedOreRecipe(
				MetalMech.hooks.getItemStack("itemParts", 1, 6, "FluidMech"),
				new Object[] {	" A ", "A A", " A ",
				Character.valueOf('A'), "ingotAluminium"
			}));
		}
		catch(Exception e) {}
		
		
		//=============================================//
		//                Biomes O Plenty              //
		//=============================================//
		try {
			if(!Loader.isModLoaded("BiomesOPlenty")) throw new Exception("Biomes O Plenty was not found.");
			
			// Extractor:
			MachineRecipes.instance().addRecipe("Extractor", MetalMech.hooks.getItemStack("blueFlower", "BOPBlocks"), new ItemStack(Item.dyePowder, 4, 6));
			MachineRecipes.instance().addRecipe("Extractor", MetalMech.hooks.getItemStack("purpleFlower", "BOPBlocks"), new ItemStack(Item.dyePowder, 4, 13));
			MachineRecipes.instance().addRecipe("Extractor", MetalMech.hooks.getItemStack("orangeFlower", "BOPBlocks"), new ItemStack(Item.dyePowder, 4, 14));
			MachineRecipes.instance().addRecipe("Extractor", MetalMech.hooks.getItemStack("pinkFlower", "BOPBlocks"), new ItemStack(Item.dyePowder, 4, 9));
			MachineRecipes.instance().addRecipe("Extractor", MetalMech.hooks.getItemStack("whiteFlower", "BOPBlocks"), new ItemStack(Item.dyePowder, 4, 7));
			MachineRecipes.instance().addRecipe("Extractor", MetalMech.hooks.getItemStack("hydrangea", "BOPBlocks"), new ItemStack(Item.dyePowder, 4, 12));
			MachineRecipes.instance().addRecipe("Extractor", MetalMech.hooks.getItemStack("violet", "BOPBlocks"), new ItemStack(Item.dyePowder, 4, 5));
			MachineRecipes.instance().addRecipe("Extractor", MetalMech.hooks.getItemStack("tinyCactus", "BOPBlocks"), new ItemStack(Item.dyePowder, 4, 2));
			
			// Compressor:
			MachineRecipes.instance().addRecipe("Compressor", MetalMech.hooks.getItemStack("redRockCobble", "BOPBlocks"), MetalMech.hooks.getItemStack("redRockBrick", 2, 0, "BOPBlocks"));
			MachineRecipes.instance().addRecipe("Compressor", MetalMech.hooks.getItemStack("mudBall", "BOPItems"), MetalMech.hooks.getItemStack("mudBrick", 2, 0, "BOPItems"));
			
			// Sawmill:
			MachineRecipes.instance().addRecipe("Sawmill", MetalMech.hooks.getItemStack("redwoodWood", "BOPBlocks"), MetalMech.hooks.getItemStack("redwoodPlank", 6, 0, "BOPBlocks"));
			MachineRecipes.instance().addRecipe("Sawmill", MetalMech.hooks.getItemStack("willowWood", "BOPBlocks"), MetalMech.hooks.getItemStack("willowPlank", 6, 0, "BOPBlocks"));
			MachineRecipes.instance().addRecipe("Sawmill", MetalMech.hooks.getItemStack("firWood", "BOPBlocks"), MetalMech.hooks.getItemStack("firPlank", 6, 0, "BOPBlocks"));
			MachineRecipes.instance().addRecipe("Sawmill", MetalMech.hooks.getItemStack("acaciaWood", "BOPBlocks"), MetalMech.hooks.getItemStack("acaciaPlank", 6, 0, "BOPBlocks"));
			MachineRecipes.instance().addRecipe("Sawmill", MetalMech.hooks.getItemStack("cherryWood", "BOPBlocks"), MetalMech.hooks.getItemStack("cherryPlank", 6, 0, "BOPBlocks"));
			MachineRecipes.instance().addRecipe("Sawmill", MetalMech.hooks.getItemStack("darkWood", "BOPBlocks"), MetalMech.hooks.getItemStack("darkPlank", 6, 0, "BOPBlocks"));
			MachineRecipes.instance().addRecipe("Sawmill", MetalMech.hooks.getItemStack("magicWood", "BOPBlocks"), MetalMech.hooks.getItemStack("magicPlank", 6, 0, "BOPBlocks"));
			MachineRecipes.instance().addRecipe("Sawmill", MetalMech.hooks.getItemStack("palmWood", "BOPBlocks"), MetalMech.hooks.getItemStack("palmPlank", 6, 0, "BOPBlocks"));
			MachineRecipes.instance().addRecipe("Sawmill", MetalMech.hooks.getItemStack("mangroveWood", "BOPBlocks"), MetalMech.hooks.getItemStack("mangrovePlank", 6, 0, "BOPBlocks"));
			MachineRecipes.instance().addRecipe("Sawmill", MetalMech.hooks.getItemStack("holyWood", "BOPBlocks"), MetalMech.hooks.getItemStack("holyPlank", 6, 0, "BOPBlocks"));
			
			MachineRecipes.instance().addRecipe("Sawmill", MetalMech.hooks.getItemStack("redwoodPlank", "BOPBlocks"), new ItemStack(Item.stick, 4, 0));
			MachineRecipes.instance().addRecipe("Sawmill", MetalMech.hooks.getItemStack("willowPlank", "BOPBlocks"), new ItemStack(Item.stick, 4, 0));
			MachineRecipes.instance().addRecipe("Sawmill", MetalMech.hooks.getItemStack("firPlank", "BOPBlocks"), new ItemStack(Item.stick, 4, 0));
			MachineRecipes.instance().addRecipe("Sawmill", MetalMech.hooks.getItemStack("acaciaPlank", "BOPBlocks"), new ItemStack(Item.stick, 4, 0));
			MachineRecipes.instance().addRecipe("Sawmill", MetalMech.hooks.getItemStack("cherryPlank", "BOPBlocks"), new ItemStack(Item.stick, 4, 0));
			MachineRecipes.instance().addRecipe("Sawmill", MetalMech.hooks.getItemStack("darkPlank", "BOPBlocks"), new ItemStack(Item.stick, 4, 0));
			MachineRecipes.instance().addRecipe("Sawmill", MetalMech.hooks.getItemStack("magicPlank", "BOPBlocks"), new ItemStack(Item.stick, 4, 0));
			MachineRecipes.instance().addRecipe("Sawmill", MetalMech.hooks.getItemStack("palmPlank", "BOPBlocks"), new ItemStack(Item.stick, 4, 0));
			MachineRecipes.instance().addRecipe("Sawmill", MetalMech.hooks.getItemStack("mangrovePlank", "BOPBlocks"), new ItemStack(Item.stick, 4, 0));
			MachineRecipes.instance().addRecipe("Sawmill", MetalMech.hooks.getItemStack("holyPlank", "BOPBlocks"), new ItemStack(Item.stick, 4, 0));
		}
		catch(Exception e) {}
	}
}
