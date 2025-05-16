package gui;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import menu.MenuRecipe;
import menu.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeCollectionScreen extends BaseSceneScreen {

    private Scene recipeScene;
    private Canvas canvas;
    private GraphicsContext gc;

    private final double lineHeight = 30;
    private final double margin = 40;

    private List<String> recipeLines;
    private double scrollOffsetY = 0;

    public RecipeCollectionScreen() {
        // ✅ เตรียมข้อความไว้ล่วงหน้า
        recipeLines = new ArrayList<>();
        for (String menuName : MenuRecipe.ALL_RECIPES.keySet()) {
            Recipe recipe = MenuRecipe.ALL_RECIPES.get(menuName);
            StringBuilder sb = new StringBuilder(menuName).append(" → ");
            recipe.getIngredients().forEach((ingredient, amount) ->
                    sb.append(amount).append("x ").append(ingredient).append(", ")
            );
            if (sb.length() > 2) sb.setLength(sb.length() - 2);
            sb.append(" [").append(recipe.getContainerUsed().getSimpleName()).append("]");
            recipeLines.add(sb.toString());
        }

        // ✅ สร้าง Canvas และ Scene
        canvas = new Canvas(SceneManager.canvaWidth, SceneManager.canvaHeight);
        gc = canvas.getGraphicsContext2D();

        Pane root = new Pane(canvas);
        recipeScene = new Scene(root, SceneManager.canvaWidth, SceneManager.canvaHeight);

        // ✅ Scroll ด้วย mouse
        recipeScene.setOnScroll((ScrollEvent e) -> {
            scrollOffsetY -= e.getDeltaY();
            clampScroll();
            render();
        });

        render();
        // ✅ เพิ่มปุ่ม Back
        Button backButton = new Button("Back to Options");
        backButton.setLayoutX(SceneManager.canvaWidth / 2.0 - 60); // ปรับตำแหน่งให้อยู่กลาง
        backButton.setLayoutY(SceneManager.canvaHeight - 50);      // อยู่ด้านล่าง
        backButton.setOnAction(e -> SceneManager.switchToOptionMenuScreen());

        root.getChildren().add(backButton); // ✅ ใส่เข้า root
    }

    private void clampScroll() {
        double maxOffset = Math.max(0, recipeLines.size() * lineHeight + margin * 2 - SceneManager.canvaHeight);
        scrollOffsetY = Math.max(0, Math.min(scrollOffsetY, maxOffset));
    }

    private void render() {
        // ✅ ล้างหน้าจอ
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // ✅ วาดพื้นหลัง
        gc.drawImage(super.defaultBackgroundImage, 0, 0, canvas.getWidth(), canvas.getHeight());

        // ✅ วาดข้อความสูตรอาหารแบบ scroll เอง
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", 18));
        gc.setTextAlign(TextAlignment.CENTER);

        double y = margin - scrollOffsetY;
        for (String line : recipeLines) {
            gc.fillText(line, canvas.getWidth() / 2, y);
            y += lineHeight;
        }
    }

    @Override
    public void onEnter() {
        SceneManager.getPrimaryStage().setScene(recipeScene);
    }

    @Override
    public void render(GraphicsContext gc) {
        render(); // เรียก render ภายในคลาสเอง
    }

    @Override
    public void reset() {
        scrollOffsetY = 0;
        render();
    }
}
