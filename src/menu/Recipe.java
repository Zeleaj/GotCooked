package menu;

import java.util.HashMap;

import container.Container;

public class Recipe {
    private HashMap<String, Integer> ingredients;
    private Class<? extends Container> containerUsed;

    public Recipe(HashMap<String, Integer> ingredients, Class<? extends Container> containerUsed) {
        this.ingredients = ingredients;
        this.containerUsed = containerUsed;
    }

    public HashMap<String, Integer> getIngredients() {
        return ingredients;
    }

    public Class<? extends Container> getContainerUsed() {
        return containerUsed;
    }
}

