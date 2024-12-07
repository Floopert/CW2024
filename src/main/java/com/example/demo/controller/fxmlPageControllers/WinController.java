package com.example.demo.controller.fxmlPageControllers;

import java.io.IOException;

import com.example.demo.controller.FxmlController;
import com.example.demo.levels.LevelParent;
import com.example.demo.levels.waveLevels.LevelOne;

import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.control.*;


/**
 * Controller class for the win screen.
 */
public class WinController extends FxmlController {
    private static final String LEVEL_ONE_CLASS_NAME = LevelOne.class.getName();

    @FXML
    private Button replayButton;

    @FXML
    private Button menuButton;

    @FXML
    private Label scoreValue;


    /**
     * Replays the game by resetting the score and changing to the first level.
     *
     * @throws IOException if an I/O error occurs
     */
    @FXML
    private void replayGame() throws IOException {
        LevelParent.resetScore();
        mainController.changeLevel(null, LEVEL_ONE_CLASS_NAME);
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
        replayButton.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
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

        scoreValue.setText(Integer.toString(LevelParent.getCurrentScore()));
    }
}