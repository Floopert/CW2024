package com.example.demo.eventListeners;

import com.example.demo.levels.LevelParent;

public interface LevelEventListener {
    public void changeLevel(LevelParent currentLevel, String levelName);
    public void goToFXML(LevelParent currentLevel, String levelToReturn, String fxml) throws Exception;
}
