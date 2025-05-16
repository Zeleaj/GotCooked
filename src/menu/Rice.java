package menu;

import gui.GameScreen;
import interfaces.CookMethod;
import interfaces.Cookable;

public class Rice extends Menu implements Cookable{
	 // Constants
    private static final String NAME_UNCOOKED = "Rice (Uncooked)";
    private static final String NAME_COOKED = "Rice";

    // State
    private boolean cooked = false;

    public Rice() {
        super(NAME_UNCOOKED, MenuRecipe.ALL_RECIPES.get("Rice").getIngredients());
    }

    @Override
    public void cook(CookMethod method) {
        if (method != CookMethod.BOIL) {
        	 GameScreen.getInstance().setFeedback(getName() + " can only be cooked by BOIL.");
            return;
        }
        if (!isCooked()) {
            setCooked(true);
            setName(NAME_COOKED);
            GameScreen.getInstance().setFeedback(getName() + " has been boiled!");
        } else {
        	 GameScreen.getInstance().setFeedback(getName() + " has already been boiled.");
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
