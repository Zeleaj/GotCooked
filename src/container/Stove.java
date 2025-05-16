package container;

import component.Player;
import gui.GameScreen;
import gui.SceneManager;
import interfaces.CookMethod;
import interfaces.Cookable;
import interfaces.Heldable;
import logic.AnimatedObject;
import menu.Menu;

public class Stove extends AnimatedObject {
    public Stove(String name, String[] imageFrameURL, Double xPosition, Double yPosition, boolean isCollidable, boolean isInteractable) {
        super(name, imageFrameURL, xPosition, yPosition,isCollidable,isInteractable);
    }

    @Override
    public void interact() {
    	Heldable held = Player.getInstance().getHeldItem();
    	Cookable cookable = null;
    	if (held == null) {
            GameScreen.getInstance().setFeedback("มือของคุณว่างเปล่า! กรุณาถือวัตถุดิบหรือภาชนะก่อนปรุงอาหาร");
            return;
        }

    	// ✅ ถ้า held เป็น Cookable → ใช้ตรง ๆ
    	if (held instanceof Cookable) {
    	    cookable = (Cookable) held;
    	}
    	// ✅ ถ้า held เป็น Container → ลองเช็ค menu ข้างใน
    	else if (held instanceof Container) {
    	    Menu menu = ((Container) held).getMenu();
    	    if (menu instanceof Cookable) {
    	        cookable = (Cookable) menu;
    	    }
    	}

    	CookMethod method = null;
    	if (held instanceof Pot) {
    	    method = CookMethod.BOIL;
    	} else if (held instanceof Pan) {
    	    method = CookMethod.FRY;
    	}

    	if (cookable != null && method != null) {
    	    if (!cookable.isCooked()) {
    	        cookable.cook(method);
    	        SceneManager.switchToCookingWaitScene();
    	        GameScreen.getInstance().setFeedback(held.getName() + " ถูกปรุงด้วยวิธี " + method + " แล้ว!");
    	    } else {
    	    	 GameScreen.getInstance().setFeedback(held.getName() + " ถูกปรุงด้วยวิธี " + method + " เรียบร้อยแล้ว (ไม่ต้องปรุงซ้ำ)");
    	    }
    	} else {
    		 GameScreen.getInstance().setFeedback("ของที่ถือ (" + held.getName() + ") ไม่สามารถปรุงบนเตาได้!");
    	}
}}

