package menu;

public class Ramen extends Menu{

	 private static final String NAME = "Ramen";

	    public Ramen() {
	        super(NAME, MenuRecipe.ALL_RECIPES.get(NAME).getIngredients());
	    }

	    @Override
	    public boolean isUsableAsIngredient() {
	        return false;
	    }

}
