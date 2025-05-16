package gui;

import javafx.scene.canvas.GraphicsContext;


public class MainMenuScreen extends BaseSceneScreen{

	double startX = 169;
    double startY = 86;
    double startWidth = 141;
    double startHeight = 18;

    @Override
    public void onEnter() {
    	gc.drawImage(SceneManager.imageLoader("image/basescene/mainMenuScreen.png"),0,0);
    	 this.setOnMouseClicked(e -> {
             double mouseX = e.getX();
             double mouseY = e.getY();

             if (mouseX >= startX && mouseX <= startX + startWidth &&
                 mouseY >= startY && mouseY <= startY + startHeight) {
                 GameScreen.getInstance().reset();
                 logic.GameManager.startGame();
                 
             } 
             else if (mouseX >= 170 && mouseX <= 310 && mouseY >= 133 && mouseY <= 153) {
                 SceneManager.switchToOptionMenuScreen();  // <-- YOU NEED THIS METHOD
             } 
             else if (mouseX >= 170 && mouseX <= 170 + 140 &&  mouseY >= 180 && mouseY <= 180 + 20) {
            	 SceneManager.getPrimaryStage().close();
             }
             
         });
    	 
    	 this.setOnMouseMoved(e -> {
    	        double mouseX = e.getX();
    	        double mouseY = e.getY();

    	        if ((mouseX >= startX && mouseX <= startX + startWidth && mouseY >= startY && mouseY <= startY + startHeight) ||
    	            (mouseX >= 170 && mouseX <= 310 && mouseY >= 133 && mouseY <= 153) ||
    	            (mouseX >= 170 && mouseX <= 310 && mouseY >= 180 && mouseY <= 200)) {
    	            this.setCursor(javafx.scene.Cursor.HAND);
    	        } else {
    	            this.setCursor(javafx.scene.Cursor.DEFAULT);
    	        }
    	    });
    }


    @Override
    public void render(GraphicsContext gc) {
       onEnter();
    }

    @Override
    public void reset() {
        // Reset UI state if needed
    }
}
