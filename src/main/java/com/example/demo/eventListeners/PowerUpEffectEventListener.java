package com.example.demo.eventListeners;

/**
 * Interface for listening to power-up effect events such as heart power-ups and projectile power-ups.
 */
public interface PowerUpEffectEventListener {

    /**
     * Applies the heart power-up effect, which increasing the health of the player.
     */
    void heartPowerUpEffect();


    /**
     * Applies the projectile power-up effect, which increasing the projectile level of the player.
     */
    void projectilePowerUpEffect();
    
}