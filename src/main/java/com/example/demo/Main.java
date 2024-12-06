package com.example.demo;

import java.lang.reflect.InvocationTargetException;
import com.example.demo.controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The main entry point for the Sky Battle application.
 */
public class Main extends Application {

    private static final int SCREEN_WIDTH = 1300;
    private static final int SCREEN_HEIGHT = 750;
    private static final String TITLE = "Sky Battle";
    private Controller myController;

    /**
     * The main entry point for all JavaFX applications.
     * This method is called after the Application class is instantiated and 
     * the JavaFX runtime is ready.
     *
     * @param stage the primary stage for this application, onto which 
     * the application scene can be set.
     * @throws ClassNotFoundException if the specified class cannot be found
     * @throws NoSuchMethodException if a particular method cannot be found
     * @throws SecurityException if a security violation occurs
     * @throws InstantiationException if the instantiation fails
     * @throws IllegalAccessException if the currently executing method does not have access to the definition of the specified class, method, field, or constructor
     * @throws IllegalArgumentException if an illegal argument is passed
     * @throws InvocationTargetException if the underlying method throws an exception
     */
    @Override
    public void start(Stage stage) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
            InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        stage.setTitle(TITLE);
        stage.setResizable(false);
        stage.setHeight(SCREEN_HEIGHT);
        stage.setWidth(SCREEN_WIDTH);
        myController = Controller.getInstance(stage);
        
        try {
            myController.launchGame();
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException |
                InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * The main method is the entry point for the application.
     * It launches the JavaFX application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch();
    }
}