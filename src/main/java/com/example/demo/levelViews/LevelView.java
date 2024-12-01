package com.example.demo.levelViews;

import com.example.demo.imageObjects.HeartDisplay;


import javafx.scene.Group;

public abstract class LevelView {
	
	private static final double HEART_DISPLAY_X_POSITION = 5;
	private static final double HEART_DISPLAY_Y_POSITION = 25;
	private final Group root;
	private final HeartDisplay heartDisplay;
	
	public LevelView(Group root, int heartsToDisplay) {
		this.root = root;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
	}
	
	public void showHeartDisplay() {
		root.getChildren().add(heartDisplay.getContainer());
	}

	public abstract void addImagesToRoot();

	
	
	public void updateHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		if (currentNumberOfHearts < heartsRemaining) {
			for (int i = 0; i < heartsRemaining - currentNumberOfHearts; i++) {
				heartDisplay.addHeart();
			}
		} else if (currentNumberOfHearts > heartsRemaining) {
			for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
				heartDisplay.removeHeart();
			}
		}
		
	}

}
