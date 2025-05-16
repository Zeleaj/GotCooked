package menu;

import java.util.ArrayList;
import java.util.HashMap;

import container.Container;
import ingredients.Ingredient;


public class MergeMenu {
    public static String findMatchingMenu(ArrayList<Ingredient> inputIngredients, Class<? extends Container> inputContainer) {
        // 1. สร้าง inputCount → นับจำนวนแต่ละ class ของ ingredient
        HashMap<String, Integer> inputCount = new HashMap<>();
        for (Ingredient ing : inputIngredients) {
            String typeName = ing.getName();
            System.out.println("[DEBUG] Ingredient class: " + ing.getName() + 
                    " simpleName: " + typeName + 
                    " getName(): " + ing.getName());
            inputCount.put(typeName, inputCount.getOrDefault(typeName, 0) + 1);
        }
     // ✅ เพิ่ม debug ตรงนี้!
        System.out.println("[DEBUG] InputCount keys: " + inputCount.keySet());
        System.out.println("[DEBUG] InputCount values: " + inputCount);


        // 2. loop ทุก recipe
        for (HashMap.Entry<String, Recipe> recipeEntry : MenuRecipe.ALL_RECIPES.entrySet()) {
            String menuName = recipeEntry.getKey();
            Recipe recipeObj = recipeEntry.getValue();
            HashMap<String, Integer> recipe = recipeObj.getIngredients();
            System.out.println("[DEBUG] Checking recipe: " + menuName + " needs: " + recipe.keySet());
            // เพิ่มเช็ค container ตรงนี้!
            if (!recipeObj.getContainerUsed().equals(inputContainer)) {
                continue; // ถ้า container ไม่ตรง → ข้าม
            }

            boolean match = true;

            // 3. เช็คแต่ละ ingredient ใน recipe
            for (HashMap.Entry<String, Integer> ingredientEntry : recipe.entrySet()) {
                String requiredType = ingredientEntry.getKey();
                Integer requiredAmount = ingredientEntry.getValue();

                Integer inputAmount = inputCount.get(requiredType);

                if (inputAmount == null || !inputAmount.equals(requiredAmount)) {
                    match = false;
                    break; // ถ้าเจอไม่ตรง → ไม่ต้องเช็คต่อ
                }
            }

            // 4. ถ้า match → return menu name
            if (match && inputCount.size() == recipe.size()) {
                return menuName;
            }
        }

        // 5. ไม่เจอ match → return null
        return null;
    }
}
