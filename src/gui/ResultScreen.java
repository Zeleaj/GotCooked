package gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import logic.GameManager;

public class ResultScreen extends BaseSceneScreen {

    private VBox layout;
    private Scene resultScene;
    private Label scoreLabel;

    public ResultScreen() {

        layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPrefSize(SceneManager.canvaWidth, SceneManager.canvaHeight);

        Label titleLabel = new Label("Time's Up!");
        titleLabel.setFont(Font.font("Arial", 28));

        scoreLabel = new Label();
        scoreLabel.setFont(Font.font("Arial", 20));

        Button returnToMenuButton = new Button("Return to Main Menu");
        returnToMenuButton.setOnAction(e -> {
            SceneManager.switchToMenuScreen();
            GameManager.resetGame();
        });

        Button exitButton = new Button("Exit Game");
        exitButton.setOnAction(e -> {
            Stage stage = SceneManager.getPrimaryStage();
            stage.close();
        });

        layout.getChildren().addAll(titleLabel, scoreLabel, returnToMenuButton, exitButton);

        // 👉 วาง background + layout ใน StackPane
        StackPane root = new StackPane();

        // สร้าง Canvas สำหรับวาด background
        Canvas bgCanvas = new Canvas(SceneManager.canvaWidth, SceneManager.canvaHeight);
        GraphicsContext gc = bgCanvas.getGraphicsContext2D();
        gc.drawImage(super.defaultBackgroundImage, 0, 0, SceneManager.canvaWidth, SceneManager.canvaHeight);

        root.getChildren().addAll(bgCanvas, layout);
        resultScene = new Scene(root, SceneManager.canvaWidth, SceneManager.canvaHeight);
    }

    @Override
    public void onEnter() {
        scoreLabel.setText("Final Score: " + GameManager.getScore());
        SceneManager.getPrimaryStage().setScene(resultScene);
    }

    @Override
    public void render(GraphicsContext gc) {
        // ไม่ต้องใช้ใน UI-based Scene แบบนี้
    }

    @Override
    public void reset() {}
}
