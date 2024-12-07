package com.example.demo.activeActors.planes;

import com.example.demo.activeActors.FighterPlane;
import com.example.demo.eventListeners.DropsEventListener;

/**
 * Represents a parent class for enemy planes with specific abilities and behaviors.
 */
public abstract class EnemyPlaneParent extends FighterPlane {
    
    private final int SCORE_VALUE;

    /**
     * Constructs a new EnemyPlaneParent with the specified parameters.
     *
     * @param imageName the name of the image representing the enemy plane
     * @param imageHeight the height of the image
     * @param initialXPos the initial x-coordinate of the enemy plane
     * @param initialYPos the initial y-coordinate of the enemy plane
     * @param health the initial health of the enemy plane
     * @param damageOutput the damage output of the enemy plane during collision of itself with the player
     */
    public EnemyPlaneParent(String imageName, int imageHeight, double initialXPos, double initialYPos, int health, int damageOutput) {
        super(imageName, imageHeight, initialXPos, initialYPos, health, damageOutput);
        SCORE_VALUE = health;
    }


    

    /**
     * [Default behavior] Updates the position of the enemy plane based on its horizontal velocity.
     */
    @Override
    public void updatePosition() {
        int horizontalVelocity = getHorizontalVelocity();
        moveHorizontally(horizontalVelocity);
    }

    /**
     * Destroys the enemy plane and triggers drop events based on probabilities.
     */
    @Override
    public void destroy() {
        super.destroy();

        double heartDropRate = getHeartDropRate();
        double projectileUpDropRate = getProjectileUpDropRate();
        DropsEventListener dropsEventListener = getDropsEventListener();
        double currentProbabilityRoll = Math.random();

        if (currentProbabilityRoll < heartDropRate) {
            double currentX = this.getLayoutX() + this.getTranslateX();
            double currentY = this.getLayoutY() + this.getTranslateY();
            dropsEventListener.spawnHeartPowerUp(currentX, currentY);
        }

        if (currentProbabilityRoll > heartDropRate && currentProbabilityRoll < heartDropRate + projectileUpDropRate) {
            double currentX = this.getLayoutX() + this.getTranslateX();
            double currentY = this.getLayoutY() + this.getTranslateY();
            dropsEventListener.spawnProjectilePowerUp(currentX, currentY);
        }
    }

    /**
     * Gets the score value of the enemy plane.
     *
     * @return the score value of the enemy plane
     */
    @Override
    public int getScoreValue() {
        return SCORE_VALUE;
    }

//--------------------------------------------------------------------------------------
//-----------------------------------Abstract Methods-----------------------------------

	/**
     * Adds an event listener for drop events.
     *
     * @param listener the DropsEventListener to be added
     */
    public abstract void addEventListener(DropsEventListener listener);


    /**
     * Gets the horizontal velocity of the enemy plane.
     *
     * @return the horizontal velocity of the enemy plane
     */
    protected abstract int getHorizontalVelocity();


    /**
     * Gets the heart drop rate of the enemy plane.
     *
     * @return the 'heart power up' drop rate of the enemy plane
     */
    protected abstract double getHeartDropRate();


    /**
     * Gets the projectile upgrade drop rate of the enemy plane.
     *
     * @return the 'projectile upgrade' drop rate of the enemy plane
     */
    protected abstract double getProjectileUpDropRate();

    /**
     * Gets the drop event listener for the enemy plane.
     *
     * @return the drop event listener for the enemy plane
     */
    protected abstract DropsEventListener getDropsEventListener();

}