package ingredients;

import gui.GameScreen;
import interfaces.Choppable;
import interfaces.Washable;

public class Seaweed extends Ingredient implements Choppable, Washable {
    // Constants → string literal
    private static final String NAME_RAW = "Seaweed (Unchopped,Unwashed)";
    private static final String NAME_WASHED = "Seaweed (Unchopped)";
    private static final String NAME_CHOPPED = "Seaweed";

    // State flags
    private boolean chopped = false;
    private boolean washed = false;

    public Seaweed() {
        super(NAME_RAW);
    }

    @Override
    public boolean isPrepared() {
        return isChopped();
    }

    @Override
    public void chop() {
        if (!isWashed()) {
        	 GameScreen.getInstance().setFeedback(getName() + " needs to be washed before chopping.");
            return;
        }

        if (!isChopped()) {
            setChopped(true);
            setName(NAME_CHOPPED);
            GameScreen.getInstance().setFeedback(getName() + " has been chopped.");
        } else {
        	 GameScreen.getInstance().setFeedback(getName() + " has already been chopped.");
        }
    }

    @Override
    public void wash() {
        if (!isWashed()) {
            setWashed(true);
            setName(NAME_WASHED);
            GameScreen.getInstance().setFeedback(getName() + " has been washed.");
        } else {
        	 GameScreen.getInstance().setFeedback(getName() + " has already been washed.");
        }
    }

    @Override
    public boolean isWashed() {
        return washed;
    }

    public void setWashed(boolean washed) {
        this.washed = washed;
    }

    // Getter/setter for name
    @Override
    public String getName() {
        return super.getName();
    }

    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public boolean isChopped() {
        return chopped;
    }

    public void setChopped(boolean chopped) {
        this.chopped = chopped;
    }
}

