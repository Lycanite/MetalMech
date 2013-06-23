package lycanite.metalmech.block;

import java.util.Random;

import universalelectricity.core.item.IItemElectric;
import universalelectricity.prefab.implement.IToolConfigurator;
import lycanite.metalmech.GuiHandler;
import lycanite.metalmech.MetalMech;
import lycanite.metalmech.client.ClientProxy;
import lycanite.metalmech.item.ItemBlockMachine;
import lycanite.metalmech.machine.MachineManager;
import lycanite.metalmech.tileentity.TileEntityElectricBase;
import lycanite.metalmech.tileentity.TileEntityElectricBattery;
import lycanite.metalmech.tileentity.TileEntityElectricGenerator;
import lycanite.metalmech.tileentity.TileEntityElectricMachine;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMachineElectric extends BlockMachineBasic {
	
	// Visuals:
	Icon[] blockIcons;
		
	// ========== Constructor ==========
	public BlockMachineElectric(int id, String texture) {
		super(id, texture);
	}
	
	
	// ========== Block Behaviour ==========
   // Placement:
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
        
        if(tileEntity instanceof TileEntityElectricBase) {
        	((TileEntityElectricBase)tileEntity).setFacing(targetFacing);
    		((TileEntityElectricBase)tileEntity).initiate();
        }
    }
    
    
	// Tile Entity:
	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		String category = MachineManager.getCategory(blockID).name;
		if(category == "Machine")
			return new TileEntityElectricMachine();
		else if(category == "Generator")
			return new TileEntityElectricGenerator();
		else if(category == "Battery")
			return new TileEntityElectricBattery();
		return null;
	}
	
	
	// Block Activated:
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
            return true;
        }
		
		if(player.isSneaking()) {
			return false;
		}
		
		if(player.inventory.getCurrentItem() != null) {
			if((player.inventory.getCurrentItem().getItem() instanceof IToolConfigurator)) {
				world.notifyBlocksOfNeighborChange(x, y, z, this.blockID);
				((IToolConfigurator)player.inventory.getCurrentItem().getItem()).wrenchUsed(player, x, y, z);
				
				return onUseWrench(world, x, y, z, player, side, hitX, hitY, hitZ);
			}
			if((player.inventory.getCurrentItem().getItem() instanceof IItemElectric)) {
				if(onUseElectricItem(world, x, y, z, player, side, hitX, hitY, hitZ)) return true;
			}
		}
	
	    return onMachineActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
	}
	
	
	// Machine Activated:
	public boolean onMachineActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		player.openGui(MetalMech.instance, GuiHandler.GUIType.ELECTRIC_MACHINE.id + world.getBlockMetadata(x, y, z), world, x, y, z);
		return true;
	}
	
	
	// Machine Wrenched:
	public boolean onUseWrench(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		TileEntityElectricBase tileEntity = (TileEntityElectricBase)world.getBlockTileEntity(x, y, z);
		int facing = tileEntity.getFacing() - 2;
		
		switch (facing) {
			case 0:
				facing = 3;
				break;
			case 3:
				facing = 1;
				break;
			case 1:
				facing = 2;
				break;
			case 2:
				facing = 0;
		}
		
		tileEntity.setFacingWithUpdate(facing + 2);
		return true;
	}
	@Deprecated
	public boolean onSneakUseWrench(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return false;
	}
	
	
	// Machine Electric Item:
	public boolean onUseElectricItem(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return false;
	}
	
	
	// ========== Block Visuals ==========
	// Render as Block:
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	
	// Is Opaque:
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	
	// Get Render Type:
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType() {
		return ClientProxy.RENDER_ID;
	}
	
	
	// Random Display Tick:
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random) {
		TileEntityElectricBase tileEntity = (TileEntityElectricBase)world.getBlockTileEntity(x, y, z);
        if((tileEntity.isActive() && tileEntity.jouleStorage <= 0) || (tileEntity.isReceiving() && tileEntity.jouleStorage > 0)) {
        	float zPos = (float)x + 0.5F;
	        float yPos = (float)y + 0.0F + random.nextFloat() * 6.0F / 16.0F;
	        float xPos = (float)z + 0.5F;
	        float fixed = 0.52F;
	        float random2 = random.nextFloat() * 0.6F - 0.3F;
	
	        switch(tileEntity.getFacing()) {
	        	case 2:
	        		world.spawnParticle("smoke", (double)(zPos + random2), (double)yPos, (double)(xPos - fixed), 0.0D, 0.0D, 0.0D);
		            world.spawnParticle("reddust", (double)(zPos + random2), (double)yPos, (double)(xPos - fixed), 0.0D, 0.0D, 0.0D);
		            break;
	        	case 3:
	        		world.spawnParticle("smoke", (double)(zPos + random2), (double)yPos, (double)(xPos + fixed), 0.0D, 0.0D, 0.0D);
		            world.spawnParticle("reddust", (double)(zPos + random2), (double)yPos, (double)(xPos + fixed), 0.0D, 0.0D, 0.0D);
		            break;
	        	case 4:
	        		world.spawnParticle("smoke", (double)(zPos - fixed), (double)yPos, (double)(xPos + random2), 0.0D, 0.0D, 0.0D);
		            world.spawnParticle("reddust", (double)(zPos - fixed), (double)yPos, (double)(xPos + random2), 0.0D, 0.0D, 0.0D);
		            break;
	        	case 5:
	        		world.spawnParticle("smoke", (double)(zPos + fixed), (double)yPos, (double)(xPos + random2), 0.0D, 0.0D, 0.0D);
		            world.spawnParticle("reddust", (double)(zPos + fixed), (double)yPos, (double)(xPos + random2), 0.0D, 0.0D, 0.0D);
		            break;
	        }
        }
    }
	
	
	// Register Icons:
	@Override
	public void registerIcons(IconRegister iconRegister) {
		int subBlocks = MachineManager.getCategory(blockID).machineCount;
		Icon[] setBlockIcons = new Icon[subBlocks];
		for(int i = 0; i < subBlocks; i++)
			setBlockIcons[i] = iconRegister.registerIcon(MetalMech.modid + ":" + texturePath + MachineManager.getMachineInfo(blockID, i).name);
		blockIcons = setBlockIcons;
	}
	
	
	// Get Texture From Side and Meta:
	@Override
	public Icon getIcon(int side, int metadata) {
		return blockIcons[metadata];
	}
}
