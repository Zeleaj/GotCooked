package menu;


import gui.GameScreen;
import interfaces.CookMethod;
import interfaces.Cookable;

public class Pancake extends Menu implements Cookable{


    private static final String NAME_UNCOOKED = "Pancake (Uncooked)";
    private static final String NAME_COOKED = "Pancake";

    private boolean cooked = false;

    public Pancake() {
        super(NAME_UNCOOKED, MenuRecipe.ALL_RECIPES.get("Pancake").getIngredients());
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
    
    public void setCooked(boolean cooked) {
        this.cooked = cooked;
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
	
}
