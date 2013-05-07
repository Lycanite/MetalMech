package lycanite.metalmech;

import java.util.HashMap;
import java.util.Map;

import lycanite.metalmech.block.BlockMachineBasic;
import lycanite.metalmech.block.BlockMachineCrusher;
import lycanite.metalmech.block.BlockMachineElectric;
import lycanite.metalmech.block.TileEntityMachine;
import lycanite.metalmech.block.TileEntityMachineCrusher;
import lycanite.metalmech.block.TileEntityMachineElectric;
import lycanite.metalmech.client.IModelRender;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = MetalMech.modid, name = MetalMech.name, version="1.4.1")
@NetworkMod(clientSideRequired=true, serverSideRequired=false, channels = {"MetalMech"}, packetHandler = PacketHandler.class)
public class MetalMech {
	
	public static boolean isRelease = false; //XXX Change to true before releasing and false for testing!
	
	public static final String modid = "MetalMech";
	public static final String name = "Metal Mechanics";
	
	// Instance:
	@Instance("MetalMech")
	public static MetalMech instance;
	
	// Proxy:
	@SidedProxy(clientSide="lycanite.metalmech.client.ClientProxy", serverSide="lycanite.metalmech.CommonProxy")
	public static CommonProxy proxy;
	
	// Creative Tab:
	public static final CreativeTabs creativeTab = new TabMetalMech(CreativeTabs.getNextID(), modid);
	
	// Managers and Handlers:
	private GuiHandler guiHandler = new GuiHandler();
	public static Hooks hooks = new Hooks();
	public RecipeManager recipeManager = new RecipeManager();
	
	
	// Block and Item Titles:
	public static String[] machineBlockTitles = {
		"Lead Furnace",
		"Aluminium Furnace",
		"Titanium Furnace"
	};
	public static String[] machineCrusherBlockTitles = {
		"Lead Crusher",
		"Aluminium Crusher",
		"Titanium Crusher"
	};
	public static String[] electricMachineBlockTitles = {
		"Electric Crusher",
		"Electric Extractor",
		"Electric Compressor"
	};
	
	// Block and Item Names:
	public static String[] machineBlockNames = {
		"leadFurnace",
		"aluminiumFurnace",
		"titaniumFurnace"
	};
	public static String[] machineCrusherBlockNames = {
		"leadCrusher",
		"aluminiumCrusher",
		"titaniumCrusher"
	};
	public static String[] electricMachineBlockNames = {
		"electricCrusher",
		"electricExtractor",
		"electricCompressor"
	};
	
	
	// Blocks:
	public static Object metalSet;
	public static Block machineBlock;
	public static Block machineBlockCrusher;
	public static Block machineBlockElectric;
	
	
	// Models:
	public static Map<String, IModelRender> models = new HashMap<String, IModelRender>();
	
	
	// Pre Init:
	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		Config.init();
		String filepath = event.getSourceFile().getAbsolutePath();
		if(!isRelease) {
				filepath = filepath.substring(0, filepath.length() - 25);
				filepath += "builds/MetalMech.zip";
		}
		hooks.metallurgyGetMetalsetData(modid, filepath);
		metalSet = hooks.metallurgyNewMetalset(modid, creativeTab);
	}
	
	
	// Init:
	@Init
    public void load(FMLInitializationEvent event) {
		
		// Network:
		NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
		
		// Properties:
		TileEntityMachine.setMachineRanks();
		TileEntityMachineElectric.setElectricMachineTypes();
		
		if(Config.furnacesEnabled) {
			machineBlock = new BlockMachineBasic(Config.furnaceID, "furnace/");
			((BlockMachineBasic)machineBlock).registerBlock();
		}
		
		if(Config.crushersEnabled) {
			machineBlockCrusher = new BlockMachineCrusher(Config.crusherID, "furnace/");
			((BlockMachineCrusher)machineBlockCrusher).registerBlock();
		}
		
		if(Config.electricMachinesEnabled) {
			machineBlockElectric = new BlockMachineElectric(Config.electricCrusherID, "furnace/");
			((BlockMachineElectric)machineBlockElectric).registerBlock();
		}
		
		// Models:
		proxy.registerModels();
		
		// Tile Entities:
		GameRegistry.registerTileEntity(TileEntityMachine.class, "TileEntityMachine");
		proxy.registerTileEntities();
		
		// Renderers:
		proxy.registerRenderInformation();
	}
	
	
	// Post Init:
    @PostInit
    public void postInit(FMLPostInitializationEvent event) {
    	recipeManager.addRecipes();
    }
   
}
