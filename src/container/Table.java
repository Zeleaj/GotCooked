package container;

import component.Player;
import gui.GameScreen;
import gui.SceneManager;
import interfaces.Interactable;
import logic.StaticObject;

public class Table extends StaticObject implements Interactable {

	public Table(String name, String staticImageURL, Double xPosition, Double yPosition, boolean isCollidable, boolean isInteractable) {
		super(name, staticImageURL, xPosition, yPosition,isCollidable,isInteractable);
	}

	@Override
	public void interact() {
		 if (Player.getInstance().getHeldItem() == null) {
		        // มือว่าง → เข้า scene เลือก container
		        SceneManager.switchToContainerSelectScene();
		    } else {
		        // มือไม่ว่าง → ไม่ให้เข้า
		    	 GameScreen.getInstance().setFeedback("คุณถือ " + Player.getInstance().getHeldItem().getName() + " อยู่ ไม่สามารถเลือก container ได้");
	}

}}
