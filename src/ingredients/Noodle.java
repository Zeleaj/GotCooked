package ingredients;

public class Noodle extends Ingredient{
	private static final String NAME = "Noodle";

	public Noodle() {
		super(NAME);
	}

	@Override
	public boolean isPrepared() {
		return true;
	}
}
