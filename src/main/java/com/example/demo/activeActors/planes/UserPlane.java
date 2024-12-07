package com.example.demo.activeActors.planes;

import com.example.demo.activeActors.ActiveActorDestructible;
import com.example.demo.activeActors.FighterPlane;
import com.example.demo.activeActors.projectileTypes.UserProjectileFactory;
import com.example.demo.eventListeners.PowerUpEffectEventListener;

/**
 * Represents the user-controlled plane with specific abilities and behaviors.
 */
public class UserPlane extends FighterPlane implements PowerUpEffectEventListener {

    private static final String IMAGE_NAME = "userplane.png";
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private static final int PLAYER_MAX_HEALTH = 10;
    private static final int DAMAGE_OUTPUT = -1;
    private static final int IMAGE_HEIGHT = 50;
    private static final int VERTICAL_VELOCITY = 8;
    private static final int HORIZONTAL_VELOCITY = 8;
    private static final int PROJECTILE_X_POSITION_OFFSET = 150;
    private static final int PROJECTILE_Y_POSITION_OFFSET = 20;
    private static final double X_LOWER_BOUND = 0;
    private static final double X_UPPER_BOUND = 1125.0;
    private static final double INITIAL_X_POSITION = 5.0;
    private static final double INITIAL_Y_POSITION = 300.0;

    private int yVelocityMultiplier;
    private int xVelocityMultiplier;
    private int projectileLevel;
    private static UserPlane instance;
    private static final UserProjectileFactory projectileFactory = UserProjectileFactory.getInstance();

    /**
     * Constructs a new UserPlane with specific abilities and behaviors.
     */
    private UserPlane() {
        super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, PLAYER_INITIAL_HEALTH, DAMAGE_OUTPUT);
        yVelocityMultiplier = 0;
        projectileLevel = 1;
    }


//--------------------------------------------------------------------------------------------------------
//-----------------------------------Singleton methods----------------------------------------------------

    /**
     * Gets the singleton instance of the UserPlane.
     *
     * @return the singleton instance of the UserPlane
     */
    public static UserPlane getInstance() {
        if (instance == null) {
            instance = new UserPlane();
        }
        return instance;
    }

    /**
     * Destroys the singleton instance of the UserPlane.
     */
    public void destroyInstance() {
        instance = null;
    }


//------------------------------------------------------------------------------------------------------------
//-----------------------------------Object Position methods--------------------------------------------------

    /**
     * Updates the position of the user plane based on the user's input and its velocity.
     */
    @Override
    public void updatePosition() {
        if (isMovingVertically()) {
            double initialTranslateY = getTranslateY();
            this.moveVertically(VERTICAL_VELOCITY * yVelocityMultiplier);
            double newYPosition = getLayoutY() + getTranslateY();
            if (newYPosition < FighterPlane.Y_UPPER_BOUND || newYPosition > FighterPlane.Y_LOWER_BOUND) {
                this.setTranslateY(initialTranslateY);
            }
        }

        if (isMovingHorizontally()) {
            double initialTranslateX = getTranslateX();
            this.moveHorizontally(HORIZONTAL_VELOCITY * xVelocityMultiplier);
            double newXPosition = getLayoutX() + getTranslateX();
            if (newXPosition > X_UPPER_BOUND || newXPosition < X_LOWER_BOUND) {
                this.setTranslateX(initialTranslateX);
            }
        }
    }

	/**
     * Resets the position of the user plane to the initial spawn position.
     */
    public void resetPosition() {
        instance.setTranslateX(0);
        instance.setTranslateY(0);
    }

	/**
     * Flags the user plane to move up.
     */
    public void moveUp() {
        yVelocityMultiplier = -1;
    }

    /**
     * Flags the user plane to move down.
     */
    public void moveDown() {
        yVelocityMultiplier = 1;
    }

    /**
     * Flags the user plane to move left.
     */
    public void moveLeft() {
        xVelocityMultiplier = -1;
    }

    /**
     * Flags the user plane to move right.
     */
    public void moveRight() {
        xVelocityMultiplier = 1;
    }

    /**
     * Flags the user plane to stop any vertical movement.
     */
    public void stopVerticalMovement() {
        yVelocityMultiplier = 0;
    }

    /**
     * Flags the user plane to stop any horizontal movement.
     */
    public void stopHorizontalMovement() {
        xVelocityMultiplier = 0;
    }


//--------------------------------------------------------------------------------------------------------
//-----------------------------------Projectile methods---------------------------------------------------

    /**
     * Fires a projectile from the user plane.
     *
     * @return a new projectile created by the UserProjectileFactory
     */
    @Override
    public ActiveActorDestructible fireProjectile() {
        return projectileFactory.createProjectile(projectileLevel, getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET), getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET));
    }



//--------------------------------------------------------------------------------------------------------
//-----------------------------------Private helper methods-----------------------------------------------

    /**
     * Checks if the user plane is moving vertically.
     *
     * @return true if the user plane is moving vertically, false otherwise
     */
    private boolean isMovingVertically() {
        return yVelocityMultiplier != 0;
    }

    /**
     * Checks if the user plane is moving horizontally.
     *
     * @return true if the user plane is moving horizontally, false otherwise
     */
    private boolean isMovingHorizontally() {
        return xVelocityMultiplier != 0;
    }

    



//--------------------------------------------------------------------------------------------------------
//-----------------------------------PowerUpEffectEventListener methods-----------------------------------

    /**
     * Increases the health of the user plane by one, up to the maximum health.
     */
    @Override
    public void heartPowerUpEffect() {
        if (health < PLAYER_MAX_HEALTH) {
            health++;
        }
    }

    /**
     * Increases the projectile level of the user plane by one, up to the maximum level.
     */
    @Override
    public void projectilePowerUpEffect() {
        if (projectileLevel < projectileFactory.getTotalProjectileTypes()) {
            projectileLevel++;
        }
    }
}