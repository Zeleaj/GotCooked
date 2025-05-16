package gui;

import component.Player;
import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import logic.GameManager;

public class PauseScreen extends BaseSceneScreen {
    private VBox buttonBox;

    public PauseScreen() {
        super();

        // Create VBox with buttons
        buttonBox = new VBox(15);
        buttonBox.setAlignment(Pos.CENTER);

        Button resumeButton = new Button("Resume Game");
        Button mainMenuButton = new Button("Return to Main Menu");
        Button newGameButton = new Button("New Game");

        resumeButton.setOnAction(e -> SceneManager.switchToGameScreen());
        mainMenuButton.setOnAction(e -> {
        	SceneManager.switchToMenuScreen();
        	GameManager.resetGame();
            Player.getInstance().reset();
        });
        newGameButton.setOnAction(e -> {
            GameManager.resetGame();
            Player.getInstance().reset();
            GameScreen.getInstance().reset();
            SceneManager.switchToGameScreen();
        });

        buttonBox.getChildren().addAll(resumeButton, mainMenuButton, newGameButton);
        buttonBox.setTranslateY(30); // Move buttons downward a bit
    }

    @Override
    public void onEnter() {
        setActive(true);
        render(getGraphicsContext2D());

        StackPane root = (StackPane) this.getParent();
        if (!root.getChildren().contains(buttonBox)) {
            root.getChildren().add(buttonBox);
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        clear();
        gc.drawImage(super.defaultBackgroundImage, 0, 0, SceneManager.canvaWidth, SceneManager.canvaHeight);

        gc.setFill(Color.rgb(0, 0, 0, 0.7));
        gc.fillRect(0, 0, SceneManager.canvaWidth, SceneManager.canvaHeight);

        gc.setFill(Color.WHITE);
        gc.setFont(new Font(28));
        gc.fillText("Game Paused", 160, 80);
    }


    @Override
    public void reset() {
        setActive(false);
        StackPane root = (StackPane) this.getParent();
        if (root != null) {
            root.getChildren().remove(buttonBox);
        }
    }
}
