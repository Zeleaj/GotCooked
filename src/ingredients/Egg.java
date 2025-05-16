package ingredients;

public class Egg extends Ingredient {
	 private static final String NAME = "Egg";
	public Egg() {
		super(NAME);
	}

	@Override
	public boolean isPrepared() {
		return true;
	}

}
