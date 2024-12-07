package com.example.demo.levelViews;

import com.example.demo.imageObjects.hud.WavesLeftDisplay;

import javafx.scene.Group;

/**
 * Represents the view for wave-based levels, including the waves left display.
 */
public class LevelViewWaveLevel extends LevelViewParent {

    private final Group root;
    private final WavesLeftDisplay wavesLeftDisplay;

    private static final double WAVES_DISPLAY_X_POSITION = 950;
    private static final double WAVES_DISPLAY_Y_POSITION = 25;
    
    /**
     * Constructs a new LevelViewWaveLevel with the specified root, hearts to display, score, and waves count.
     *
     * @param root the root group for the level view
     * @param heartsToDisplay the number of hearts to display
     * @param score the initial score to display
     * @param wavesCount the initial number of waves to display
     */
    public LevelViewWaveLevel(Group root, int heartsToDisplay, int score, int wavesCount) {
        super(root, heartsToDisplay, score);
        this.root = root;
        wavesLeftDisplay = new WavesLeftDisplay(WAVES_DISPLAY_X_POSITION, WAVES_DISPLAY_Y_POSITION, wavesCount);
    }

    
    /**
     * Adds level-specific images to the root group. In this case, the 'waves left' display.
     */
    @Override
    public void addImagesToRoot() {
        root.getChildren().add(wavesLeftDisplay.getContainer());
    }


    /**
     * Updates the number of 'waves left' displayed.
     *
     * @param wavesRemaining the new number of waves remaining
     */
    public void updateWavesLeft(int wavesRemaining) {
        wavesLeftDisplay.updateRemainingWaves(wavesRemaining);
    }


}