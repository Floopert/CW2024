package com.example.demo.activeActors.projectileTypes.bossProjectiles;

import com.example.demo.activeActors.Projectile;

/**
 * Represents a Tier 2 fireball projectile fired by a boss plane.
 */
public class BossProjectileT2 extends Projectile {
    
    private static final String IMAGE_NAME = "fireball2.png";
    private static final int IMAGE_HEIGHT = 75;
    private static final int HORIZONTAL_VELOCITY = -15;
    private static final int DAMAGE_OUTPUT = 4;

	
    /**
     * Constructs a new BossProjectileT2 with the specified initial position.
     *
     * @param initialXPos the initial x-coordinate of the fireball projectile
     * @param initialYPos the initial y-coordinate of the fireball projectile
     */
    public BossProjectileT2(double initialXPos, double initialYPos) {
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