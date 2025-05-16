package ingredients;

import interfaces.Heldable;

public abstract class Ingredient implements Heldable{
	private String name ;
	
	public Ingredient(String name) {
		setName(name);
	}
	
	public abstract boolean isPrepared(); //tell state of Ingredient

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
