package menu;

import java.util.HashMap;

import interfaces.Heldable;

public abstract class Menu implements Heldable{
	private final HashMap<String,Integer> ingredientRequired ;
	private String name ;
	
	public Menu(String name,HashMap<String,Integer> ingredientRequired){
		setName(name);
		this.ingredientRequired = ingredientRequired;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HashMap<String, Integer> getIngredientRequired() {
		return ingredientRequired;
	}

	public abstract boolean isUsableAsIngredient();
	
	

}
