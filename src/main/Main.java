package main;

import gui.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;
import logic.GameManager;

public class Main extends Application{
    

    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage){
    	SceneManager scene = new SceneManager();
    	scene.initialize(primaryStage);
    	GameManager.setIsGameEnded(false);    	
    }

}