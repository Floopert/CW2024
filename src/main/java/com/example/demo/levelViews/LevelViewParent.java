package com.example.demo.levelViews;

import com.example.demo.imageObjects.hud.*;

import javafx.scene.Group;

public abstract class LevelViewParent {
	
	private static final double HEART_DISPLAY_X_POSITION = 5;
	private static final double HEART_DISPLAY_Y_POSITION = 25;
	private static final double SCOREBOARD_X_POSITION = 500;
	private static final double SCOREBOARD_Y_POSITION = 25;
	private final Group root;
	private final HeartDisplay heartDisplay;
	private final Scoreboard scoreboard;
	


	public abstract void addImagesToRoot();

	public LevelViewParent(Group root, int heartsToDisplay, int score) {
		this.root = root;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
		this.scoreboard = new Scoreboard(SCOREBOARD_X_POSITION, SCOREBOARD_Y_POSITION, score);
	}
	

	public void showHud() {
		showHeartDisplay();
		showScoreboard();
	}

	private void showHeartDisplay() {
		root.getChildren().add(heartDisplay.getContainer());
	}

	private void showScoreboard() {
		root.getChildren().add(scoreboard.getContainer());
	}
	
	
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

	public void updateScore(int score) {
		scoreboard.updateScoreValue(score);
	}

}
