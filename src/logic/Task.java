package logic;

import container.Container;
import interfaces.Cookable;
import interfaces.Heldable;
import menu.Menu;

public class Task {
	private Menu expectedMenu;
    private Class<? extends Container> requiredContainer;
    private int score; // ✅ เพิ่มคะแนนแต่ละ Task


    public Task(Menu expectedMenu, Class<? extends Container> requiredContainer, int score) {
        this.expectedMenu = expectedMenu;
        this.requiredContainer = requiredContainer;
        this.score = score;
    }

    public boolean isCompletedBy(Heldable held) {
        if (!(held instanceof Container container)) return false;
        Menu actualMenu = container.getMenu();
        
        if (actualMenu == null) return false;

        boolean nameMatches = actualMenu.getName().equals(expectedMenu.getName());
        boolean cookedIfRequired = !(actualMenu instanceof Cookable)
                || ((Cookable) actualMenu).isCooked();
        boolean correctContainer = requiredContainer.isInstance(container);

        return nameMatches && cookedIfRequired && correctContainer;
    }

    public String getMenuName() {
        return expectedMenu.getName();
    }

    public int getScore() {
        return score;
    }
    public String getContainerName() {
        return requiredContainer.getSimpleName();
    }
}
