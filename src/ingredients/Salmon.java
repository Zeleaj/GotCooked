package ingredients;

import gui.GameScreen;
import interfaces.Choppable;

public class Salmon extends Ingredient implements Choppable{
	// ชื่อในแต่ละสถานะ
    private static final String NAME_RAW = "Salmon (Unchopped)";
    private static final String NAME_CHOPPED = "Salmon";

    // state ว่าหั่นแล้วหรือยัง
    private boolean chopped = false;

    public Salmon() {
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
        return isChopped(); // พร้อมเมื่อหั่นแล้ว
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
