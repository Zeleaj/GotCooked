package container;

import java.util.stream.Collectors;

import ingredients.Ingredient;
import menu.Menu;

public class Dish extends Container{

	    @Override
	    public boolean canProcess() { return false; }

	    @Override
	    public void process() { /* nothing */ }

	    @Override
	    protected void updateName() {
	        if (menu != null) {
	            name = "Dish (" + menu.getName() + ")";
	        } else {
	            name = "Dish (empty)";
	        }
	    }

		public void setMenu(Menu menu) {
			super.menu = menu ;
			updateName();
		}
		@Override
		public String getName() {
		    if (menu != null) {
		        return "Dish (" + menu.getName() + ")";
		    } else if (!ingredients.isEmpty()) {
		        String ingredientNames = ingredients.stream()
		            .map(Ingredient::getName)
		            .collect(Collectors.joining(", "));
		        return "Dish [" + ingredientNames + "]";
		    } else {
		        return "Dish (empty)";
		    }}

	
}
