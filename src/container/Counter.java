package container;

import component.Player;
import gui.GameScreen;
import ingredients.Ingredient;
import interfaces.Heldable;
import interfaces.Interactable;
import logic.StaticObject;
import menu.MenuFactory;

public class Counter extends StaticObject implements Interactable{
    private Container containerOnCounter = null;  // container ที่วางอยู่บน counter

    public Counter(String name, String staticImageURL, Double xPosition, Double yPosition, boolean isCollidable, boolean isInteractable) {
        super(name,staticImageURL, xPosition, yPosition,isCollidable,isInteractable);
    }

    @Override
    public void interact() {
    	 Player player = Player.getInstance();
    	    Heldable held = player.getHeldItem();

    	    if (held == null) {
    	        // ไม่ถือของ → พยายามหยิบ
    	        if (containerOnCounter != null) {
    	            player.setHeldItem(containerOnCounter);
    	            GameScreen.getInstance().setFeedback("คุณหยิบ " + containerOnCounter.getName() + " จากเคาน์เตอร์");
    	            containerOnCounter = null;
    	        } else {
    	        	 GameScreen.getInstance().setFeedback("ไม่มีอะไรบนเคาน์เตอร์ให้หยิบ");
    	        }
    	    } else if (held instanceof Ingredient) {
    	        // ถือ ingredient
    	        if (containerOnCounter != null) {
    	            containerOnCounter.addIngredient((Ingredient) held);
    	            player.setHeldItem(null);
    	            GameScreen.getInstance().setFeedback("เพิ่ม " + held.getName() + " → เข้า " + containerOnCounter.getName());
    	         // ✅ เพิ่ม check auto merge หลัง add ingredient
    	            if (containerOnCounter.canProcess()) {
    	                containerOnCounter.process();
    	                GameScreen.getInstance().setFeedback("🟢 Auto merge สำเร็จ → " + containerOnCounter.getName());
    	            }
    	        } else {
    	        	GameScreen.getInstance().setFeedback("ไม่มี container บนเคาน์เตอร์เพื่อใส่ " + held.getName());
    	        }
    	    } else if (held instanceof Container) {
    	        // ถือ container
    	        if (containerOnCounter == null) {
    	            containerOnCounter = (Container) held;
    	            // ✅ เซ็ตตำแหน่ง container ให้อยู่บน counter นี้
    	            containerOnCounter.setPosition(this.getX(), this.getY());
    	            player.setHeldItem(null);
    	            GameScreen.getInstance().setFeedback("คุณวาง " + containerOnCounter.getName() + " ลงบนเคาน์เตอร์");
    	        } else {
    	            // merge container
    	            mergeContainer((Container) held, containerOnCounter);
    	            player.setHeldItem(null);
    	            GameScreen.getInstance().setFeedback("รวม " + held.getName() + " → เข้า " + containerOnCounter.getName());
    	        }
    	    } else {
    	    	 GameScreen.getInstance().setFeedback("ของที่ถือ (" + held.getName() + ") ไม่สามารถใช้กับเคาน์เตอร์ได้");
    	    }
    }

    private void mergeContainer(Container from, Container to) {
    	 if (from.getMenu() != null) {
    	        boolean merged = false;

    	        if (to instanceof Dish) {
    	            ((Dish) to).setMenu(from.getMenu());
    	            merged = true;
    	        } else if (to instanceof Bowl) {
    	            ((Bowl) to).setMenu(from.getMenu());
    	            merged = true;
    	        } else if (to instanceof Pot) {
    	            if(from.getMenu().isUsableAsIngredient()) {
    	            	((Pot) to).addIngredient(MenuFactory.createIngredientFromMenu(from.getMenu()));
    	            }
    	            merged = true;
    	        } else if (to instanceof Pan) {
    	        	if(from.getMenu().isUsableAsIngredient()) {
    	            	((Pan) to).addIngredient(MenuFactory.createIngredientFromMenu(from.getMenu()));
    	            }
    	            merged = true;
    	        }

    	        if (merged) {
    	            GameScreen.getInstance().setFeedback("เพิ่มเมนู " + from.getMenu().getName() + " → เข้า " + to.getName());
    	            from.removeContent();
    	            to.updateName();

    	            // ✅ เช็ค auto merge หลังจากย้ายเมนู
    	            if (to.canProcess()) {
    	                to.process();
    	                GameScreen.getInstance().setFeedback("🟢 Auto merge สำเร็จ → " + to.getName());
    	            }
    	        } else {
    	            GameScreen.getInstance().setFeedback("❌ ไม่สามารถ merge: " + to.getName() + " ไม่รองรับเมนู (ไม่ใช่ภาชนะที่รองรับ)");
    	        }
    	    } else {
    	        GameScreen.getInstance().setFeedback("❌ ไม่สามารถ merge: " + from.getName() + " ยังไม่มีเมนู (ต้อง cook ก่อน)");
    	    }
    }

    public Container getContainerOnCounter() {
        return containerOnCounter;
    }

    public void setContainerOnCounter(Container container) {
        this.containerOnCounter = container;
    }

}
