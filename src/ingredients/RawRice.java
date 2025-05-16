package ingredients;


public class RawRice extends Ingredient {
	private static final String NAME = "RawRice";
	public RawRice() {
		super(NAME);
	}

	@Override
	public boolean isPrepared() {
		return false;
	}

}
