package lycanite.metalmech;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreDictionary.OreRegisterEvent;

public class RecipesOreRegistry {
	@ForgeSubscribe
	public void OreRegisterEvent(OreRegisterEvent event) {
        //System.out.println("Ore Registered: " + event.Name);
		MachineRecipes.checkPendingRecipes(event.Name, event.Ore);
    }
}
