package menu;

public class SalmonSushi extends Menu{

	private static final String NAME = "SalmonSushi";

    public SalmonSushi() {
        super(NAME, MenuRecipe.ALL_RECIPES.get(NAME).getIngredients());
    }

    @Override
    public boolean isUsableAsIngredient() {
        return false;
    }
}


