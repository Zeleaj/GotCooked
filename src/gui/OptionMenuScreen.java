package gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import logic.SoundManager;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class OptionMenuScreen extends BaseSceneScreen {
    private Scene optionScene;

    public OptionMenuScreen() {
        StackPane root = new StackPane();

        Canvas canvas = new Canvas(SceneManager.canvaWidth, SceneManager.canvaHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(super.defaultBackgroundImage, 0, 0, SceneManager.canvaWidth, SceneManager.canvaHeight);

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPrefSize(SceneManager.canvaWidth, SceneManager.canvaHeight);
        double currentVolume = SoundManager.getGlobalVolume();
        Label volumeLabel = new Label("BGM Volume: " + (int)(currentVolume * 100) + "%");
        volumeLabel.setStyle("-fx-text-fill: white;");
        Slider volumeSlider = new Slider(0, 1, currentVolume);
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.setMajorTickUnit(0.25);
        volumeSlider.setMinorTickCount(4);
        volumeSlider.setBlockIncrement(0.1);
        volumeSlider.setSnapToTicks(true);

        // 🎧 อัปเดตเสียงแบบ real-time
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            double vol = newVal.doubleValue();
            SoundManager.setGlobalVolume(vol);
            volumeLabel.setText("BGM Volume: " + (int)(vol * 100) + "%");
        });

        Button recipeButton = new Button("View Recipe Collection");
        recipeButton.setOnAction(e -> SceneManager.switchToRecipeCollectionScreen());

        Button backButton = new Button("Back to Main Menu");
        backButton.setOnAction(e -> SceneManager.switchToMenuScreen());

        layout.getChildren().addAll(
            volumeLabel, volumeSlider,
            recipeButton, backButton
        );

        root.getChildren().addAll(canvas, layout);
        optionScene = new Scene(root);
    }

    @Override
    public void onEnter() {
        SceneManager.getPrimaryStage().setScene(optionScene);
    }

    @Override
    public void render(GraphicsContext gc) {}

    @Override
    public void reset() {}
}
