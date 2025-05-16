package ingredients;

import gui.GameScreen;
import interfaces.Choppable;

public class Tuna extends Ingredient implements Choppable{
	// ชื่อในแต่ละสถานะ
    private static final String NAME_RAW = "Tuna (Unchopped)";
    private static final String NAME_CHOPPED = "Tuna";

    // สถานะการหั่น
    private boolean chopped = false;

    public Tuna() {
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
