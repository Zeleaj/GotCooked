package container;

import java.util.stream.Collectors;

import gui.GameScreen;
import ingredients.Ingredient;
import menu.MenuFactory;
import menu.MergeMenu;

public class Pot extends Container{
	@Override
    public boolean canProcess() {
        // เช็คว่า ingredients สามารถ merge เป็น menu ได้มั้ย
        return MergeMenu.findMatchingMenu(ingredients,Pot.class) != null;
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
        String menuName = MergeMenu.findMatchingMenu(ingredients,Pot.class);
        if (menuName != null) {
            this.menu = MenuFactory.createMenu(menuName) ; // หรือ dynamic ตาม menuName
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
            name = "Pot (" + menu.getName() + ")";
        } else if (!ingredients.isEmpty()) {
            name = "Pot (with ingredients)";
        } else {
            name = "Pot (empty)";
        }
    }
    @Override
    public String getName() {
        if (menu != null) {
            return getClass().getSimpleName() + " (" + menu.getName() + ")";
        } else if (!ingredients.isEmpty()) {
            // รวมชื่อวัตถุดิบข้างใน
            String ingredientNames = ingredients.stream()
                .map(Ingredient::getName)
                .collect(Collectors.joining(", "));
            return getClass().getSimpleName() + " [" + ingredientNames + "]";
        } else {
            return getClass().getSimpleName() + " (empty)";
        }
    }

    

}
