package com.example.demo.controller.fxmlPageControllers;

import java.io.IOException;
import com.example.demo.controller.FxmlController;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Button;

public class WinController extends FxmlController{
    private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.levels.LevelOne";

    @FXML
    Button replayButton;

    @FXML
    Button menuButton;

    @FXML
    private void replayGame() throws IOException {

        mainController.changeLevel(null, LEVEL_ONE_CLASS_NAME);
    }

    @FXML
    private void goToMainMenu() {
        try{
            mainController.goToFXML(null, null, "menu");
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    @FXML
    private void initialize() {
        // Add an event filter to block the spacebar from triggering the button action
        replayButton.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
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
