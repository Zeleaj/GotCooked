package gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Duration;


public class CookingWaitScene extends BaseSceneScreen {

    private double progress = 0.0;
    private Timeline progressTimeline;
    private static String cookingResult = null;

    public CookingWaitScene() {
        super();
    }

    @Override
    public void onEnter() {
        setActive(true);
        progress = 0.0;

        progressTimeline = new Timeline(
            new KeyFrame(Duration.seconds(0.1), e -> {
                progress += 0.02;
                render(gc);
                if (progress >= 1.0) {
                    progressTimeline.stop();
                    
                    Platform.runLater(() -> {
                        SceneManager.switchToGameScreen();  // Go back to game
                    });
                }
            })
        );
        progressTimeline.setCycleCount(Timeline.INDEFINITE);
        progressTimeline.play();
    }

    @Override
    public void render(GraphicsContext gc) {
        clear();
        
        // วาดพื้นหลัง
        super.drawDefaultBackground();

        gc.setFill(Color.BLACK);
        gc.fillText("Cooking...", 200, 100);

        gc.setFill(Color.GRAY);
        gc.fillRect(150, 120, 200, 20);

        gc.setFill(Color.GREEN);
        gc.fillRect(150, 120, 200 * progress, 20);
    }


    @Override
    public void reset() {
        if (progressTimeline != null) progressTimeline.stop();
        progress = 0.0;
        clear();
    }
    public static void clearCookingResult() {
        cookingResult = null;
    }
    
    public static void setCookingResult(String item) {
        cookingResult = item;
    }

    public static String getCookingResult() {
        return cookingResult;
    }
}