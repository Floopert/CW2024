package com.example.demo.eventListeners;

/**
 * Interface for listening to input events such as firing projectiles and pausing the game.
 */
public interface InputEventListener {

    /**
     * Fires a projectile.
     */
    default void fireProjectile(){};

    
    /**
     * Pauses the game.
     */
    default void pauseGame(){};

}