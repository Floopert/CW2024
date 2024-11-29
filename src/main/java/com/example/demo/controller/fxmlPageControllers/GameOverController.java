package com.example.demo.controller.fxmlPageControllers;


import java.io.IOException;
import com.example.demo.controller.FxmlController;
import javafx.fxml.FXML;

public class GameOverController extends FxmlController {

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
    
}
