package com.example.demo.levelViews;

import javafx.scene.Group;

public class LevelViewLevelOne extends LevelViewParent {

	private final Group root;
    
    public LevelViewLevelOne(Group root, int heartsToDisplay, int score) {
        super(root, heartsToDisplay, score);
        this.root = root;
    }
    
    @Override
    public void addImagesToRoot() {
        // TODO add score display
        
    }
    
}
