package container;

import component.Player;
import gui.GameScreen;
import gui.SceneManager;
import logic.AnimatedObject;

public class Chiller extends AnimatedObject {

	    public Chiller(String name, String[] imageFrameURL, double x, double y, boolean isCollidable, boolean isInteractable) {
	        super(name, imageFrameURL, x, y,isCollidable,isInteractable);
	    }

	    @Override
	    public void interact() {
	        if (Player.getInstance().getHeldItem() == null) {
	            SceneManager.switchToChillerScene();
	        } else {
	            GameScreen.getInstance().setFeedback("มือ player ไม่ว่าง!");
	        }
	    }
	

}
