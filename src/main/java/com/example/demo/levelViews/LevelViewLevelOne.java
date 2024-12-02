package com.example.demo.levelViews;

import com.example.demo.imageObjects.hud.WavesLeftDisplay;

import javafx.scene.Group;

public class LevelViewLevelOne extends LevelViewParent {

	private final Group root;
    private final WavesLeftDisplay wavesLeftDisplay;

    private static final double WAVES_DISPLAY_X_POSITION = 950;
    private static final double WAVES_DISPLAY_Y_POSITION = 25;
    
    public LevelViewLevelOne(Group root, int heartsToDisplay, int score, int wavesCount) {
        super(root, heartsToDisplay, score);
        this.root = root;
        wavesLeftDisplay = new WavesLeftDisplay(WAVES_DISPLAY_X_POSITION, WAVES_DISPLAY_Y_POSITION, wavesCount);
    }
    
    @Override
    public void addImagesToRoot() {
        root.getChildren().add(wavesLeftDisplay.getContainer());
    }

    public void updateWavesLeft(int wavesRemaining) {
        wavesLeftDisplay.updateRemainingWaves(wavesRemaining);
    }
    
}
