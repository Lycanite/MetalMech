package lycanite.metalmech;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class MachineRecipes {
	
	private static final MachineRecipes instance = new MachineRecipes();
	public static Map<String, Map> recipes = new HashMap<String, Map>();
	public static Map<String, Map> pendingRecipes = new HashMap<String, Map>();
	
	// Get Instance:
	public static final MachineRecipes instance() {
		return instance;
	}
	
	
	//=============================================//
	//                 Get Recipes                 //
	//=============================================//
	
	// Get Recipe List:
	public Map getRecipes(String machineType) {
		return recipes.get(machineType);
	}
	
	// Get Recipe Result:
	public ItemStack getResult(String machineType, ItemStack itemStack) {
		
		// With Meta:
		ItemStack output = (ItemStack)recipes.get(machineType).get(Arrays.asList(new Integer[] { Integer.valueOf(itemStack.itemID), Integer.valueOf(itemStack.getItemDamage()) }));
		if(output != null) return output;
	    
		return null;
	}
	
	
	//=============================================//
	//                 Add Recipes                 //
	//=============================================//
	
	// Add Recipe Directly:
	public static void addRecipe(String machineType, int itemID, int metadata, ItemStack itemStack) {
		if(recipes.get(machineType) == null) recipes.put(machineType, new HashMap());
		recipes.get(machineType).put(Arrays.asList(new Integer[] { Integer.valueOf(itemID), Integer.valueOf(metadata) }), itemStack);
	}
	
	// Add Recipe with ItemStack or ore Registry and Stack Size:
	public static void addRecipe(String machineType, Object inputItem, Object outputStack, int outputAmount) {
		if(inputItem == null) return;
		if(outputStack == null) return;
		
		// Output
		ArrayList<ItemStack> outputList = new ArrayList<ItemStack>();
		if(outputStack instanceof ItemStack) outputList.add((ItemStack)outputStack);
		else if(outputStack instanceof String) outputList = OreDictionary.getOres((String)outputStack);
		if(outputList.size() < 1) {
			System.out.println("[MetalMech] WARNING: Unable to find output " + outputStack + " for " + machineType + " recipe.");
			return;
		}
		ItemStack output = outputList.get(0).copy(); // Only one output.
		
		// Input
		ArrayList<ItemStack> inputList = new ArrayList<ItemStack>();
		if(inputItem instanceof ItemStack)  inputList.add((ItemStack)inputItem);
		else if(inputItem instanceof Item)  inputList.add(new ItemStack((Item)inputItem));
		else if(inputItem instanceof Block)  inputList.add(new ItemStack((Block)inputItem));
		else if(inputItem instanceof String) {
			inputList = OreDictionary.getOres((String)inputItem);
			if(inputList.size() < 1) addPendingRecipe(machineType, (String)inputItem, new ItemStack(output.itemID, outputAmount, output.getItemDamage()));
		}
		
		// Add Recipes:
		for(ItemStack input : inputList)
			addRecipe(machineType, input.itemID, input.getItemDamage(), new ItemStack(output.itemID, outputAmount, output.getItemDamage()));
	}
	
	// Add Recipe with ItemStack Output:
	public static void addRecipe(String machineType, Object inputItem, ItemStack outputItem) {
		addRecipe(machineType, inputItem, outputItem, outputItem.stackSize);
	}
	
	
	//=============================================//
	//                Pending Recipes              //
	//=============================================//
	
	// Add Pending Recipe:
	public static void addPendingRecipe(String machineType, String inputName, ItemStack outputStack) {
		if(pendingRecipes.get(machineType) == null) pendingRecipes.put(machineType, new HashMap());
		pendingRecipes.get(machineType).put(inputName, outputStack);
	}
	
	// Check Pending Recipes:
	public static void checkPendingRecipes(String name, ItemStack oreStack) {
		// Search the pending recipes of each machine:
		for(Map.Entry<String, Map> pendingRecipeSet : pendingRecipes.entrySet()) {
			if(pendingRecipeSet.getValue().get(name) != null) {
				addRecipe(pendingRecipeSet.getKey(), oreStack, (ItemStack)pendingRecipeSet.getValue().get(name));
			}
		}
	}
}