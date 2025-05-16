package gui;



import component.Player;
import interfaces.Interactable;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import logic.Direction;
import logic.GameManager;
import logic.KeyboardController;
import logic.SoundManager;
import logic.Task;
import logic.TaskManager;

public class SceneManager extends Canvas{
	//field
    private final static String iconURL = "image/gotcookedicon.png";
    private final static String gameName = "GotCooked!";
	public static int canvaWidth = 480 ;
    public static int canvaHeight = 270 ;
	public final static int topPadding = 14 ;
	public final static int pixelSize = 32 ;
    public final static int totalHorizontalTiles = 15 ;
    public final static int totalVerticalTiles = 8 ;
    //obj field
    public static KeyboardController keyboardChecker;
    private static Stage primaryStage ;
    private static Scene presentScene;
    private static final MainMenuScreen menuScreen = new MainMenuScreen();
    //There are more scene.
    private static final ChillerScene chillerScene = new ChillerScene();
    private static final ContainerSelectScene containerScene = new ContainerSelectScene();
    
    private static AnimationTimer playerMoveTimer;    
    
    
    private static final CookingWaitScene cookingWaitScene = new CookingWaitScene();
    
    private static final PauseScreen pauseScreen = new PauseScreen();
    
    private static final ResultScreen resultScreen = new ResultScreen();
    
    private static Thread gameTimerThread = null;
    private static boolean gameTimerStarted = false;
    
    private static OptionMenuScreen optionMenuScreen = new OptionMenuScreen();
    private static RecipeCollectionScreen recipeCollectionScreen = new RecipeCollectionScreen();
    
    
    
    //Method
    public void initialize(Stage stage) {
    	setPrimaryStage(stage);
    	StackPane root = new StackPane();
    	root.getChildren().add(menuScreen); // Change to BaseScreen Later   
        menuScreen.onEnter();
    	GameScreen.getInstance().addObject(); // Download from Json 
    	GameScreen.getInstance().preloadStaticImages(); // Download to cached prevent lag * *
    	GameScreen.getInstance().preloadDynamicObjects();
    	GameScreen.getInstance().addDynamicObject();
    	Scene baseScene = new Scene(root,canvaWidth,canvaHeight);
    	setPresentScene(baseScene);
    	getPrimaryStage().setScene(getPresentScene());
        getPrimaryStage().getIcons().add(SceneManager.imageLoader(iconURL));
        getPrimaryStage().setTitle(gameName);
        getPrimaryStage().setResizable(false);
        Platform.runLater(() -> {
            GameScreen.getInstance().render(GameScreen.getInstance().getGraphicsContext2D());
        });
        getPrimaryStage().show();
        SoundManager.playMenuBGM();
        TaskManager.generateTask(); 
    	}
    public static void switchToMenuScreen() {
    	//Do later
    	if (playerMoveTimer != null) {
            playerMoveTimer.stop(); // Stop game loop if running
        }
    	
        StackPane root = new StackPane();
        root.getChildren().add(menuScreen);
        menuScreen.onEnter(); // Reset or prepare menu visuals

        Scene menuScene = new Scene(root, canvaWidth, canvaHeight);
        setPresentScene(menuScene);
        getPrimaryStage().setScene(menuScene);
        SoundManager.playMenuBGM();
        SoundManager.stopGameBGM();
        stopGameTimer();
    }
    
    public static void switchToPauseScreen() {
        if (playerMoveTimer != null) {
            playerMoveTimer.stop();
        }

        StackPane root = new StackPane();
        root.getChildren().add(pauseScreen);
        pauseScreen.onEnter();

        Scene scene = new Scene(root, canvaWidth, canvaHeight);
        setPresentScene(scene);
        getPrimaryStage().setScene(scene);
        SoundManager.stopMenuBGM();
        stopGameTimer();
    }
    
    public static void switchToOptionMenuScreen() {
        optionMenuScreen.onEnter();
    }

