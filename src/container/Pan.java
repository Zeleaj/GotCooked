package container;

import java.util.stream.Collectors;

import gui.GameScreen;
import ingredients.Ingredient;
import menu.MenuFactory;
import menu.MergeMenu;

public class Pan extends Container {
	@Override
    public boolean canProcess() {
        return MergeMenu.findMatchingMenu(ingredients,Pan.class) != null;
    }
    @Override
    public void addIngredient(Ingredient ingredient) {
        // ถ้ามีเมนูเดิมใน Pan → แปลงเป็น ingredient
        if (this.menu != null) {
            if (!this.menu.isUsableAsIngredient()) {
                GameScreen.getInstance().setFeedback("❌ เมนู " + menu.getName() + " ไม่สามารถใช้เป็นวัตถุดิบได้");
                return;
            }

            Ingredient oldMenuAsIngredient = MenuFactory.createIngredientFromMenu(this.menu);
            if (oldMenuAsIngredient == null) {
                GameScreen.getInstance().setFeedback("❌ แปลงเมนูเดิม (" + menu.getName() + ") ไม่สำเร็จ");
                return;
            }

            ingredients.add(oldMenuAsIngredient);
            this.menu = null;
            GameScreen.getInstance().setFeedback("ℹ️ เมนูเดิมถูกแปลงเป็นวัตถุดิบเพื่อรวมกับของใหม่");
        }
        ingredients.add(ingredient);
        updateName();

        if (canProcess()) {
            process();
        }
    }

    @Override
    public void process() {
        String menuName = MergeMenu.findMatchingMenu(ingredients,Pan.class);

        if (menuName != null) {
            this.menu = MenuFactory.createMenu(menuName);  // ใช้ factory dynamic
            ingredients.clear();
            updateName();
            GameScreen.getInstance().setFeedback("Merge: " + menuName);
        } else {
            updateName();
            GameScreen.getInstance().setFeedback("Cannot merge this combination.");
        }
    }

    @Override
    protected void updateName() {
        if (menu != null) {
            name = "Pan (" + menu.getName() + ")";
        } else if (!ingredients.isEmpty()) {
            name = "Pan (with ingredients)";
        } else {
            name = "Pan (empty)";
        }
    }
    @Override
    public String getName() {
        if (menu != null) {
            return "Pan (" + menu.getName() + ")";
        } else if (!ingredients.isEmpty()) {
            String ingredientNames = ingredients.stream()
                .map(Ingredient::getName)
                .collect(Collectors.joining(", "));
            return "Pan [" + ingredientNames + "]";
        } else {
            return "Pan (empty)";
        }}
	
}

