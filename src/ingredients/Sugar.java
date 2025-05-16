package ingredients;

public class Sugar extends Ingredient{
	 private static final String NAME = "Sugar";
	public Sugar() {
		super(NAME);
	}

	@Override
	public boolean isPrepared() {
		return true;
	}

}
