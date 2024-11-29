package com.example.demo.controller.fxmlPageControllers;


import java.io.IOException;
import com.example.demo.controller.FxmlController;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Button;

public class GameOverController extends FxmlController {

    @FXML
    Button restartButton;

    @FXML
    Button menuButton;



    @FXML
    private void restartLevel() throws IOException {
        mainController.changeLevel(null, levelToReturn);
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
        restartButton.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
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
