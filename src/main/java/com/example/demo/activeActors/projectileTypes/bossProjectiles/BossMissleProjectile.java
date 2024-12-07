package com.example.demo.activeActors.projectileTypes.bossProjectiles;

import com.example.demo.activeActors.Projectile;

/**
 * Represents a missle projectile fired by a boss plane due to the addition of the boss missle ability.
 */
public class BossMissleProjectile extends Projectile {
    
    private static final String IMAGE_NAME = "missle.png";
    private static final int IMAGE_HEIGHT = 30;
    private static final int HORIZONTAL_VELOCITY = -10;
    private static final int DAMAGE_OUTPUT = 8;


    /**
     * Constructs a new BossMissleProjectile with the specified initial position.
     *
     * @param initialXPos the initial x-coordinate of the missile projectile
     * @param initialYPos the initial y-coordinate of the missile projectile
     */
    public BossMissleProjectile(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, DAMAGE_OUTPUT);
    }


    /**
     * Gets the horizontal velocity of the missile projectile.
     *
     * @return the horizontal velocity of the missile projectile
     */
    @Override
    protected int getHorizontalVelocity() {
        return HORIZONTAL_VELOCITY;
    }

}