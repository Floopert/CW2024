package com.example.demo.controller;

/**
 * Base controller class for FXML pages.
 */
public class FxmlController {
    
    protected Controller mainController;
    protected String levelToReturn;

    /**
     * Sets the main controller for this controller.
     *
     * @param mainController the main controller to be set
     */
    public void setMainController(Controller mainController) {
        this.mainController = mainController;
    }

    /**
     * Sets the level to return to for this controller.
     *
     * @param levelToReturn the level to return to
     */
    public void setLevelToReturn(String levelToReturn) {
        this.levelToReturn = levelToReturn;
    }
}