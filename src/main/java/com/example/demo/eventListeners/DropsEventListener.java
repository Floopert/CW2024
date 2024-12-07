package com.example.demo.eventListeners;

/**
 * Interface for listening to drop events such as spawning power-ups.
 */
public interface DropsEventListener {

    /**
     * Spawns a heart power-up at the specified position.
     *
     * @param initialXPos the initial x-coordinate of the heart power-up
     * @param initialYPos the initial y-coordinate of the heart power-up
     */
    void spawnHeartPowerUp(double initialXPos, double initialYPos);


    /**
     * Spawns a projectile power-up at the specified position.
     *
     * @param initialXPos the initial x-coordinate of the projectile power-up
     * @param initialYPos the initial y-coordinate of the projectile power-up
     */
    void spawnProjectilePowerUp(double initialXPos, double initialYPos);

    
}