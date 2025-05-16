package gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;



public abstract class BaseSceneScreen extends Canvas{
    //Constructor
	protected GraphicsContext gc = getGraphicsContext2D();
	protected boolean isActive = false;
    protected final Image defaultBackgroundImage = SceneManager.imageLoader("image/basescene/pixel_art_kitchen_480x270.png");

    public BaseSceneScreen() {
    	this.setWidth(SceneManager.canvaWidth);
    	this.setHeight(SceneManager.canvaHeight); 
    }
    
    //method
     
    public abstract void render(GraphicsContext gc);
    
    public abstract void onEnter();
    
    public abstract void reset();
    
    public void drawDefaultBackground() {
    	gc.drawImage(defaultBackgroundImage, 0, 0, SceneManager.canvaWidth, SceneManager.canvaHeight);
    }
    public void clear() {
        gc.clearRect(0, 0, getWidth(), getHeight());
    }
    
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { this.isActive = active; }
    
}
