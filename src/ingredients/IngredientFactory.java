package ingredients;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import interfaces.Heldable;


public class IngredientFactory {
	private static final Map<String, Supplier<Heldable>> registry = new HashMap<>();

    static {
    	registry.put("Salmon",Salmon::new);
        registry.put("Tuna", Tuna::new);
        registry.put("Egg", Egg::new);
        registry.put("Pork", Pork::new);
        registry.put("FriedPork",() -> new Ingredient("FriedPork") {
            @Override
            public boolean isPrepared() {
                return true;  // หรือ false ถ้าต้องการให้ยังดิบ
            }});
        registry.put("RawRice", RawRice::new);
        registry.put("Rice", () -> new Ingredient("Rice") {
            @Override
            public boolean isPrepared() {
                return true;  // หรือ false ถ้าต้องการให้ยังดิบ
            }});
        registry.put("Miso", Miso::new);
        registry.put("Soup", () -> new Ingredient("Soup") {
            @Override
            public boolean isPrepared() {
                return true;  // หรือ false ถ้าต้องการให้ยังดิบ
            }});
        registry.put("Seaweed", Seaweed::new);
        registry.put("Wagame", Wagame::new);
        registry.put("Flour", Flour::new);
        registry.put("Sugar", Sugar::new);
        registry.put("Noodle", Noodle::new);
        registry.put("Water", Water::new);
    }

    public static Heldable createIngredient(String name) {
        Supplier<Heldable> supplier = registry.get(name);
        if (supplier != null) {
            return supplier.get();
        } else {
            throw new IllegalArgumentException("Unknown ingredient: " + name);
        }
    }
}
