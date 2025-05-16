package ingredients;

import gui.GameScreen;
import interfaces.Choppable;

public class Pork extends Ingredient implements Choppable{
	private static final String NAME_RAW = "Pork (Unchopped)";
    private static final String NAME_CHOPPED = "Pork";

    private boolean chopped = false;

    public Pork() {
        super(NAME_RAW);
    }

    @Override
    public void chop() {
        if (!isChopped()) {
            setChopped(true);
            setName(NAME_CHOPPED);
            GameScreen.getInstance().setFeedback(getName() + " has been chopped.");
        } else {
            GameScreen.getInstance().setFeedback(getName() + " has already been chopped.");
        }
    }

    @Override
    public boolean isPrepared() {
        return isChopped();
    }

    @Override
    public boolean isChopped() {
        return chopped;
    }

    public void setChopped(boolean chopped) {
        this.chopped = chopped;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    public void setName(String name) {
        super.setName(name);
    }

}
