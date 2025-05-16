package logic;
import gui.SceneManager;
import interfaces.Interactable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class AnimatedObject implements Interactable{
	private String name;
	private String[] imageURL;
	private String staticImageURL;
	private double xPosition;
	private double yPosition;
	private boolean isInteractable;
	private long frameInterval = 100_000_000; // default 10 fps
	private long lastFrameTime;
	private Boolean isCollidable;
	private Image[] frames;
	private int currentFrameIndex = 0;


	//For Dynamic Object
	public AnimatedObject(String name,String[] imageFrameURL,Double xPosition,Double yPosition,Boolean isInteractable,Boolean isCollidable){
		setName(name);
		setImageURL(imageFrameURL);
		setxPosition(xPosition);
		setyPosition(yPosition);
		setInteractable(isInteractable());
		setIsCollidable(isCollidable);
		preloadFrames();
	}
	void render(GraphicsContext gc) {
		if (frames != null && frames.length > 0) {
	        gc.drawImage(frames[currentFrameIndex], xPosition, yPosition);
	    }	
		}
	
	public void preloadFrames() {
	    frames = new Image[imageURL.length];
	    for (int i = 0; i < imageURL.length; i++) {
	        frames[i] = SceneManager.imageLoader(imageURL[i]);
	    }
	}
	public void updateFrame() {
	    long now = System.nanoTime();
	    if (now - lastFrameTime >= frameInterval) {
	        currentFrameIndex = (currentFrameIndex + 1) % frames.length;
	        lastFrameTime = now;
	    }
	}


	//Getter and Setter 

	public String getName() {
		return name;
	}
	private void setName(String name) {
		this.name = name;
	}
	public String[] getImageURL() {
		return imageURL;
	}
	private void setImageURL(String[] imageURL) {
		this.imageURL = imageURL;
	}
	public String getStaticImageURL() {
		return staticImageURL;
	}
	public double getxPosition() {
		return xPosition;
	}
	private void setxPosition(double xPosition) {
		this.xPosition = xPosition;
	}
	public double getyPosition() {
		return yPosition;
	}
	private void setyPosition(double yPosition) {
		this.yPosition = yPosition;
	}
	
	public long getFrameInterval() {
		return frameInterval;
	}
	public long getLastFrameTime() {
		return lastFrameTime;
	}
	public boolean isInteractable() {
		return isInteractable;
	}
	public void setInteractable(boolean isInteractable) {
		this.isInteractable = isInteractable;
	}
	public Boolean getIsCollidable() {
		return isCollidable;
	}
	public void setIsCollidable(Boolean isCollidable) {
		this.isCollidable = isCollidable;
	}
	public Image getCurrentFrame() {
	    return frames[currentFrameIndex];
	}
	@Override
	public void interact() {
		 System.out.println("Interacted with AnimatedObject: " + getName());
    		
	}

	
	
}
