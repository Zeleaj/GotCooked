package ingredients;

public class Miso extends Ingredient{
    private static final String NAME = "Miso";

	public Miso() {
		super(NAME);
	}

	@Override
	public boolean isPrepared() {
		return true;
	}

}
