package com.example.demo.activeActors;

/**
 * Represents a power-up that can be collected by the player.
 */
public abstract class PowerUp extends ActiveActorDestructible {

    private static final int HEALTH = 1;
    private static final int DAMAGE_OUTPUT = 0;

    /**
     * Constructs a new PowerUp with the specified parameters.
     *
     * @param imageName the name of the image representing the power-up
     * @param imageHeight the height of the image
     * @param initialXPos the initial x-coordinate of the power-up
     * @param initialYPos the initial y-coordinate of the power-up
     */
    public PowerUp(String imageName, int imageHeight, double initialXPos, double initialYPos) {
        super(imageName, imageHeight, initialXPos, initialYPos, HEALTH, DAMAGE_OUTPUT);
    }

    

    /**
     * Updates the position of the power-up based on its horizontal velocity.
     */
    @Override
    public void updatePosition() {
        int horizontalVelocity = getHorizontalVelocity();
        moveHorizontally(horizontalVelocity);
    }


	/**
     * Gets the horizontal velocity of the power-up.
     *
     * @return the horizontal velocity of the power-up
     */
    protected abstract int getHorizontalVelocity();

	
}