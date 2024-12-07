package com.example.demo.activeActors;

/**
 * Represents a projectile that can be fired by an actor.
 */
public abstract class Projectile extends ActiveActorDestructible {

    private static final int HEALTH = 1;

    /**
     * Constructs a new Projectile with the specified parameters.
     *
     * @param imageName the name of the image representing the projectile
     * @param imageHeight the height of the image
     * @param initialXPos the initial x-coordinate of the projectile
     * @param initialYPos the initial y-coordinate of the projectile
     * @param damageOutput the damage output of the projectile
     */
    public Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos, int damageOutput) {
        super(imageName, imageHeight, initialXPos, initialYPos, HEALTH, damageOutput);
    }

    

    /**
     * Updates the position of the projectile based on its horizontal velocity.
     */
    @Override
    public void updatePosition() {
        int horizontalVelocity = getHorizontalVelocity();
        moveHorizontally(horizontalVelocity);
    }


	/**
     * Gets the horizontal velocity of the projectile.
     *
     * @return the horizontal velocity of the projectile
     */
    protected abstract int getHorizontalVelocity();

	
}