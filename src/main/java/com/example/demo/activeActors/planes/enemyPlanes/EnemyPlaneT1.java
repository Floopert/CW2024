package com.example.demo.activeActors.planes.enemyPlanes;

import com.example.demo.activeActors.ActiveActorDestructible;
import com.example.demo.activeActors.planes.EnemyPlaneParent;
import com.example.demo.activeActors.projectileTypes.enemyProjectiles.EnemyProjectileT1;
import com.example.demo.eventListeners.DropsEventListener;

/**
 * Represents the EnemyPlaneT1 with specific abilities and behaviors.
 */
public class EnemyPlaneT1 extends EnemyPlaneParent {

    private static final String IMAGE_NAME = "enemyplaneT1.png";
    private static final double PROJECTILE_X_POSITION_OFFSET = -60.0;
    private static final double PROJECTILE_Y_POSITION_OFFSET = 25.0;
    private static final double HEART_DROP_RATE = 0.01;
    private static final double PROJECTILE_UP_DROP_RATE = 0.005;
    private static final double FIRE_RATE = .01;
    private static final int IMAGE_HEIGHT = 50;
    private static final int HORIZONTAL_VELOCITY = -6;
    private static final int INITIAL_HEALTH = 1;
    private static final int DAMAGE_OUTPUT = INITIAL_HEALTH;

    private DropsEventListener dropsEventListener;



    /**
     * Constructs a new EnemyPlaneT1 with the specified initial position.
     *
     * @param initialXPos the initial x-coordinate of the enemy plane
     * @param initialYPos the initial y-coordinate of the enemy plane
     */
    public EnemyPlaneT1(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH, DAMAGE_OUTPUT);
    }


    /**
     * Fires a projectile from the enemy plane.
     *
     * @return a new EnemyProjectileT1 if the enemy fires in the current frame, null otherwise
     */
    @Override
    public ActiveActorDestructible fireProjectile() {
        if (Math.random() < FIRE_RATE) {
            double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
            double projectileYPostion = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
            return new EnemyProjectileT1(projectileXPosition, projectileYPostion);
        }
        return null;
    }


    /**
     * Adds an event listener for power up drop events.
     *
     * @param listener the DropsEventListener to be added
     */
    public void addEventListener(DropsEventListener listener) {
        dropsEventListener = listener;
    }


    /**
     * Gets the horizontal velocity of the enemy plane.
     *
     * @return the horizontal velocity of the enemy plane
     */
    @Override
    protected int getHorizontalVelocity() {
        return HORIZONTAL_VELOCITY;
    }


    /**
     * Gets the 'heart power up' drop rate of the enemy plane.
     *
     * @return the 'heart power up' drop rate of the enemy plane
     */
    @Override
    protected double getHeartDropRate() {
        return HEART_DROP_RATE;
    }


    /**
     * Gets the 'projectile upgrade' drop rate of the enemy plane.
     *
     * @return the 'projectile upgrade' drop rate of the enemy plane
     */
    @Override
    protected double getProjectileUpDropRate() {
        return PROJECTILE_UP_DROP_RATE;
    }


    /**
     * Gets the drop event listener for the enemy plane.
     *
     * @return the drop event listener for the enemy plane
     */
    @Override
    protected DropsEventListener getDropsEventListener() {
        return dropsEventListener;
    }

}