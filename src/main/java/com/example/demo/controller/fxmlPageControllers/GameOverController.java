package com.example.demo.controller.fxmlPageControllers;

import java.io.IOException;
import com.example.demo.controller.FxmlController;
import com.example.demo.levels.LevelParent;
import com.example.demo.levels.waveLevels.LevelOne;

import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Button;

/**
 * Controller class for the game over screen.
 */
public class GameOverController extends FxmlController {

    @FXML
    private Button restartButton;

    @FXML
    private Button menuButton;


    
    /**
     * Restarts the current level.
     *
     * @throws IOException if an I/O error occurs
     */
    @FXML
    private void restartLevel() throws IOException {
        if (levelToReturn == LevelOne.class.getName()) {
            LevelParent.resetScore();
        }
        mainController.changeLevel(null, levelToReturn);
    }


    /**
     * Navigates to the main menu.
     */
    @FXML
    private void goToMainMenu() {
        try {
            LevelParent.resetScore();
            mainController.goToFXML(null, null, "menu");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded.
     */
    @FXML
    private void initialize() {
        // Add an event filter to block the spacebar from triggering the button action
        restartButton.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.SPACE) {
                event.consume();
            }
        });

        // Add an event filter to block the spacebar from triggering the button action
        menuButton.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.SPACE) {
                event.consume();
            }
        });
    }


}