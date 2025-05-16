package menu;

public class TunaSushi extends Menu{
	 private static final String NAME = "TunaSushi";
	 public TunaSushi() {
	        super(NAME, MenuRecipe.ALL_RECIPES.get(NAME).getIngredients());
	    }

	@Override
	public boolean isUsableAsIngredient() {
		return false;
	}

}