    public static void switchToRecipeCollectionScreen() {
        recipeCollectionScreen.onEnter();
    }

    
    public static void switchToGameScreen() {
        StackPane root = new StackPane();
        root.getChildren().add(GameScreen.getInstance());
        GameScreen.getInstance().onEnter();
        TaskManager.generateTask(); 

        Scene gameScene = new Scene(root, canvaWidth, canvaHeight);
        setPresentScene(gameScene);
        getPrimaryStage().setScene(gameScene);

        Player.getInstance().render(GameScreen.getInstance().gc);
        keyboardChecker = new KeyboardController();
        updatePlayerMove();
        SceneManager.startGameTimer(GameScreen.getInstance().getGraphicsContext2D());
        SoundManager.stopMenuBGM();
        if (!SoundManager.isGameBgmPlaying) {
            SoundManager.playGameBgm(); // ✅ Ensure GameBGM plays again
        }
        SoundManager.stopCook();
        SoundManager.stopServe();
    	}
    
    public static void switchToChillerScene() {
    	if (playerMoveTimer != null) {
    	    playerMoveTimer.stop();
    	}
        StackPane root = new StackPane();
        chillerScene.onEnter(); // Setup screen visuals and click logic
        root.getChildren().add(chillerScene);
        Scene scene = new Scene(root, canvaWidth, canvaHeight);
        setPresentScene(scene);
        getPrimaryStage().setScene(scene);
        stopGameTimer();
    }
    
    public static void switchToCookingWaitScene() {
    	if (playerMoveTimer != null) {
    	    playerMoveTimer.stop();
    	}
        StackPane root = new StackPane();
        root.getChildren().add(cookingWaitScene);
        cookingWaitScene.onEnter();
        Scene cookingScene = new Scene(root, canvaWidth, canvaHeight);
        setPresentScene(cookingScene);
        getPrimaryStage().setScene(cookingScene);
        SoundManager.playCook();
        stopGameTimer();
    }
    
    public static void switchToResultScreen() {
    	 if (playerMoveTimer != null) {
    	        playerMoveTimer.stop();
    	        SoundManager.stopGameBGM();
    	    }
    	    resultScreen.onEnter();  // <== This must call the correct logic
    	    SoundManager.stopGameBGM();
        
    }
    
    public static Image imageLoader(String url) {
    	return new Image(ClassLoader.getSystemResource(url).toString());
	}

