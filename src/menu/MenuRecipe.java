package menu;

import java.util.HashMap;

import container.Dish;
import container.Pan;
import container.Pot;

public class MenuRecipe {
	public static final HashMap<String,Recipe> ALL_RECIPES = new HashMap<>();

	 static {
		 	HashMap<String, Integer> friedPorkRecipe = new HashMap<>();
		 	friedPorkRecipe.put("Pork", 1);
		 	ALL_RECIPES.put("FriedPork", new Recipe(friedPorkRecipe, Pan.class));

		 	HashMap<String, Integer> soupRecipe = new HashMap<>();
	        soupRecipe.put("Water", 1);
	        soupRecipe.put("Wagame", 1);
	        soupRecipe.put("Miso", 1);
	        ALL_RECIPES.put("Soup", new Recipe(soupRecipe, Pot.class));
	        
	        HashMap<String, Integer> cookedRiceRecipe = new HashMap<>();
	        cookedRiceRecipe.put("Water", 1);
	        cookedRiceRecipe.put("RawRice", 1);
	        ALL_RECIPES.put("Rice", new Recipe(cookedRiceRecipe, Pot.class)); // cook ด้วยหม้อ
	        
	        HashMap<String, Integer> salmonSushiRecipe = new HashMap<>();
	        salmonSushiRecipe.put("Seaweed", 1);
	        salmonSushiRecipe.put("Rice", 1);
	        salmonSushiRecipe.put("Salmon", 1);
	        ALL_RECIPES.put("SalmonSushi", new Recipe(salmonSushiRecipe,Dish.class));

	        HashMap<String, Integer> tunaSushiRecipe = new HashMap<>();
	        tunaSushiRecipe.put("Seaweed", 1);
	        tunaSushiRecipe.put("Rice", 1);
	        tunaSushiRecipe.put("Tuna", 1);
	        ALL_RECIPES.put("TunaSushi", new Recipe(tunaSushiRecipe, Dish.class));

	        HashMap<String, Integer> ramenRecipe = new HashMap<>();
	        ramenRecipe.put("Soup", 1);
	        ramenRecipe.put("Noodle", 1);
	        ramenRecipe.put("FriedPork",1);
	        ALL_RECIPES.put("Ramen", new Recipe(ramenRecipe, Pot.class));

	        HashMap<String, Integer> omuriceRecipe = new HashMap<>();
	        omuriceRecipe.put("Egg", 1);
	        omuriceRecipe.put("Rice", 1);
	        ALL_RECIPES.put("Omurice", new Recipe(omuriceRecipe, Pan.class));

	        HashMap<String, Integer> pancakeRecipe = new HashMap<>();
	        pancakeRecipe.put("Flour", 1);
	        pancakeRecipe.put("Sugar", 1);
	        pancakeRecipe.put("Egg", 1);
	        ALL_RECIPES.put("Pancake", new Recipe(pancakeRecipe, Pan.class));

	        HashMap<String, Integer> breadRecipe = new HashMap<>();
	        breadRecipe.put("Flour", 1);
	        breadRecipe.put("Egg", 1);
	        ALL_RECIPES.put("Bread", new Recipe(breadRecipe, Pan.class));
	    }
}	
