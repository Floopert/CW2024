package com.example.demo.activeActors;

/**
 * Represents a fighter plane with specific abilities and behaviors.
 */
public abstract class FighterPlane extends ActiveActorDestructible {

    public static final int Y_UPPER_BOUND = 80;
    public static final int Y_LOWER_BOUND = 670;
    private static final int NO_SCORE_VALUE = 0;


    /**
     * Constructs a new FighterPlane with the specified parameters.
     *
     * @param imageName the name of the image representing the fighter plane
     * @param imageHeight the height of the image
     * @param initialXPos the initial x-coordinate of the fighter plane
     * @param initialYPos the initial y-coordinate of the fighter plane
     * @param health the initial health of the fighter plane
     * @param damageOutput the damage output of the fighter plane if it collides with another actor
     */
    public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health, int damageOutput) {
        super(imageName, imageHeight, initialXPos, initialYPos, health, damageOutput);
    }


	/**
     * Gets the score value of the fighter plane. The default is 0 if not overriden.
     *
     * @return the score value of the fighter plane.
     */
    public int getScoreValue() {
        return NO_SCORE_VALUE;
    }


    /**
     * Gets the x-coordinate position for the projectile with the specified offset.
     *
     * @param xPositionOffset the x-coordinate offset for the projectile
     * @return the x-coordinate position for the projectile
     */
    protected double getProjectileXPosition(double xPositionOffset) {
        return getLayoutX() + getTranslateX() + xPositionOffset;
    }


    /**
     * Gets the y-coordinate position for the projectile with the specified offset.
     *
     * @param yPositionOffset the y-coordinate offset for the projectile
     * @return the y-coordinate position for the projectile
     */
    protected double getProjectileYPosition(double yPositionOffset) {
        return getLayoutY() + getTranslateY() + yPositionOffset;
    }


    



//--------------------------------- Abstract Methods ---------------------------------//

    /**
     * Fires a projectile from the fighter plane.
     *
     * @return the fired projectile
     */
    public abstract ActiveActorDestructible fireProjectile();


}