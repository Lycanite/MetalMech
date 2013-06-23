package lycanite.metalmech.item;

import java.util.List;

import cpw.mods.fml.common.registry.LanguageRegistry;

import lycanite.metalmech.MetalMech;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import universalelectricity.core.electricity.ElectricityDisplay;
import universalelectricity.core.electricity.ElectricityPack;
import universalelectricity.core.electricity.ElectricityDisplay.ElectricUnit;
import universalelectricity.core.item.ElectricItemHelper;
import universalelectricity.core.item.IItemElectric;

public class ItemTool extends ItemBase {
	
	// Tool:
	public int durability = 100;
	
	// ==================================================
	//                       Setup
	// ==================================================
	// ========== Constructor ==========
	public ItemTool(int id, String setTexturePath) {
		super(id, setTexturePath);
		setHasSubtypes(false);
		setMaxStackSize(1);
		setMaxDamage(durability);
		setNoRepair();
	}
	
	
	// ==========  Get Item Name from Metadata ==========
	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
        return titles[0];
    }
	
	
	// ========== Get Sub items ==========
	@Override
	public void getSubItems(int par1, CreativeTabs creativeTab, List list) {
		list.add(new ItemStack(this));
	}
	
	
	// ========== Add Info ==========
	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean par4) {
		return;
	}
	
	
	// ========== On Created ==========
	@Override
	public void onCreated(ItemStack itemStack, World world, EntityPlayer par3EntityPlayer) {
		return;
	}
	
	
	// ==================================================
	//                      Visuals
	// ==================================================
	// ========== Register Icons ==========
	@Override
	public void registerIcons(IconRegister iconRegister) {
		itemIcons = new Icon[1];
		itemIcons[0] = iconRegister.registerIcon(MetalMech.modid + ":" + texturePath + itemNames[0]);
	}
	
	
	// ========== Get Texture From Side and Metadata ==========
	public Icon getIconFromDamage(int metadata) {
		return itemIcons[0];
	}
}
