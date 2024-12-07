package com.example.demo.levels.waveLevels;

import com.example.demo.activeActors.ActiveActorDestructible;
import com.example.demo.activeActors.FighterPlane;
import com.example.demo.activeActors.planes.enemyPlanes.EnemyPlaneT1;
import com.example.demo.levels.LevelTwo;
import com.example.demo.levels.WaveLevel;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents Level One in the game with specific enemy spawn probabilities and wave size.
 */
public class LevelOne extends WaveLevel {
    
    private static LevelOne instance;

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background1.jpg";
    private static final String CURRENT_LEVEL = LevelOne.class.getName();
    private static final String NEXT_LEVEL = LevelTwo.class.getName();
    private int waveSize = 10;
    private static final double ENEMY_SPAWN_PROBABILITY = 0.05;

    /**
     * Constructs a new LevelOne with the specified screen dimensions.
     *
     * @param screenHeight the height of the screen
     * @param screenWidth the width of the screen
     */
    private LevelOne(double screenHeight, double screenWidth) {
        super(screenHeight, screenWidth, CURRENT_LEVEL, NEXT_LEVEL);

        // Background is declared in the superclass, but since it is different for each level, it is initialized here
        background = new ImageView(new Image(getClass().getResource(BACKGROUND_IMAGE_NAME).toExternalForm()));
    }

    /**
     * Gets the singleton instance of LevelOne.
     *
     * @param screenHeight the height of the screen
     * @param screenWidth the width of the screen
     * @return the singleton instance of LevelOne
     */
    public static LevelOne getInstance(double screenHeight, double screenWidth) {
        if (instance == null) {
            instance = new LevelOne(screenHeight, screenWidth);
        }
        return instance;
    }

    /**
     * Destroys the singleton instance of LevelOne.
     */
    public void destroyInstance() {
        instance = null;
    }

	
    /**
     * Spawns enemy units based on the spawn probabilities and wave size.
     */
    @Override
    protected void spawnEnemyUnits() {
        if (!waveHasEnded()) {
            if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
                double newEnemyInitialYPosition = (Math.random() * (FighterPlane.Y_LOWER_BOUND - FighterPlane.Y_UPPER_BOUND)) + FighterPlane.Y_UPPER_BOUND;
                ActiveActorDestructible newEnemy = new EnemyPlaneT1(getScreenWidth(), newEnemyInitialYPosition);
                activeActorManager.addEnemyUnit(newEnemy);
                waveSize--;
            }
        }
        updateWavesRemaining(waveSize + getCurrentNumberOfEnemies());
    }


    /**
     * Checks if the wave has ended.
     *
     * @return true if the wave has ended, false otherwise
     */
    @Override
    protected boolean waveHasEnded() {
        return waveSize == 0;
    }


    /**
     * Gets the size of the wave.
     *
     * @return the size of the wave
     */
    @Override
    protected int getWaveSize() {
        return waveSize;
    }

}