    public static void switchToContainerSelectScene() {
    	if (playerMoveTimer != null) {
    	    playerMoveTimer.stop();
    	}
        StackPane root = new StackPane();
        containerScene.render(containerScene.gc);
        root.getChildren().add(containerScene);
        Scene scene = new Scene(root, canvaWidth, canvaHeight);
        setPresentScene(scene);
        getPrimaryStage().setScene(scene);
    }
    //Listening to keyboard input
    public static void updatePlayerMove(){
       //Keyboard Updater; using for updating position
    	//SoundManager.playGameBgm();
    	playerMoveTimer  = new AnimationTimer() {
    	    @Override
    	    
    	    public void handle(long now) {
    	        if (GameManager.isGameEnded()) {
    	            this.stop(); // หยุด timer ถ้าเกมจบ
    	            return;
    	        }
    	        GameScreen.getInstance().clear(); // clear scene
    	        //1. render background map ก่อน
                GameScreen.getInstance().render(GameScreen.getInstance().getGraphicsContext2D());
    	        if (keyboardChecker.isUpPressed()) {
    	            Player.getInstance().move(Direction.UP);
    	            Player.getInstance().updateAnimation(Direction.UP);
    	        } else if (keyboardChecker.isDownPressed()) {
    	            Player.getInstance().move(Direction.DOWN);
    	            Player.getInstance().updateAnimation(Direction.DOWN);
    	        } else if (keyboardChecker.isRightPressed()) {
    	            Player.getInstance().move(Direction.RIGHT);
    	            Player.getInstance().updateAnimation(Direction.RIGHT);
    	        } else if (keyboardChecker.isLeftPressed()) {
    	            Player.getInstance().move(Direction.LEFT);
    	            Player.getInstance().updateAnimation(Direction.LEFT);
    	        } else if (keyboardChecker.isEscPressed()) {
                    switchToPauseScreen();
                    SoundManager.playChillerOpen();
                }
    	        if (GameScreen.getInstance().getHitObject() != null && GameScreen.getInstance().getHitObject() instanceof Interactable && keyboardChecker.isInteraction()) {
    	        	GameScreen.getInstance().getHitObject().interact();
    	        	SoundManager.playChillerOpen();
    	        } else {
    	            //System.out.println("ไม่มี object ที่ interact ได้");
    	        }
    	        // clearRect หรือ background draw ควรอยู่ที่นี่ด้วยถ้าใช้
    	        Player.getInstance().render(GameScreen.getInstance().gc);
    	        //Draw Timer and Score board
    	        drawOverlay(GameScreen.getInstance().gc);
    	    }
    	};
    	playerMoveTimer.start();

    	
    }
    public static void startGameTimer(GraphicsContext gc) {
        if (gameTimerStarted && gameTimerThread != null && gameTimerThread.isAlive()) return; // already running

        gameTimerStarted = true;

        gameTimerThread = new Thread(() -> {
            while (GameManager.getTimeLeft() > 0 && !Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(1000);
                    GameManager.decrementTime();
                    Platform.runLater(() -> drawOverlay(gc));
                } catch (InterruptedException e) {
                    System.out.println("Timer thread interrupted");
                    return; // Stop the thread
                }
            }

            if (!Thread.currentThread().isInterrupted()) {
                Platform.runLater(() -> {
                    drawOverlay(gc); // final update
                    GameManager.setIsGameEnded(true);
                    SoundManager.stopGameBGM();
                    SceneManager.switchToResultScreen();
                });
            }
        });

        gameTimerThread.setDaemon(true);
        gameTimerThread.start();
    }
    
    //For Text and Score board
    public static void drawOverlay(GraphicsContext gc) {
    	if (!(getPresentScene().getRoot().getChildrenUnmodifiable().contains(GameScreen.getInstance()))) {
            return; 
        }

        gc.setFont(javafx.scene.text.Font.font("Arial", 16));
        gc.setFill(javafx.scene.paint.Color.BLACK);
        
        String timerText = String.format("Time: %02d:%02d", GameManager.getTimeLeft() / 60, GameManager.getTimeLeft() % 60);
        String scoreText = "Score: " + GameManager.getScore();
        String holdingItem;
        
        if(Player.getInstance().getHeldItem() == null) {
        	holdingItem = "Hand: Empty";
        }
        else {
        	holdingItem = "Hand: " + Player.getInstance().getHeldItem().getName();
        }

        gc.fillText(timerText, 10, 20);
        gc.fillText(scoreText, 10, 40);
        gc.fillText(holdingItem, 10, 60);
        
        Task currentTask = TaskManager.getCurrentTask();
        if (currentTask != null) {
        	gc.setFill(javafx.scene.paint.Color.WHITE);
            String menuText = "Order:\n" + currentTask.getMenuName(); // ✅ ใช้ชื่อเมนูจาก Task
            String[] lines = menuText.split("\n");

            double x = SceneManager.canvaWidth - 100;
            double y = 20;

            for (String line : lines) {
                gc.fillText(line, x, y);
                y += 20;
            }
        }

        }
    
    
    private static void stopGameTimer() {
        if (gameTimerThread != null && gameTimerThread.isAlive()) {
            gameTimerThread.interrupt();
            gameTimerThread = null;
            gameTimerStarted = false;
        }
    }

    
  //Getter and Setter
	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void setPrimaryStage(Stage primaryStage) {
		SceneManager.primaryStage = primaryStage;
	}
	public static Scene getPresentScene() {
		return presentScene;
	}
	private static void setPresentScene(Scene presentScene) {
		SceneManager.presentScene = presentScene;
	}
	
	public static void resetGameTimerFlag() {
	    gameTimerStarted = false;
	}
	
	
    
}
