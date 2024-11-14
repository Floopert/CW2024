package com.example.demo;

import javafx.scene.Group;

public class LevelViewLevelOne extends LevelView {

	private final Group root;
    
    public LevelViewLevelOne(Group root, int heartsToDisplay) {
        super(root, heartsToDisplay);
        this.root = root;
    }
    
    @Override
    public void addImagesToRoot() {
        // TODO add score display
        
    }
    
}
