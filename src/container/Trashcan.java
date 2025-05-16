package container;

import component.Player;
import gui.GameScreen;
import interfaces.Heldable;
import interfaces.Interactable;
import logic.StaticObject;

public class Trashcan extends StaticObject implements Interactable{

	private boolean isConfirming = false;

    public Trashcan(String name, String imageUrl, double x, double y, boolean isCollidable, boolean isInteractable) {
        super(name, imageUrl, x, y, isCollidable, isInteractable);
    }

    @Override
    public void interact() {
        Player player = Player.getInstance();
        Heldable held = player.getHeldItem();

        if (held == null) {
            GameScreen.getInstance().setFeedback("ไม่มีของในมือจะทิ้ง");
            isConfirming = false; // รีเซ็ตการยืนยันถ้าไม่มีของ
            return;
        }

        if (!isConfirming) {
            GameScreen.getInstance().setFeedback("ยืนยันจะทิ้ง " + held.getName() + " ? (กดอีกครั้งเพื่อทิ้ง)");
            isConfirming = true;
        } else {
            player.setHeldItem(null);
            GameScreen.getInstance().setFeedback("ทิ้งของเรียบร้อยแล้ว!");
            isConfirming = false; // reset state หลังทิ้ง
        }
    }

}
