package gui;

import component.Player;
import ingredients.IngredientFactory;
import interfaces.Heldable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;


public class ChillerScene extends BaseSceneScreen {
	    public ChillerScene() {
	        // เพิ่ม mouse listener (ถ้าต้องการตรวจคลิก)
	        this.setOnMouseClicked(this::handleMouseClick);
	    }

	    @Override
	    public void render(GraphicsContext gc) {
	        clear(); // เคลียร์ canvas ก่อน
	        gc.drawImage(SceneManager.imageLoader("image/basescene/chiller_inventory2.png"), 0, 0, getWidth(), getHeight()); // วาด background เต็ม canvas
	    }

	    @Override
	    public void onEnter() {
	        render(this.gc);
	        setActive(true);
	    }

	    @Override
	    public void reset() {
	        setActive(false);
	    }

	    private void handleMouseClick(MouseEvent event) {
	    	double x = event.getX();
	        double y = event.getY();

	        String clickedItem = getClickedIngredient(x, y);
	        if (clickedItem != null) {
	            // ลองให้ player รับของ
	            Heldable item = IngredientFactory.createIngredient(clickedItem) ; // หรือสร้าง object ตามจริง
	            Player player = Player.getInstance();
	            player.setHeldItem(item);
	            SceneManager.switchToGameScreen();
	            GameScreen.getInstance().setFeedback("Picked up: " + clickedItem);
}
	        }
	    

	    private String getClickedIngredient(double x, double y) {
	        class Box {
	            double x, y, width, height;
	            String name;
	            Box(double x, double y, double width, double height, String name) {
	                this.x = x; this.y = y; this.width = width; this.height = height; this.name = name;
	            }
	            boolean contains(double px, double py) {
	                return px >= x && px <= x + width && py >= y && py <= y + height;
	            }
	        }

	        Box[] boxes = new Box[] {
	        	    // แถว 1 (y = 52)
	        	    new Box(38,  52, 101, 66, "Wagame"),   // ← เปลี่ยนชื่อให้ถูกแล้ว
	        	    new Box(139, 52, 101, 66, "Miso"),
	        	    new Box(240, 52, 101, 66, "Seaweed"),
	        	    new Box(341, 52, 101, 66, "Noodle"),

	        	    // แถว 2 (y = 118)
	        	    new Box(38,  118, 101, 66, "RawRice"),
	        	    new Box(139, 118, 101, 66, "Salmon"),
	        	    new Box(240, 118, 101, 66, "Tuna"),
	        	    new Box(341, 118, 101, 66, "Egg"),

	        	    // แถว 3 (y = 184)
	        	    new Box(38,  184, 101, 66, "Sugar"),
	        	    new Box(139, 184, 101, 66, "Pork"),
	        	    new Box(240, 184, 101, 66, "Flour")
	        	    // ช่องสุดท้ายว่าง (ไม่ต้องใส่ก็ได้)
	        	};

	        for (Box box : boxes) {
	            if (box.contains(x, y)) {
	                return box.name;
	            }
	        }
	        return null;
	    }

}

