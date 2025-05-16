package menu;

import gui.GameScreen;
import interfaces.CookMethod;
import interfaces.Cookable;

public class Omurice extends Menu implements Cookable{

	 private static final String NAME_UNCOOKED = "Omurice (Uncooked)";
	    private static final String NAME_COOKED = "Omurice";

	    private boolean cooked = false;

	    public Omurice() {
	        super(NAME_UNCOOKED, MenuRecipe.ALL_RECIPES.get("Omurice").getIngredients());
	    }

	    @Override
	    public void cook(CookMethod method) {
	        if (method != CookMethod.FRY) {
	            GameScreen.getInstance().setFeedback(getName() + " can only be cooked by FRY.");
	            return;
	        }

	        if (!isCooked()) {
	            cooked = true;
	            setName(NAME_COOKED);
	            GameScreen.getInstance().setFeedback(getName() + " has been fried!");
	        } else {
	            GameScreen.getInstance().setFeedback(getName() + " is already cooked.");
	        }
	    }

	    @Override
	    public boolean isCooked() {
	        return cooked;
	    }

	    @Override
	    public boolean isUsableAsIngredient() {
	        return false;
	    }

		@Override
		public void forceCook() {
			setCooked(true);
            setName(NAME_COOKED);

		}
		public void setCooked(boolean cooked) {
	        this.cooked = cooked;
	    }
		
}
