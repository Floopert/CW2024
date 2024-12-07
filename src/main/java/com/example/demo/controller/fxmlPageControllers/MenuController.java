package com.example.demo.controller.fxmlPageControllers;

import java.io.IOException;
import com.example.demo.controller.FxmlController;
import com.example.demo.levels.waveLevels.LevelOne;

import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Button;


/**
 * Controller class for the main menu screen.
 */
public class MenuController extends FxmlController {

    private static final String LEVEL_ONE_CLASS_NAME = LevelOne.class.getName();

    @FXML
    private Button playButton;

    @FXML
    private Button exitButton;


    /**
     * Starts the game by changing to the first level.
     *
     * @throws IOException if an I/O error occurs
     */
    @FXML
    private void startGame() throws IOException {
        mainController.changeLevel(null, LEVEL_ONE_CLASS_NAME);
    }


    /**
     * Exits the game.
     */
    @FXML
    private void exitGame() {
        System.exit(0);
    }


    /**
     * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded.
     */
    @FXML
    private void initialize() {
        // Add an event filter to block the spacebar from triggering the button action
        playButton.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.SPACE) {
                event.consume();
            }
        });

        // Add an event filter to block the spacebar from triggering the button action
        exitButton.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.SPACE) {
                event.consume();
            }
        });
    }


}