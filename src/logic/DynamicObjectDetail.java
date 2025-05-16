package logic;

public class DynamicObjectDetail {
	private String[]  imageFrameURL;
    private double x;
    private double y;
    private boolean isInteractable;
    private boolean isCollidable;
    private long frameInterval;
    private String name;

	public String[] getFrames() {
		return imageFrameURL;
	}
	public void setFrames(String[] frames) {
		this.imageFrameURL = frames;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	
	public boolean isInteractable() {
		return isInteractable;
	}
	public void setInteractable(boolean isInteractable) {
		this.isInteractable = isInteractable;
	}
	public boolean isCollidable() {
		return isCollidable;
	}
	public void setCollidable(boolean isCollidable) {
		this.isCollidable = isCollidable;
	}
	public long getFrameInterval() {
		return frameInterval;
	}
	public void setFrameInterval(long frameInterval) {
		this.frameInterval = frameInterval;
	}
	public String[] getImageFrameURL() {
		return this.imageFrameURL;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    
    
}
