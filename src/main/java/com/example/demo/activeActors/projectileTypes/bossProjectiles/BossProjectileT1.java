package com.example.demo.activeActors.projectileTypes.bossProjectiles;

import com.example.demo.activeActors.Projectile;

/**
 * Represents a Tier 1 fireball projectile fired by a boss plane.
 */
public class BossProjectileT1 extends Projectile {
    
    private static final String IMAGE_NAME = "fireball.png";
    private static final int IMAGE_HEIGHT = 75;
    private static final int HORIZONTAL_VELOCITY = -15;
    private static final int DAMAGE_OUTPUT = 2;

	
    /**
     * Constructs a new BossProjectileT1 with the specified initial position.
     *
     * @param initialXPos the initial x-coordinate of the fireball projectile
     * @param initialYPos the initial y-coordinate of the fireball projectile
     */
    public BossProjectileT1(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, DAMAGE_OUTPUT);
    }


    /**
     * Gets the horizontal velocity of the fireball projectile.
     *
     * @return the horizontal velocity of the fireball projectile
     */
    @Override
    protected int getHorizontalVelocity() {
        return HORIZONTAL_VELOCITY;
    }

}