package container;

import java.util.ArrayList;

import ingredients.Ingredient;
import interfaces.Heldable;
import menu.Menu;

public abstract class Container implements Heldable{
	protected String name;  // Display name (จะ update ตาม state)
	    protected ArrayList<Ingredient> ingredients = new ArrayList<>();
	    protected Menu menu; // ถ้า process เสร็จ → menu ที่ได้
	    protected double x, y;
	    public abstract boolean canProcess(); // สำหรับ Pot/Pan → process ได้มั้ย
	    public abstract void process(); // process อาหาร (boil, fry)

	    public void addIngredient(Ingredient ing) {
	        ingredients.add(ing);
	        updateName();
	    }

	    public void removeContent() {
	        ingredients.clear();
	        menu = null;
	        updateName();
	    }

	    protected abstract void updateName(); // update ชื่อ display ตาม state

	    public boolean isEmpty() {
	        return ingredients.isEmpty() && menu == null;
	    }
	    public Menu getMenu() {
	        return this.menu;
	    }

	    public ArrayList<Ingredient> getIngredients() {
	        return this.ingredients;
	    }

	    public String getName() {
	        return this.name;
	    }
	    public void setPosition(double x, double y) {
	        this.x = x; this.y = y;
	    }
	    public double getX() { return x; }
	    public double getY() { return y; }

}
