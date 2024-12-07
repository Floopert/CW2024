package com.example.demo.activeActors.projectileTypes.userProjectiles;

import com.example.demo.activeActors.Projectile;


/**
 * Represents a Tier 4 projectile fired by the user plane.
 */
public class UserProjectileT4 extends Projectile {

    private static final String IMAGE_NAME = "userfire4.png";
    private static final int IMAGE_HEIGHT = 21;
    private static final int HORIZONTAL_VELOCITY = 15;
    private static final int DAMAGE_OUTPUT = 5;


    /**
     * Constructs a new UserProjectileT4 with the specified initial position.
     *
     * @param initialXPos the initial x-coordinate of the projectile
     * @param initialYPos the initial y-coordinate of the projectile
     */
    public UserProjectileT4(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, DAMAGE_OUTPUT);
        this.setScoreableFromCollision(true);
    }


    /**
     * Gets the horizontal velocity of the projectile.
     *
     * @return the horizontal velocity of the projectile
     */
    @Override
    protected int getHorizontalVelocity() {
        return HORIZONTAL_VELOCITY;
    }


}