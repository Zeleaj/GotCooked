package container;

import java.util.ArrayList;

import component.Player;
import gui.GameScreen;
import ingredients.Ingredient;
import interfaces.Heldable;
import interfaces.Interactable;
import logic.StaticObject;
import menu.Menu;
import menu.MenuFactory;
import menu.MergeMenu;

public class RollingMat extends StaticObject implements Interactable{
	private ArrayList<Ingredient> placedIngredients = new ArrayList<>();
	private boolean confirmMerging = false;
	private Dish dishOnMat = null;


	public RollingMat(String name, String imageUrl, double x, double y, boolean isCollidable, boolean isInteractable) {
		super(name, imageUrl, x, y, isCollidable, isInteractable);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void interact() {
		Player player = Player.getInstance();
	    Heldable held = player.getHeldItem();

	    if (held instanceof Dish && dishOnMat == null) {
	        // วางจาน
	        dishOnMat = (Dish) held;
	        player.setHeldItem(null);
	        // ✅ ดึง ingredients จากจานมาด้วย
	        ArrayList<Ingredient> ingredientsFromDish = dishOnMat.getIngredients(); // ตรวจสอบว่า getIngredients() มีใน Dish แล้ว
	        if (ingredientsFromDish != null && !ingredientsFromDish.isEmpty()) {
	            placedIngredients.addAll(ingredientsFromDish);
	            // 📝 รวมชื่อวัตถุดิบเป็นข้อความ
	            StringBuilder ingredientNames = new StringBuilder();
	            for (Ingredient ing : ingredientsFromDish) {
	                ingredientNames.append(ing.getName()).append(", ");
	            }

	            // 🔚 ตัด comma ท้ายสุดออก
	            if (ingredientNames.length() >= 2) {
	                ingredientNames.setLength(ingredientNames.length() - 2);
	            }

	            GameScreen.getInstance().setFeedback("วางจานบน RollingMat แล้ว พร้อมวัตถุดิบ: " + ingredientNames);
	        } else {
	            GameScreen.getInstance().setFeedback("วางจานบน RollingMat แล้ว!");
	        }
	    }

	    else if (held instanceof Ingredient && dishOnMat != null) {
	        // เพิ่มวัตถุดิบ
	        placedIngredients.add((Ingredient) held);
	        player.setHeldItem(null);
	        GameScreen.getInstance().setFeedback("เพิ่ม " + held.getName() + " ลงบน RollingMat");
	    }
	    else if (held instanceof Ingredient && dishOnMat == null) {
	        // ❗ ถือ Ingredient แต่ไม่มีจาน → แจ้งเตือน
	        GameScreen.getInstance().setFeedback("คุณต้องวางจานก่อนใส่วัตถุดิบบน RollingMat");
	    }
	    else if (held == null && placedIngredients.size() >= 2) {
	        // เริ่ม/ยืนยัน merge
	        if (!confirmMerging) {
	            boolean canMerge = MergeMenu.findMatchingMenu(placedIngredients, Dish.class) != null;
	            if (canMerge) {
	                confirmMerging = true;
	                GameScreen.getInstance().setFeedback("สามารถรวมเมนูได้! กดอีกครั้งเพื่อยืนยัน");
	            } else {
	            	GameScreen.getInstance().setFeedback("❌ วัตถุดิบนี้รวมกันไม่ได้ทำการล้างวัตถุดิบ");
	                this.clearIngredients();
	            }
	        }
	        else {
	            String menuName = MergeMenu.findMatchingMenu(placedIngredients, Dish.class);
	            if (menuName != null) {
	                Menu menu = MenuFactory.createMenu(menuName);
	                dishOnMat.setMenu(menu);
	                placedIngredients.clear();
	                player.setHeldItem(dishOnMat);
	                dishOnMat = null;
	                confirmMerging = false;
	                GameScreen.getInstance().setFeedback("รวมเมนูสำเร็จ: " + menuName);
	            }
	        }
	    }

	    else if (held == null && dishOnMat != null && placedIngredients.isEmpty()) {
	        // ดึงจานออก (ถ้ายังไม่ได้ใส่อะไร)
	        player.setHeldItem(dishOnMat);
	        dishOnMat = null;
	        GameScreen.getInstance().setFeedback("คุณหยิบจานคืนจาก RollingMat");
	    }

	    else if (held instanceof Container && !(held instanceof Dish)) {
	    	Container container = (Container) held;
	        if (container.getMenu() != null && dishOnMat != null) {
	            // แปลง menu ใน pot/pan เป็น Ingredient
	            Menu menu = container.getMenu();
	            if (menu.isUsableAsIngredient()) {
	                Ingredient fromMenu = MenuFactory.createIngredientFromMenu(menu);
	                placedIngredients.add(fromMenu);
	                player.setHeldItem(null); // เอามือว่าง
	                GameScreen.getInstance().setFeedback("เท " + menu.getName() + " ใส่จานบน RollingMat แล้ว");
	            } else {
	                GameScreen.getInstance().setFeedback("เมนู " + menu.getName() + " ไม่สามารถใช้เป็นวัตถุดิบได้");
	            }
	        } else {
	            GameScreen.getInstance().setFeedback("RollingMat รองรับเฉพาะจาน หรือภาชนะที่มีเมนูแล้วเทได้");
	        }
	    }

	    else {
	        GameScreen.getInstance().setFeedback("ไม่สามารถทำรายการนี้ได้");
	    }
	}
	public Dish getDishOnMat() {
        return  dishOnMat;
    }
	public void clearIngredients() {
	    placedIngredients.clear();
	    GameScreen.getInstance().setFeedback("🧹 ล้างวัตถุดิบออกจาก RollingMat แล้ว");
	}

	public void setDishOnMat(Dish dishOnMat) {
		this.dishOnMat = dishOnMat;
	}
	

}
