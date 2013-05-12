package lycanite.metalmech;

import java.util.HashMap;
import java.util.Map;

import lycanite.metalmech.block.BlockMachineBasic;
import lycanite.metalmech.block.BlockMachineCrusher;
import lycanite.metalmech.block.BlockMachineElectric;
import lycanite.metalmech.client.IModelRender;
import lycanite.metalmech.tileentity.TileEntityMachine;
import lycanite.metalmech.tileentity.TileEntityMachineCrusher;
import lycanite.metalmech.tileentity.TileEntityMachineElectric;
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

@Mod(modid = MetalMech.modid, name = MetalMech.name, version="1.4.3")
@NetworkMod(clientSideRequired=true, serverSideRequired=false, channels = {"MetalMech"}, packetHandler = PacketHandler.class)
public class MetalMech {
	
	public static boolean isRelease = false; //XXX Change to true before releasing and false for testing!
	
	public static final String modid = "MetalMech";
	public static final String name = "Metal Mechanics";
	public static String filepath;
	
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
	
	// Blocks:
	public static Object metalSet;
	public static Block machineBlock;
	public static Block machineBlockCrusher;
	public static Block machineBlockElectric;
	
	
	// Models:
	public static Map<String, IModelRender> models = new HashMap<String, IModelRender>();
	
	
	// ==================================================
	//                Pre-Initialization
	// ==================================================
	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		
		// ========== Create Machines ==========
		MachineManager.addMachines();
		
		// ========== Config ==========
		Config.init();
		
		// ========== Create Metalset ==========
		filepath = event.getSourceFile().getAbsolutePath();
		if(!isRelease) {
				filepath = filepath.substring(0, filepath.length() - 25);
				filepath += "resources/MetalMech.zip";
		}
		hooks.metallurgyGetMetalsetData(modid, filepath);
		metalSet = hooks.metallurgyNewMetalset(modid, creativeTab);
	}
	
	
	// ==================================================
	//                  Initialization
	// ==================================================
	@Init
    public void load(FMLInitializationEvent event) {
		
		// ========== Network ==========
		NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
		
		// ========== Create Blocks ==========
		if(Config.furnacesEnabled) {
			machineBlock = new BlockMachineBasic(MachineManager.getMachineBlockID("Furnace"), "furnace/");
			((BlockMachineBasic)machineBlock).registerBlock();
		}
		
		if(Config.crushersEnabled) {
			machineBlockCrusher = new BlockMachineCrusher(MachineManager.getMachineBlockID("Crusher"), "furnace/");
			((BlockMachineBasic)machineBlockCrusher).registerBlock();
		}
		
		if(Config.electricMachinesEnabled) {
			machineBlockElectric = new BlockMachineElectric(MachineManager.getMachineBlockID("Electric"), "furnace/");
			((BlockMachineBasic)machineBlockElectric).registerBlock();
		}
		
		// ========== Models ==========
		proxy.registerModels();
		
		// ========== Tile Entities ==========
		GameRegistry.registerTileEntity(TileEntityMachine.class, "TileEntityMachine");
		proxy.registerTileEntities();
		
		// ========== Renderers ==========
		proxy.registerRenderInformation();
	}
	
	
	// ==================================================
	//                Post-Initialization
	// ==================================================
    @PostInit
    public void postInit(FMLPostInitializationEvent event) {
    	
    	// ========== Add Recipes ==========
    	recipeManager.addRecipes();
    }
   
}
