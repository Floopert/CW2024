package com.example.demo.levels;

import com.example.demo.levelViews.LevelViewWaveLevel;

/**
 * Represents a wave-based level and is the parent for all wave-based levels in the game.
 */
public abstract class WaveLevel extends LevelParent {
    
    private final String nextLevel;

    /**
     * Constructs a new WaveLevel with the specified screen dimensions, current level, and next level.
     *
     * @param screenHeight the height of the screen
     * @param screenWidth the width of the screen
     * @param currentLevel the class path of the current level
     * @param nextLevel the class path of the next level
     */
    public WaveLevel(double screenHeight, double screenWidth, String currentLevel, String nextLevel) {
        super(screenHeight, screenWidth, currentLevel);
        this.nextLevel = nextLevel;
    }

    

    /**
     * Checks if the game is over by determining if the user is destroyed or if the wave has ended and all enemies are killed.
     */
    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (waveHasEnded() && allEnemiesKilled()) {
            goToNextLevel(nextLevel);
        }
    }

    /**
     * Checks if all enemies are killed.
     *
     * @return true if all enemies are killed, false otherwise
     */
    private boolean allEnemiesKilled() {
        return getCurrentNumberOfEnemies() == 0;
    }

    /**
     * Instantiates the level view for the wave level.
     */
    @Override
    protected void instantiateLevelView() {
        // Reference to levelView is stored in super class to access methods that are generated the same for all levels
        super.levelView = new LevelViewWaveLevel(getRoot(), getUser().getHealth(), getCurrentScore(), getWaveSize());
    }

    /**
     * Updates the number of waves remaining.
     *
     * @param wavesRemaining the number of waves remaining
     */
    protected void updateWavesRemaining(int wavesRemaining) {
        ((LevelViewWaveLevel) super.levelView).updateWavesLeft(wavesRemaining);
    }



//---------------------------------------------------------------------------
//-----------------------ABSTRACT METHODS------------------------------------

	/**
     * Gets the size of the wave.
     *
     * @return the size of the wave
     */
    protected abstract int getWaveSize();

    /**
     * Checks if the wave has ended.
     *
     * @return true if the wave has ended, false otherwise
     */
    protected abstract boolean waveHasEnded();

	
}