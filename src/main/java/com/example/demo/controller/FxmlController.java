package com.example.demo.controller;

public class FxmlController {
    
    protected Controller mainController;
    protected String levelToReturn;

    public void setMainController(Controller mainController){
        this.mainController = mainController;
    };

    public void setLevelToReturn(String levelToReturn){
        this.levelToReturn = levelToReturn;
    };
}
