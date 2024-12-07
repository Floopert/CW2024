package com.example.demo.levelViews;

import com.example.demo.imageObjects.hud.*;

import javafx.scene.Group;

/**
 * Represents the parent class for level views, including HUD elements like heart display and scoreboard.
 */
public abstract class LevelViewParent {
    
    private static final double HEART_DISPLAY_X_POSITION = 5;
    private static final double HEART_DISPLAY_Y_POSITION = 25;
    private static final double SCOREBOARD_X_POSITION = 550;
    private static final double SCOREBOARD_Y_POSITION = 25;
    private final Group root;
    private final HeartDisplay heartDisplay;
    private final Scoreboard scoreboard;

    /**
     * Constructs a new LevelViewParent with the specified root, hearts to display, and score.
     *
     * @param root the root group for the level view
     * @param heartsToDisplay the number of hearts to display
     * @param score the initial score to display
     */
    public LevelViewParent(Group root, int heartsToDisplay, int score) {
        this.root = root;
        this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
        this.scoreboard = new Scoreboard(SCOREBOARD_X_POSITION, SCOREBOARD_Y_POSITION, score);
    }


    /**
     * Shows the HUD elements on screen, including heart display and scoreboard.
     */
    public void showHud() {
        showHeartDisplay();
        showScoreboard();
    }

    

    /**
     * Updates the hearts displayed in the heart display.
     *
     * @param heartsRemaining the number of hearts remaining
     */
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

    /**
     * Updates the score displayed in the scoreboard.
     *
     * @param score the new score to display
     */
    public void updateScore(int score) {
        scoreboard.updateScoreValue(score);
    }


//------------------------------------------------------------------------------------//
//----------------------------- PRIVATE HELPER METHODS -------------------------------//


	/**
     * Adds the heart display to root group.
     */
    private void showHeartDisplay() {
        root.getChildren().add(heartDisplay.getContainer());
    }

    /**
     * Adds the scoreboard to root group.
     */
    private void showScoreboard() {
        root.getChildren().add(scoreboard.getContainer());
    }



//------------------------------------------------------------------------------------//
//--------------------------------- ABSTRACT METHODS ---------------------------------//

    /**
     * Adds images to the root group.
     */
    public abstract void addImagesToRoot();



}