package com.example.demo.activeActors.powerUpTypes;

import com.example.demo.activeActors.PowerUp;
import com.example.demo.activeActors.planes.UserPlane;
import com.example.demo.eventListeners.PowerUpEffectEventListener;

/**
 * Represents a heart power-up that increases the health of the user plane.
 */
public class HeartUp extends PowerUp {

    private static final String IMAGE_NAME = "addHeart.png";
    private static final int IMAGE_HEIGHT = 30;
    private static final int HORIZONTAL_VELOCITY = -6;

    private PowerUpEffectEventListener eventListener;


    /**
     * Constructs a new HeartUp power-up with the specified initial position.
     *
     * @param initialXPos the initial x-coordinate of the heart power-up
     * @param initialYPos the initial y-coordinate of the heart power-up
     */
    public HeartUp(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
        eventListener = UserPlane.getInstance();
    }


    /**
     * Gets the horizontal velocity of the heart power-up.
     *
     * @return the horizontal velocity of the heart power-up
     */
    @Override
    public int getHorizontalVelocity() {
        return HORIZONTAL_VELOCITY;
    }


    /**
     * Flags the power-up for destruction after a collision and triggers the heart power-up effect.
     *
     * @param damage the user's damage output -1 should be passed in here, which would instantly destroy the power-up
     */
    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);
        eventListener.heartPowerUpEffect();
    }


}