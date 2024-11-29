package com.example.demo.controller.fxmlPageControllers;

import java.io.IOException;
import com.example.demo.controller.FxmlController;
import javafx.fxml.FXML;



public class MenuController extends FxmlController {

    private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.levels.LevelOne";

    @FXML
    private void startGame() throws IOException {
        mainController.changeLevel(null,LEVEL_ONE_CLASS_NAME);
    }

    @FXML
    private void exitGame() {
        System.exit(0);
    }
}
