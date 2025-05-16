package container;

import component.Player;
import gui.GameScreen;
import ingredients.Ingredient;
import ingredients.IngredientFactory;
import interfaces.Washable;
import logic.AnimatedObject;

public class Sink extends AnimatedObject {
	boolean hasWater = false;

	public Sink(String name, String[] imageFrameURL, Double xPosition, Double yPosition, boolean isCollidable, boolean isInteractable) {
		super(name, imageFrameURL, xPosition, yPosition,isCollidable,isInteractable);
	}

	@Override
	public void interact() {

		if (Player.getInstance().isHolding(Pot.class) ) {
			 GameScreen.getInstance().setFeedback("Player is holding Pot");
	        Pot pot = (Pot) Player.getInstance().getHeldItem();
	        for (Ingredient ing : pot.getIngredients()) {
	            if (ing.getName().equals("Water")) {
	                hasWater = true;
	                break;
	            }
	        }

	        if (hasWater) {
	        	 GameScreen.getInstance().setFeedback("Already Have Water !!!!!");
	        } else {
	            pot.addIngredient((Ingredient)IngredientFactory.createIngredient("Water"));
	            GameScreen.getInstance().setFeedback("Added water to pot!");
	            if(pot.canProcess()) {
		            pot.process();
		        GameScreen.getInstance().setFeedback("Merge to:" + pot.getMenu());
	            }
	            
	        }
	        
	    }
	    else if (Player.getInstance().isHolding(Washable.class)) {
	        ((Washable) Player.getInstance().getHeldItem()).wash();
	        GameScreen.getInstance().setFeedback("Item washed!");
	    }
	    else {
	    	 GameScreen.getInstance().setFeedback("Nothing to wash or fill.");
	    }
	}
	

}
