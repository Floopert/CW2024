package com.example.demo.activeActors.projectileTypes.enemyProjectiles;

import com.example.demo.activeActors.Projectile;

/**
 * Represents a T1 projectile fired by an enemy plane.
 */
public class EnemyProjectileT1 extends Projectile {
    
    private static final String IMAGE_NAME = "enemyFire.png";
    private static final int IMAGE_HEIGHT = 25;
    private static final int HORIZONTAL_VELOCITY = -10;
    private static final int DAMAGE_OUTPUT = 1;


    /**
     * Constructs a new EnemyProjectileT1 with the specified initial position.
     *
     * @param initialXPos the initial x-coordinate of the projectile
     * @param initialYPos the initial y-coordinate of the projectile
     */
    public EnemyProjectileT1(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, DAMAGE_OUTPUT);
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