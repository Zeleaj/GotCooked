package menu;

import gui.GameScreen;
import interfaces.CookMethod;
import interfaces.Cookable;

public class FriedPork extends Menu implements Cookable{
	 private static final String NAME_UNCOOKED = "FriedPork (Uncooked)";
	    private static final String NAME_COOKED = "FriedPork";

	    private boolean cooked = false;

	    public FriedPork() {
	        super(NAME_UNCOOKED, MenuRecipe.ALL_RECIPES.get("FriedPork").getIngredients());
	    }

	    @Override
	    public void cook(CookMethod method) {
	        if (method != CookMethod.FRY) {
	        	GameScreen.getInstance().setFeedback(getName() + " can only be cooked by FRY.");
	            return;
	        }
	        if (!isCooked()) {
	            setCooked(true);
	            setName(NAME_COOKED);
	            GameScreen.getInstance().setFeedback(getName() + " has been fried!");
	        } else {
	        	GameScreen.getInstance().setFeedback(getName() + " has already been fried.");
	        }
	    }

	    @Override
	    public boolean isCooked() {
	        return cooked;
	    }

	    @Override
	    public boolean isUsableAsIngredient() {
	        return true;
	    }

	    public void setCooked(boolean cooked) {
	        this.cooked = cooked;
	    }

		@Override
		public void forceCook() {
			setCooked(true);
            setName(NAME_COOKED);

		}
		
}
