package menu;

import java.util.HashMap;

import ingredients.Ingredient;

public class MenuFactory {
	private static final HashMap<String, Class<? extends Menu>> MENU_MAP = new HashMap<>();

    static {
        MENU_MAP.put("Soup", Soup.class);
        MENU_MAP.put("SalmonSushi",SalmonSushi.class);
        MENU_MAP.put("TunaSushi", TunaSushi.class);
        MENU_MAP.put("Ramen", Ramen.class);
        MENU_MAP.put("Omurice", Omurice.class);
        MENU_MAP.put("Pancake", Pancake.class);
        MENU_MAP.put("Bread", Bread.class);
        MENU_MAP.put("Rice", Rice.class);
        MENU_MAP.put("FriedPork", FriedPork.class);
    }

    public static Menu createMenu(String menuName) {
        Class<? extends Menu> menuClass = MENU_MAP.get(menuName);
        if (menuClass != null) {
            try {
                return menuClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null; // ไม่เจอ class → return null
    }
    public static Ingredient createIngredientFromMenu(Menu menu) {
    	 Ingredient ing = new Ingredient(menu.getName()) {
    	        @Override
    	        public boolean isPrepared() {
    	            return true;
    	        }
    	    };

    	    // ✅ Test ทันที
    	    System.out.println("[TEST] createIngredientFromMenu: menu = " + menu.getName() + ", result.getName() = '" + ing.getName() + "'");

    	    return ing;

}}

