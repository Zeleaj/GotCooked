package component;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import logic.Direction;
import logic.GameManager;

import java.util.Map;

import gui.SceneManager;
import interfaces.Heldable;

public class Player {
    //Field
	private double playerXPos, playerYPos;
    private final int WIDTH = 32, HEIGHT = 32 , startXPos =  7, startYPos = 3;
    private double speed = 0.05;
    private Image playerImg;
    private Heldable heldItem = null;
    //Singleton Object
    private static Player instance;
    // Animation states
    private final Map<Direction, Image> animations = Map.of(
        Direction.UP, SceneManager.imageLoader("image/Player/Playerfacingnorth.png"),
        Direction.DOWN,SceneManager.imageLoader("image/Player/Playerfacingsouth.png"),
        Direction.LEFT, SceneManager.imageLoader("image/Player/Playerfacingwest.png"),
        Direction.RIGHT, SceneManager.imageLoader("image/Player/Playerfacingeast.png"));
    
    //Constructor
    private Player() {
    	reset();
    }
    
    
    //Method
    public void move(Direction direction) {
    	double newPlayerXPos = Player.getInstance().getPlayerXPos();
        double newPlayerYPos = Player.getInstance().getPlayerYPos();
        
        	switch (direction) {
            case UP:
                newPlayerYPos -= speed;
                break;
            case DOWN:
            	newPlayerYPos += speed;
                break;
            case LEFT:
            	newPlayerXPos -= speed;
                break;
            case RIGHT:
            	newPlayerXPos += speed;
                break;
        	}
        if (!GameManager.isIllegalMove(newPlayerXPos, newPlayerYPos)) {
            Player.getInstance().setPlayerXPos(newPlayerXPos); 
            Player.getInstance().setPlayerYPos(newPlayerYPos);
            }
        }
    

    public void updateAnimation(Direction direction) {
        if (animations.containsKey(direction)) {
            playerImg = animations.get(direction);
        }
    }

    public void render(GraphicsContext gc) {
    	gc.drawImage(playerImg, GameManager.positionX(playerXPos),GameManager.positionY(playerYPos), WIDTH, HEIGHT);
    }
    
    public void reset() {
    	setPlayerXPos(startXPos);
        setPlayerYPos(startYPos);
        setSpeed(speed);
        setHeldItem(null);         
        setPlayerImg(getAnimations().get(Direction.DOWN)); // Default animation
        
    }
    //InstanceCheck
    public static Player getInstance() {
    	if(instance == null) {
    		instance = new Player(); // if there are no player instance create new one else return that instance
    	}
    	return instance;
    }
    
    //getter and setter 

	public double getPlayerXPos() {
		return playerXPos;
	}

	public void setPlayerXPos(double newPlayerXPos) {
		this.playerXPos = newPlayerXPos;
	}

	public double getPlayerYPos() {
		return playerYPos;
	}

	public void setPlayerYPos(double playerYPos) {
		this.playerYPos = playerYPos;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	 public Heldable getHeldItem() {
	        return heldItem;
	    }

	 public void setHeldItem(Heldable heldItem) {
	        this.heldItem = heldItem;
	    }

	 public boolean isHolding() {
	        return heldItem != null ;
	        
	    }
	 public boolean isHolding(Class<?> clazz) {
	        return heldItem != null && clazz.isInstance(heldItem) ; //check class that is required 
	    }

	public Image getPlayerImg() {
		return playerImg;
	}

	public void setPlayerImg(Image playerImg) {
		this.playerImg = playerImg;
	}

	public Map<Direction, Image> getAnimations() {
		return animations;
	}

	

    
    
    
    
 
}