package menu;


import gui.GameScreen;
import interfaces.CookMethod;
import interfaces.Cookable;

public class Bread extends Menu implements Cookable{
	private static final String NAME_UNCOOKED = "Bread (Uncooked)";
    private static final String NAME_COOKED = "Bread";
    private boolean cooked = false;

    public Bread() {
        super(NAME_UNCOOKED,MenuRecipe.ALL_RECIPES.get("Bread").getIngredients());
    }

    @Override
    public void cook(CookMethod method) {
        if (method != CookMethod.FRY) { // เปลี่ยนเป็น BAKE ได้ถ้ามี
            GameScreen.getInstance().setFeedback(getName() + " can only be cooked by FRY.");
            return;
        }

        if (!isCooked()) {
            cooked = true;
            setName(NAME_COOKED);
            GameScreen.getInstance().setFeedback(getName() + " has been cooked!");
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
        return false; // ไม่ใช้ Bread เป็นวัตถุดิบในเมนูอื่น
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
