package lycanite.metalmech.block;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.MathHelper;

public class SlotMachine extends Slot {
	
	// Info:
    private EntityPlayer playerEntity;
    private int experienceStored;
    
    // Constructor:
    public SlotMachine(EntityPlayer player, IInventory inventory, int x, int y, int z) {
        super(inventory, x, y, z);
        this.playerEntity = player;
    }
    
    
    // Valid Item Check:
    public boolean isItemValid(ItemStack par1ItemStack) {
        return false;
    }
    
    
    // Decrease Item Stack Size:
    public ItemStack decrStackSize(int par1) {
        if (this.getHasStack()) {
            this.experienceStored += Math.min(par1, this.getStack().stackSize);
        }

        return super.decrStackSize(par1);
    }
    
    
    // Pickup Item From Slot:
    public void onPickupFromSlot(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack) {
        this.onCrafting(par2ItemStack);
        super.onPickupFromSlot(par1EntityPlayer, par2ItemStack);
    }
    
    
    // On Taking the Processed Item with field_75228_b???:
    protected void onCrafting(ItemStack par1ItemStack, int par2)
    {
        this.experienceStored += par2;
        this.onCrafting(par1ItemStack);
    }
    
    
    // On Taking the Processed Item:
    protected void onCrafting(ItemStack itemStack)
    {
    	itemStack.onCrafting(this.playerEntity.worldObj, this.playerEntity, this.experienceStored);

        if (!this.playerEntity.worldObj.isRemote)
        {
            int var2 = this.experienceStored;
            float var3 = FurnaceRecipes.smelting().getExperience(itemStack);
            int var4;

            if (var3 == 0.0F) {
                var2 = 0;
            }
            else if (var3 < 1.0F) {
                var4 = MathHelper.floor_float((float)var2 * var3);

                if (var4 < MathHelper.ceiling_float_int((float)var2 * var3) && (float)Math.random() < (float)var2 * var3 - (float)var4) {
                    ++var4;
                }

                var2 = var4;
            }

            while (var2 > 0) {
                var4 = EntityXPOrb.getXPSplit(var2);
                var2 -= var4;
                this.playerEntity.worldObj.spawnEntityInWorld(new EntityXPOrb(this.playerEntity.worldObj, this.playerEntity.posX, this.playerEntity.posY + 0.5D, this.playerEntity.posZ + 0.5D, var4));
            }
        }

        this.experienceStored = 0;
        
        GameRegistry.onItemSmelted(playerEntity, itemStack);
        
        
        // Achievements:
        if (itemStack.itemID == Item.ingotIron.itemID)
        {
            this.playerEntity.addStat(AchievementList.acquireIron, 1);
        }
        
        if (itemStack.itemID == Item.fishCooked.itemID)
        {
            this.playerEntity.addStat(AchievementList.cookFish, 1);
        }
    }
}
