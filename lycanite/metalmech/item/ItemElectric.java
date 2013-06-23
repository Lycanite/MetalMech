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

public class ItemElectric extends ItemBase implements IItemElectric {
	
	// Electricity:
	public double jouleStorage = 0;
	
	// ==================================================
	//                       Setup
	// ==================================================
	// ========== Constructor ==========
	public ItemElectric(int id, String setTexturePath) {
		super(id, setTexturePath);
		setHasSubtypes(false);
		setMaxStackSize(1);
		setMaxDamage(100);
		setNoRepair();
	}
	
	
	// ========== Register Item ==========
	@Override
	public void registerItem() {
		for(int i = 0; i < 100; i++) {
			ItemStack itemStack = new ItemStack(itemID, 1, i);
			LanguageRegistry.addName(itemStack, titles[0]);
			if(oreRegistry) {
				OreDictionary.registerOre(itemNames[0], itemStack);
				OreDictionary.registerOre(oreNames[0], itemStack);
			}
			System.out.println("Battery: " + i);
		}
	}
	
	
	// ==========  Get Item Name from Metadata ==========
	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
        return titles[0];
    }
	
	
	// ========== Get Sub items ==========
	@Override
	public void getSubItems(int par1, CreativeTabs creativeTab, List list) {
		list.add(ElectricItemHelper.getUncharged(new ItemStack(this)));
		ItemStack chargedItem = new ItemStack(this);
		list.add(ElectricItemHelper.getWithCharge(chargedItem, getJouleStorage(chargedItem)));
	}
	
	
	// ========== Add Info ==========
	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer entityPlayer, List list, boolean par4) {
		String color = "";
		double amps = getAmpsStored(itemStack);

		if(amps <= getJouleStorage(itemStack) / 3)
			color = "\u00a74";
		else if (amps > this.getJouleStorage(itemStack) * 2 / 3)
			color = "\u00a72";
		else
			color = "\u00a76";
		list.add(color + (int)(amps / 1000) + "kJ" + " / " + (int)(getJouleStorage(itemStack) / 1000) + "kJ");
	}
	
	
	// ========== On Created ==========
	@Override
	public void onCreated(ItemStack itemStack, World world, EntityPlayer par3EntityPlayer) {
		setAmpsStored(0, itemStack);
	}
	
	
	// ==================================================
	//                    Electricity
	// ==================================================
	// ========== Get Amps Stored ==========
	public double getJouleStorage(ItemStack itemStack) {
		return jouleStorage;
	}
	@Override
	public double getMaxJoules(ItemStack itemStack) {
		return getAmpsStored(itemStack);
	}
	
	
	// ========== Get Voltage ==========
	@Override
	public double getVoltage(ItemStack itemStack) {
		return MetalMech.mediumVoltage;
	}
	
	
	// ========== On Receive ==========
	@Override
	public ElectricityPack onReceive(ElectricityPack electricityPack, ItemStack itemStack) {
		double rejectedElectricity = Math.max((this.getJoules(itemStack) + electricityPack.getWatts()) - getJouleStorage(itemStack), 0);
		double joulesToStore = electricityPack.getWatts() - rejectedElectricity;
		this.setJoules(this.getJoules(itemStack) + joulesToStore, itemStack);
		return ElectricityPack.getFromWatts(joulesToStore, this.getVoltage(itemStack));
	}
	
	
	// ========== On Provide ==========
	@Override
	public ElectricityPack onProvide(ElectricityPack electricityPack, ItemStack itemStack) {
		double electricityToUse = Math.min(this.getJoules(itemStack), electricityPack.getWatts());
		this.setJoules(this.getJoules(itemStack) - electricityToUse, itemStack);
		return ElectricityPack.getFromWatts(electricityToUse, this.getVoltage(itemStack));
	}
	
	
	// ========== Get Receive Request ==========
	@Override
	public ElectricityPack getReceiveRequest(ItemStack itemStack) {
		return ElectricityPack.getFromWatts(Math.min(getJouleStorage(itemStack) - this.getJoules(itemStack), this.getTransferRate(itemStack)), this.getVoltage(itemStack));
	}
	
	
	// ========== Get Provide Request ==========
	@Override
	public ElectricityPack getProvideRequest(ItemStack itemStack) {
		return ElectricityPack.getFromWatts(Math.min(this.getJoules(itemStack), this.getTransferRate(itemStack)), this.getVoltage(itemStack));
	}
	
	
	// ========== Get Transfer Rate ==========
	public double getTransferRate(ItemStack itemStack) {
		return getJouleStorage(itemStack) * 0.01;
	}
	
	
	// ========== Set Amps ==========
	public void setAmpsStored(double amps, ItemStack itemStack) {
		if (itemStack.getTagCompound() == null)
			itemStack.setTagCompound(new NBTTagCompound());

		double ampsStored = Math.max(Math.min(amps, getJouleStorage(itemStack)), 0);
		itemStack.getTagCompound().setDouble("electricity", ampsStored);

		itemStack.setItemDamage((int) (100 - (ampsStored / getJouleStorage(itemStack)) * 100));
	}
	@Override
	public void setJoules(double joules, ItemStack itemStack) {
		setAmpsStored(joules, itemStack);
	}
	
	
	// ========== Get Amps ==========
	public double getAmpsStored(ItemStack itemStack) {
		if (itemStack.getTagCompound() == null)
			return 0;

		double ampsStored = itemStack.getTagCompound().getDouble("electricity");

		itemStack.setItemDamage((int) (100 - (ampsStored / getJouleStorage(itemStack)) * 100));
		return ampsStored;
	}
	@Override
	public double getJoules(ItemStack itemStack) {
		return getAmpsStored(itemStack);
	}
	
	
	// ========== Needs Charge ==========
	public boolean needsCharge() {
		return getAmpsStored(new ItemStack(this.itemID, 1, 0)) < getJouleStorage(new ItemStack(this.itemID, 1, 0));
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
