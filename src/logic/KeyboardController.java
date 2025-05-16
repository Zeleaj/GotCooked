package logic;
import gui.SceneManager;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyboardController {
	//field
	private boolean upPressed = false;
	private boolean downPressed = false;
	private boolean leftPressed = false;
	private boolean rightPressed = false;
	private boolean interactionTriggered = false;
	private boolean escPressed;
	//Constructor
	public KeyboardController() {
		keyboardChecker();
	}
	
	

	public void keyboardChecker() {
        SceneManager.getPresentScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {

                if (e.getCode() == KeyCode.W) {
                    upPressed = true;
                }
                if (e.getCode() == KeyCode.S) {
                    downPressed = true;
                }
                if (e.getCode() == KeyCode.A) {
                    leftPressed = true;
                }
                if (e.getCode() == KeyCode.D) {
                    rightPressed = true;
                }
                if (e.getCode() == KeyCode.E) {
                    interactionTriggered = true;
                }
                if(e.getCode() == KeyCode.ESCAPE) {
                	escPressed = true;
                }
              
              
            }

        });
        SceneManager.getPresentScene().setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {

                if (e.getCode() == KeyCode.W) {
                    upPressed = false;
                }
                if (e.getCode() == KeyCode.S) {
                    downPressed = false;
                }
                if (e.getCode() == KeyCode.A) {
                    leftPressed = false;
                }
                if (e.getCode() == KeyCode.D) {
                    rightPressed = false;
                }
                if (e.getCode() == KeyCode.E) {
                    interactionTriggered = false;}
                if(e.getCode() == KeyCode.ESCAPE) {
                	escPressed = false;
                }
            }
        });
    }
    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }
    
	public boolean isInteraction() {
		 if (interactionTriggered) {
	            interactionTriggered = false; // reset flag หลัง return
	            return true;
	        }
	        return false;
	}
	
	public boolean isEscPressed() {
	    return escPressed;
	}	
	
}
