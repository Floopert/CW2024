package com.example.demo.controller.fxmlPageControllers;

import java.io.IOException;
import com.example.demo.controller.FxmlController;
import com.example.demo.levels.waveLevels.LevelOne;

import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Button;

public class MenuController extends FxmlController {

    private static final String LEVEL_ONE_CLASS_NAME = LevelOne.class.getName();

    @FXML
    Button playButton;

    @FXML
    Button exitButton;

    @FXML
    private void startGame() throws IOException {
        mainController.changeLevel(null,LEVEL_ONE_CLASS_NAME);
    }

    @FXML
    private void exitGame() {
        System.exit(0);
    }


    @FXML
    private void initialize() {
        // Add an event filter to block the spacebar from triggering the button action
        playButton.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.SPACE) {
                event.consume();
            }
        });

        exitButton.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.SPACE) {
                event.consume();
            }
        });
    }
}
