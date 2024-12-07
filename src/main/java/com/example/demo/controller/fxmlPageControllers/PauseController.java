package com.example.demo.controller.fxmlPageControllers;

import com.example.demo.activeActors.planes.UserPlane;
import com.example.demo.controller.FxmlController;
import com.example.demo.levels.LevelParent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Button;


/**
 * Controller class for the pause screen.
 */
public class PauseController extends FxmlController {

    @FXML
    private Button resumeButton;

    @FXML
    private Button menuButton;


    /**
     * Resumes the game by returning to the current level.
     */
    @FXML
    private void resumeGame() {
        mainController.resumeLevel(levelToReturn);
    }


    /**
     * Navigates to the main menu.
     */
    @FXML
    private void goToMainMenu() {
        try {
            LevelParent myLevel = mainController.getLevelInstance(levelToReturn);
            
            UserPlane.getInstance().destroyInstance();
            myLevel.removeEventListener(mainController);
            myLevel.destroyInstance();
            
            LevelParent.resetScore();
            mainController.goToFXML(null, null, "menu");

        } catch (Exception e) {
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
        resumeButton.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
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