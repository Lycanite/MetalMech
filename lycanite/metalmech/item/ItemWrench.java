package lycanite.metalmech.item;

import universalelectricity.prefab.implement.IToolConfigurator;
import lycanite.metalmech.MetalMech;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class ItemWrench extends ItemTool implements IToolConfigurator {
	
	// Info:
	public int toolType = 0;
	public static String[] baseItemNames = {
		"bronzeWrench", "steelWrench"
	};
	public static String[] baseNames = {
		"BronzeWrench", "Steel Wrench"
	};
	public static String[] baseTitles = {
		"Bronze Wrench", "Steel Wrench"
	};
	public static String[] baseOreNames = {
		"wrench", "wrench"
	};
	public static int[] baseDurability = {
		5000, 5000
	};
	
	// ==================================================
	//                       Setup
	// ==================================================
	// ========== Constructor ==========
	public ItemWrench(int id, String setTexturePath, int setToolType) {
		super(id, setTexturePath);
		toolType = setToolType;
		itemNames[0] = baseItemNames[toolType];
		names[0] = baseNames[toolType];
		titles[0] = baseTitles[toolType];
		oreNames[0] = baseOreNames[toolType];
		durability = baseDurability[toolType];
		setMaxDamage(durability);
		oreRegistry = true;
	}
	
	
	// ==================================================
	//                       Tool
	// ==================================================
	// ========== Can Wrench ==========
	@Override
	public boolean canWrench(EntityPlayer entityPlayer, int x, int y, int z) {
		return true;
	}
	
	
	// ========== Wrench Used ==========
	@Override
	public void wrenchUsed(EntityPlayer entityPlayer, int x, int y, int z) {
		if (entityPlayer.getCurrentEquippedItem() != null)
			if (entityPlayer.getCurrentEquippedItem().getItem() instanceof ItemWrench)
				entityPlayer.getCurrentEquippedItem().damageItem(1, entityPlayer);
	}
	
	
	// ========== On Item Use First ==========
	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer entityPlayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		int blockID = world.getBlockId(x, y, z);

		if (blockID == Block.furnaceIdle.blockID || blockID == Block.furnaceBurning.blockID || blockID == Block.dropper.blockID || blockID == Block.hopperBlock.blockID || blockID == Block.dispenser.blockID || blockID == Block.pistonBase.blockID || blockID == Block.pistonStickyBase.blockID) {
			int metadata = world.getBlockMetadata(x, y, z);

			int[] rotationMatrix = { 1, 2, 3, 4, 5, 0 };

			if (blockID == Block.furnaceIdle.blockID || blockID == Block.furnaceBurning.blockID)
			{
				rotationMatrix = ForgeDirection.ROTATION_MATRIX[0];
			}

			world.setBlockMetadataWithNotify(x, y, z, ForgeDirection.getOrientation(rotationMatrix[metadata]).ordinal(), 3);
			this.wrenchUsed(entityPlayer, x, y, z);

			return true;
		}

		return false;
	}
	
	
	// ========== On Item Use ==========
	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		return false;
	}
	
	
	// ========== Should Pass Sneaking Click To Block ==========
	@Override
	public boolean shouldPassSneakingClickToBlock(World world, int x, int y, int z) {
		return true;
	}
}
