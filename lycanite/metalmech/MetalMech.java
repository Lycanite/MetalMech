package lycanite.metalmech;

import java.util.HashMap;
import java.util.Map;

import lycanite.metalmech.block.BlockMachineBasic;
import lycanite.metalmech.block.BlockMachineCrusher;
import lycanite.metalmech.block.BlockMachineElectric;
import lycanite.metalmech.block.BlockWire;
import lycanite.metalmech.client.IModelRender;
import lycanite.metalmech.item.ItemBase;
import lycanite.metalmech.item.ItemBattery;
import lycanite.metalmech.item.ItemComponent;
import lycanite.metalmech.item.ItemPlate;
import lycanite.metalmech.item.ItemWrench;
import lycanite.metalmech.machine.MachineManager;
import lycanite.metalmech.tileentity.TileEntityMachine;
import lycanite.metalmech.tileentity.TileEntityMachineCrusher;
import lycanite.metalmech.tileentity.TileEntityElectricBase;
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

@Mod(modid = MetalMech.modid, name = MetalMech.name, version="1.5.1")
@NetworkMod(clientSideRequired=true, serverSideRequired=false, channels = {"MetalMech"}, packetHandler = PacketHandler.class)
public class MetalMech {
	
	public static boolean isRelease = true; //XXX Change to true before releasing and false for testing!
	
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
	public static Block machineBlockModular;
	
	public static Block generatorBlockElectric;
	public static Block generatorBlockModular;
	
	public static Block batteryBlockElectric;
	public static Block batteryBlockModular;
	
	public static Block wireBlock;
	
	// Items:
	public static Item componentItem;
	public static Item plateItem;
	public static Item[] batteryItem;
	public static Item[] wrenchItem;
	
	// Old Models:
	public static Map<String, IModelRender> models = new HashMap<String, IModelRender>();
	
	// Electric:
	public static int lowVoltage = 60;
	public static int mediumVoltage = 120;
	public static int highVoltage = 240;
	public static int extremeVoltage = 480;
	
	public static int lowCapacity = 60;
	public static int mediumCapacity = 120;
	public static int highCapacity = 240;
	public static int extremeCapacity = 480;
	
	
	// ==================================================
	//                Pre-Initialization
	// ==================================================
	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		
		// ========== Create Machines ==========
		MachineManager.addMachines();
		
		// ========== Config ==========
		Config.init(event.getModConfigurationDirectory());
		
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
			machineBlockElectric = new BlockMachineElectric(MachineManager.getMachineBlockID("Machine"), "machines/");
			((BlockMachineBasic)machineBlockElectric).registerBlock();
		}
		
		if(Config.electricGeneratorsEnabled) {
			generatorBlockElectric = new BlockMachineElectric(MachineManager.getMachineBlockID("Generator"), "machines/");
			((BlockMachineBasic)generatorBlockElectric).registerBlock();
		}
		
		if(Config.electricBatteriesEnabled) {
			batteryBlockElectric = new BlockMachineElectric(MachineManager.getMachineBlockID("Battery"), "machines/");
			((BlockMachineBasic)batteryBlockElectric).registerBlock();
		}
		
		if(Config.wiresEnabled) {
			wireBlock = new BlockWire(MachineManager.getMachineBlockID("Wire"), "wires/");
			((BlockWire)wireBlock).registerBlock();
		}
		
		// ========== Create Items ==========
		if(Config.componentsEnabled) {
			componentItem = new ItemComponent(Config.componentsID, "components/");
			((ItemBase)componentItem).registerItem();
		}

		if(Config.platesEnabled) {
			plateItem = new ItemPlate(Config.platesID, "plates/");
			((ItemBase)plateItem).registerItem();
		}

		if(Config.batteryEnabled) {
			batteryItem = new Item[3];
			for(int i = 0; i < batteryItem.length; i++) {
				batteryItem[i] = new ItemBattery(Config.batteryID + i, "batteries/", i);
				((ItemBase)batteryItem[i]).registerItem();
			}
		}

		if(Config.wrenchEnabled) {
			wrenchItem = new Item[2];
			for(int i = 0; i < wrenchItem.length; i++) {
				wrenchItem[i] = new ItemWrench(Config.wrenchID + i, "tools/", i);
				((ItemBase)wrenchItem[i]).registerItem();
			}
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
