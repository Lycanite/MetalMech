package lycanite.metalmech.block;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lycanite.metalmech.CommonProxy;
import lycanite.metalmech.GuiHandler;
import lycanite.metalmech.MetalMech;
import lycanite.metalmech.block.TileEntityMachine;
import lycanite.metalmech.item.ItemBlockMachine;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

public class BlockMachineBasic extends BlockContainer {
	
	// Info:
	public Random random = new Random();
	public String texturePath;
	public Map<String, Icon> blockIcons = new HashMap<String, Icon>();
	public Map<String, Icon> blockIconsTop = new HashMap<String, Icon>();
	public Map<String, Icon> blockIconsFront = new HashMap<String, Icon>();
	public Map<String, Icon> blockIconsActive = new HashMap<String, Icon>();
	
	// Constructor:
	public BlockMachineBasic(int id, String texture) {
		super(id, Material.iron);
		this.texturePath = texture;
        setHardness(3.5F);
        MinecraftForge.setBlockHarvestLevel(this, "pickaxe", 1);
        setStepSound(Block.soundMetalFootstep);
        setCreativeTab(MetalMech.creativeTab);
        //setRequiresSelfNotify();
	}
	
	
	// Get Names:
	public String[] getNames() {
		return MetalMech.machineBlockNames;
	}
	
	
	// Get Titles:
	public String[] getTitles() {
		return MetalMech.machineBlockTitles;
	}
	
	
	// Register Block:
	public void registerBlock() {
		Item.itemsList[this.blockID] = new ItemBlockMachine(this.blockID - 256);
		for(int subBlock = 0; subBlock < this.getNames().length; subBlock++) {
			LanguageRegistry.instance().addStringLocalization(this.getNames()[subBlock] + ".name", this.getTitles()[subBlock]);
		}
	}
	
	
	// Get Sub Blocks (For Creative Tabs):
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int unknown, CreativeTabs tab, List subItems) {
		for(int itemMeta = 0; itemMeta < getNames().length; itemMeta++) {
			subItems.add(new ItemStack(this, 1, itemMeta));
		}
	}
	
	
	// Drop ID:
	@Override
	public int idDropped(int par1, Random random, int zero) {
        return this.blockID;
	}
	
	
	// Drop Meta:
	@Override
	public int damageDropped(int metadata) {
		return metadata;
	}
	
	
	// Register Icons:
	@Override
	public void registerIcons(IconRegister iconRegister) {
		for(String machineRankN : TileEntityMachine.machineRankMetadata.keySet()) {
			this.blockIcons.put(machineRankN, iconRegister.registerIcon(MetalMech.modid + ":" + texturePath + machineRankN + "Side"));
			this.blockIconsTop.put(machineRankN, iconRegister.registerIcon(MetalMech.modid + ":" + texturePath + machineRankN + "Top"));
			this.blockIconsFront.put(machineRankN, iconRegister.registerIcon(MetalMech.modid + ":" + texturePath + machineRankN + "Front"));
			this.blockIconsActive.put(machineRankN, iconRegister.registerIcon(MetalMech.modid + ":" + texturePath + machineRankN + "Active"));
		}
	}
	
	
	// Get Texture:
	@Override
	public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side) {
		int metadata = world.getBlockMetadata(x, y, z);
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		int facing = 2;
		boolean isActive = false;
		
		if(tileEntity instanceof TileEntityMachine) {
			facing = ((TileEntityMachine)tileEntity).facing;
			isActive = ((TileEntityMachine)tileEntity).isActive();
		}
		if(tileEntity instanceof TileEntityMachineElectric) {
			facing = ((TileEntityMachineElectric)tileEntity).facing;
			isActive = ((TileEntityMachineElectric)tileEntity).isActive();
		}
		
		switch(side) {
			case 1: return this.blockIconsTop.get(TileEntityMachine.getRankFromMetadata(metadata)); // Top
			case 0: return this.blockIconsTop.get(TileEntityMachine.getRankFromMetadata(metadata)); // Bottom
			default:
				if(side == facing) {
					if(isActive) {
						return this.blockIconsActive.get(TileEntityMachine.getRankFromMetadata(metadata)); // Front On
					}
					else {
						return this.blockIconsFront.get(TileEntityMachine.getRankFromMetadata(metadata)); // Front
					}
				}
				else {
					return this.blockIcons.get(TileEntityMachine.getRankFromMetadata(metadata)); // Sides
				}
		}
	}
	
	
	// Get Icon from Side and Metadata:
	@Override
	public Icon getIcon(int side, int metadata) {
		switch(side) {
			case 1: return this.blockIconsTop.get(TileEntityMachine.getRankFromMetadata(metadata)); // Top
			case 0: return this.blockIconsTop.get(TileEntityMachine.getRankFromMetadata(metadata)); // Bottom
			case 3: return this.blockIconsFront.get(TileEntityMachine.getRankFromMetadata(metadata)); // Front (Inventory)
			default: return this.blockIcons.get(TileEntityMachine.getRankFromMetadata(metadata)); // Sides
		}
	}
	
	
	// Block Loaded:
    public void onBlockAdded(World world, int x, int y, int z) {
        super.onBlockAdded(world, x, y, z);
        //this.setDefaultDirection(world, x, y, z);
    }
    
    
    // Set Default Direction:
    private void setDefaultDirection(World world, int x, int y, int z) {
        if (!world.isRemote) {
            int blockBehind = world.getBlockId(x, y, z - 1);
            int blockInfront = world.getBlockId(x, y, z + 1);
            int blockLeft = world.getBlockId(x - 1, y, z);
            int blockRight = world.getBlockId(x + 1, y, z);
            short rotation = 3;
            
            if (Block.opaqueCubeLookup[blockBehind] && !Block.opaqueCubeLookup[blockInfront]) {
            	rotation = 3; // Front
            }
            
            if (Block.opaqueCubeLookup[blockInfront] && !Block.opaqueCubeLookup[blockBehind]) {
            	rotation = 2; // Back
            }
            
            if (Block.opaqueCubeLookup[blockLeft] && !Block.opaqueCubeLookup[blockRight]) {
            	rotation = 5; // Right
            }
            
            if (Block.opaqueCubeLookup[blockRight] && !Block.opaqueCubeLookup[blockLeft]) {
            	rotation = 4; // Left
            }
            
            TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
            if(tileEntity instanceof TileEntityMachine)
            	((TileEntityMachine)tileEntity).setFacing(rotation);
            if(tileEntity instanceof TileEntityMachineElectric)
            	((TileEntityMachineElectric)tileEntity).setFacing(rotation);
        }
    }
    
    
	// On Block Placed:
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entity, ItemStack itemStack) {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
    	int rotation = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        int targetFacing = 3;
    	
        switch(rotation) {
        	case 0:
        		targetFacing = 2;
        		break;
        	case 1:
        		targetFacing = 5;
        		break;
        	case 2:
        		targetFacing = 3;
        		break;
        	case 3:
        		targetFacing = 4;
        		break;
        }
        
        if(tileEntity instanceof TileEntityMachine)
        	((TileEntityMachine)tileEntity).setFacing(targetFacing);
        if(tileEntity instanceof TileEntityMachineElectric)
        	((TileEntityMachineElectric)tileEntity).setFacing(targetFacing);
    }
	
	
	// On Block Activated:
	@Override
	public boolean onBlockActivated(World world, int blockX, int blockY, int blockZ, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
            return true;
        }
		
		TileEntity tileEntity = world.getBlockTileEntity(blockX, blockY, blockZ);
		
		if(tileEntity == null || player.isSneaking()) {
			return false;
		}
		
		player.openGui(MetalMech.instance, GuiHandler.GUIType.MACHINE.id + world.getBlockMetadata(blockX, blockY, blockZ), world, blockX, blockY, blockZ);
		return true;
	}
	
	
	// Break Block:
	@Override
	public void breakBlock(World world, int blockX, int blockY, int blockZ, int par5, int par6) {
		spillItems(world, blockX, blockY, blockZ);
		super.breakBlock(world, blockX, blockY, blockZ, par5, par6);
    }
	
	
	// Spill Items:
	public void spillItems(World world, int blockX, int blockY, int blockZ) {
		TileEntity tileEntity = world.getBlockTileEntity(blockX, blockY, blockZ);
		if(tileEntity == null || !(tileEntity instanceof IInventory)) {
			return;
		}
		
		IInventory inventory = (IInventory) tileEntity;
        for (int n = 0; n < inventory.getSizeInventory(); n++) {
            ItemStack itemStack = inventory.getStackInSlot(n);
            
            if (itemStack != null) {
                float direction1 = this.random.nextFloat() * 0.8F + 0.1F;
                float direction2 = this.random.nextFloat() * 0.8F + 0.1F;
                float direction3 = this.random.nextFloat() * 0.8F + 0.1F;

                while (itemStack.stackSize > 0) {
                    int spawnRandom = this.random.nextInt(21) + 10;

                    if (spawnRandom > itemStack.stackSize) {
                    	spawnRandom = itemStack.stackSize;
                    }
                    
                    itemStack.stackSize -= spawnRandom;
                    EntityItem itemEntity = new EntityItem(world, (double)((float)blockX + direction1), (double)((float)blockY + direction2), (double)((float)blockZ + direction3), new ItemStack(itemStack.itemID, spawnRandom, itemStack.getItemDamage()));

                    if(itemStack.hasTagCompound()) {
                    	itemEntity.getEntityItem().setTagCompound((NBTTagCompound) itemStack.getTagCompound().copy()); // was func_92014_d now getEntityItem
                    }
                    
                    float factor = 0.05F;
                    itemEntity.motionX = (double)((float)this.random.nextGaussian() * factor);
                    itemEntity.motionY = (double)((float)this.random.nextGaussian() * factor + 0.2F);
                    itemEntity.motionZ = (double)((float)this.random.nextGaussian() * factor);
                    world.spawnEntityInWorld(itemEntity);
                    //itemStack.stackSize = 0;
                }
            }
        }
	}
	
	
	// Tile Entity:
	@Override
	public TileEntity createNewTileEntity(World world) {
		TileEntityMachine tileEntity = new TileEntityMachine();
		return tileEntity;
	}
	
	
	// Update Machine Block State:
	public static void updateBlockState(World world, int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        
        world.theProfiler.startSection("checkLight");
        world.updateAllLightTypes(x, y, z);
        world.theProfiler.endSection();
        world.markBlockForUpdate(x, y, z);
    }
	
	
	// Block Lighting:
	@Override
	public int getLightValue(IBlockAccess world,int  x,int y,int z){
	    try { 
	    	TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		     if (tileEntity != null) {
		    	 if(tileEntity instanceof TileEntityMachine) {
		    		 if(((TileEntityMachine)tileEntity).isActive()) {
			        	 return 15;
			         }
		    	 }
		    	 if(tileEntity instanceof TileEntityMachineElectric) {
		    		 if(((TileEntityMachineElectric)tileEntity).isActive()) {
			        	 return 15;
			         }
		    	 }
		     }
	    }
	    catch(Exception e) { }
	     return 0;
	}
	
	
	// Random Display Tick:
	@Override
	public void randomDisplayTick(World world, int x, int y, int z, Random random) {
		TileEntityMachine tileEntity = (TileEntityMachine)world.getBlockTileEntity(x, y, z);
        if(tileEntity.isActive()) {
        	float zPos = (float)x + 0.5F;
	        float yPos = (float)y + 0.0F + random.nextFloat() * 6.0F / 16.0F;
	        float xPos = (float)z + 0.5F;
	        float fixed = 0.52F;
	        float random2 = random.nextFloat() * 0.6F - 0.3F;
	
	        switch(tileEntity.facing) {
	        	case 2:
	        		world.spawnParticle("smoke", (double)(zPos + random2), (double)yPos, (double)(xPos - fixed), 0.0D, 0.0D, 0.0D);
		            world.spawnParticle("flame", (double)(zPos + random2), (double)yPos, (double)(xPos - fixed), 0.0D, 0.0D, 0.0D);
		            break;
	        	case 3:
	        		world.spawnParticle("smoke", (double)(zPos + random2), (double)yPos, (double)(xPos + fixed), 0.0D, 0.0D, 0.0D);
		            world.spawnParticle("flame", (double)(zPos + random2), (double)yPos, (double)(xPos + fixed), 0.0D, 0.0D, 0.0D);
		            break;
	        	case 4:
	        		world.spawnParticle("smoke", (double)(zPos - fixed), (double)yPos, (double)(xPos + random2), 0.0D, 0.0D, 0.0D);
		            world.spawnParticle("flame", (double)(zPos - fixed), (double)yPos, (double)(xPos + random2), 0.0D, 0.0D, 0.0D);
		            break;
	        	case 5:
	        		world.spawnParticle("smoke", (double)(zPos + fixed), (double)yPos, (double)(xPos + random2), 0.0D, 0.0D, 0.0D);
		            world.spawnParticle("flame", (double)(zPos + fixed), (double)yPos, (double)(xPos + random2), 0.0D, 0.0D, 0.0D);
		            break;
	        }
        }
    }
}