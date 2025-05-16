package ingredients;

public class Water extends Ingredient {
    private static final String NAME = "Water";

    public Water() {
        super(NAME);
    }

    @Override
    public boolean isPrepared() {
        return true;
    }
}

