package logic;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import container.Bowl;
import container.Container;
import container.Dish;
import interfaces.Cookable;
import menu.Menu;
import menu.MenuFactory;

public class TaskManager {
	 private static Task currentTask;
	    private static boolean taskGenerated = false;

	    public static void generateTask() {
	        if (taskGenerated) return;

	        List<String> menuNames = Arrays.asList(
	            "Ramen", "Soup", "FriedPork", "SalmonSushi", "Omurice","TunaSushi","Rice","Pancake","Bread"
	        );

	        String selected = menuNames.get(new Random().nextInt(menuNames.size()));
	        Menu menu = MenuFactory.createMenu(selected);
	        if (menu instanceof Cookable cookable) cookable.forceCook();

	        Class<? extends Container> requiredContainer = getRequiredContainerFor(selected);
	        int score = getScoreForMenu(selected); 
	        currentTask = new Task(menu, requiredContainer,score);
	        taskGenerated = true;
	    }

	    public static Task getCurrentTask() {
	        return currentTask;
	    }

	    public static void finishCurrentTask() {
	        currentTask = null;
	        taskGenerated = false;
	    }

	    private static Class<? extends Container> getRequiredContainerFor(String menuName) {
	        return switch (menuName) {
	            case "Ramen", "Soup","Rice" -> Bowl.class;
	            default -> Dish.class;
	        };
	    }
	    private static int getScoreForMenu(String menuName) {
	        return switch (menuName) {
	            case "Ramen", "Omurice" -> 10;
	            case "TunaSushi", "SalmonSushi", "Bread" -> 5;
	            case  "Pancake" , "Soup"-> 8;
	            case "Rice" ,"FriedPork"-> 4;
	            default -> 5;
	        };}
}
