package gui;

import component.Player;
import container.Bowl;
import container.Dish;
import container.Pan;
import container.Pot;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public class ContainerSelectScene extends BaseSceneScreen{
	private static final int ICON_SIZE = 80; // เพิ่มขนาด box

	private static final double POT_X = 130;
	private static final double POT_Y = 60;

	private static final double PAN_X = 270;
	private static final double PAN_Y = 60;

	private static final double DISH_X = 130;
	private static final double DISH_Y = 160;

	private static final double BOWL_X = 270;
	private static final double BOWL_Y = 160;
	public ContainerSelectScene() {
        super();
        this.setOnMouseClicked(this::handleMouseClick); //lambda expression e -> handleMouseClick(e)

	}
	private void handleMouseClick(MouseEvent e) {
        double x = e.getX();
        double y = e.getY();

        if (isInside(x, y, POT_X, POT_Y, ICON_SIZE, ICON_SIZE)) {
            Player.getInstance().setHeldItem(new Pot());;
            SceneManager.switchToGameScreen();
            GameScreen.getInstance().setFeedback("ตอนนี้กำลังถือ Pot");
        } else if (isInside(x, y, PAN_X, PAN_Y, ICON_SIZE, ICON_SIZE)) {
            Player.getInstance().setHeldItem(new Pan());
            SceneManager.switchToGameScreen();
            GameScreen.getInstance().setFeedback("ตอนนี้กำลังถือ Pan");
        } else if (isInside(x, y, DISH_X, DISH_Y, ICON_SIZE, ICON_SIZE)) {
            Player.getInstance().setHeldItem(new Dish());
            SceneManager.switchToGameScreen();
            GameScreen.getInstance().setFeedback("ตอนนี้กำลังถือ Dish");
        } else if (isInside(x, y, BOWL_X, BOWL_Y, ICON_SIZE, ICON_SIZE)) {
            Player.getInstance().setHeldItem(new Bowl());
            SceneManager.switchToGameScreen();
            GameScreen.getInstance().setFeedback("ตอนนี้กำลังถือ Bowl");

        }
    }

	@Override
	public void render(GraphicsContext gc) {
		 gc.drawImage(SceneManager.imageLoader("image/basescene/ContainerBackground1.png"), 0, 0, getWidth(), getHeight());
	}
	
	@Override
	public void onEnter() {
	}

	@Override
	public void reset() {
	}
	private boolean isInside(double x, double y, double rectX, double rectY, double width, double height) {
	        return x >= rectX && x <= rectX + width && y >= rectY && y <= rectY + height;
	    }
	
}
