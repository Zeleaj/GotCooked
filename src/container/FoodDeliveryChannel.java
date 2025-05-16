package container;

import component.Player;
import gui.GameScreen;
import interfaces.Heldable;
import interfaces.Interactable;
import logic.GameManager;
import logic.StaticObject;
import logic.Task;
import logic.TaskManager;

public class FoodDeliveryChannel extends StaticObject implements Interactable{

	public FoodDeliveryChannel(String name, String imageUrl, double x, double y, boolean isCollidable,
			boolean isInteractable) {
		super(name, imageUrl, x, y, isCollidable, isInteractable);
	}

	@Override
	public void interact() {
		 Player player = Player.getInstance();
		    Heldable held = player.getHeldItem();
		    Task task = TaskManager.getCurrentTask();

		    if (task != null && task.isCompletedBy(held)) {
		        GameScreen.getInstance().setFeedback("✅ เสิร์ฟสำเร็จ!");
		        player.setHeldItem(null);
		        GameManager.addScore(task.getScore());
		        System.out.println("[SCORE DEBUG] Score is now: " + GameManager.getScore());
		        TaskManager.finishCurrentTask();
		        TaskManager.generateTask(); // ✅ สร้าง Task ใหม่ทันที

		    } else {
		        GameScreen.getInstance().setFeedback("❌ ยังไม่ตรงกับออเดอร์");
		    }
	}
	
}
