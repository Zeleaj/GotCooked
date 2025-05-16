package ingredients;

public class Flour extends Ingredient{
	 private static final String NAME = "Flour";
	public Flour() {
		super(NAME);
	}

	@Override
	public boolean isPrepared() {
		return true;
	}

}
