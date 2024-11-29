package com.example.demo.controller.fxmlPageControllers;

import com.example.demo.controller.FxmlController;
import com.example.demo.levels.LevelParent;
import javafx.fxml.FXML;

public class PauseController extends FxmlController {
    
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
}
