package com.example.demo.controller.fxmlPageControllers;

import com.example.demo.controller.FxmlController;
import com.example.demo.levels.LevelParent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Button;

public class PauseController extends FxmlController {
    

    @FXML
    Button resumeButton;

    @FXML
    Button menuButton;

    @FXML
    private void resumeGame() {
        mainController.resumeLevel(levelToReturn);
    }

    @FXML
    private void goToMainMenu() {
        try{
            LevelParent myLevel = mainController.getLevelInstance(levelToReturn);
            
            myLevel.removeEventListener(mainController);
            myLevel.destroyInstance();
            
            mainController.goToFXML(null, null, "menu");

        } catch (Exception e){
            e.printStackTrace();
        }
    }



    @FXML
    private void initialize() {
        // Add an event filter to block the spacebar from triggering the button action
        resumeButton.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.SPACE) {
                event.consume();
            }
        });

        menuButton.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.SPACE) {
                event.consume();
            }
        });
    }


}
