package com.example.demo.eventListeners;

import com.example.demo.levels.LevelParent;

/**
 * Interface for listening to level events such as changing levels and navigating to FXML pages.
 */
public interface LevelEventListener {

    /**
     * Changes the level to the specified level name.
     *
     * @param currentLevel the current level to be changed
     * @param levelName the name of the level to change to
     */
    void changeLevel(LevelParent currentLevel, String levelName);


    /**
     * Navigates to a specified FXML page.
     *
     * @param currentLevel the current level prior to switching to the FXML page
     * @param levelToReturn the class path of the level to return to if the FXML page has a button that returns to the level
     * @param fxml the name of the FXML file to switch to
     * @throws Exception if an error occurs during navigation
     */
    void goToFXML(LevelParent currentLevel, String levelToReturn, String fxml) throws Exception;

    
}