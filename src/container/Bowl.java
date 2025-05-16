package container;

import java.util.stream.Collectors;

import gui.GameScreen;
import ingredients.Ingredient;
import menu.Menu;

public class Bowl extends Container {
	@Override
    public boolean canProcess() {
        return false; // Bowl ไม่ process
    }

    @Override
    public void process() {
        // Bowl ไม่มีการ process อะไร
    	 GameScreen.getInstance().setFeedback("Bowl cannot process ingredients.");
    }

    @Override
    protected void updateName() {
        if (menu != null) {
            name = "Bowl (" + menu.getName() + ")";
        } else {
            name = "Bowl (empty)";
        }
    }

    // เพิ่ม function รับอาหารจาก pot/pan
    public void setMenu(Menu menu) {
        this.menu = menu;
        updateName();
    }
    @Override
	public String getName() {
	    if (menu != null) {
	        return "Bowl (" + menu.getName() + ")";
	    } else if (!ingredients.isEmpty()) {
	        String ingredientNames = ingredients.stream()
	            .map(Ingredient::getName)
	            .collect(Collectors.joining(", "));
	        return "Bowl [" + ingredientNames + "]";
	    } else {
	        return "Bowl (empty)";
	    }}


}
