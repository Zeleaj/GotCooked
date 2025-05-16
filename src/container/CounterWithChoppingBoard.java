package container;

import component.Player;
import gui.GameScreen;
import interfaces.Choppable;
import interfaces.Interactable;
import logic.StaticObject;

public class CounterWithChoppingBoard extends StaticObject implements Interactable{

	public CounterWithChoppingBoard(String name, String imageUrl, double x, double y, boolean isCollidable,
			boolean isInteractable) {
		super(name, imageUrl, x, y, isCollidable, isInteractable);
	}
	
	@Override
	public void interact() {
		if (Player.getInstance().isHolding(Choppable.class)) {
            ((Choppable) Player.getInstance().getHeldItem()).chop();
        } else {
        	 GameScreen.getInstance().setFeedback("Nothing to chop.");
        }
	}

}
