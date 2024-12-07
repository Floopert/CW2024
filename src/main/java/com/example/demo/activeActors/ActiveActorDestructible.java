package com.example.demo.activeActors;

import javafx.scene.image.*;


/**
 * Represents an active actor that can be destroyed.
 */
public abstract class ActiveActorDestructible extends ImageView {

	private static final String IMAGE_LOCATION = "/com/example/demo/images/";

    protected int health;
    private int damageOutput;
    private boolean isDestroyed;
    private boolean canScoreFromCollision;

    



    /**
     * Constructs a new ActiveActorDestructible with the specified parameters.
     *
     * @param imageName the name of the image representing the actor
     * @param imageHeight the height of the image
     * @param initialXPos the initial x-coordinate of the actor
     * @param initialYPos the initial y-coordinate of the actor
     * @param health the initial health of the actor
     * @param damageOutput the damage output of the actor when it collides with another actor
     */
    public ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos, int health, int damageOutput) {
        this.setImage(new Image(getClass().getResource(IMAGE_LOCATION + imageName).toExternalForm()));
        this.setLayoutX(initialXPos);
        this.setLayoutY(initialYPos);
        this.setFitHeight(imageHeight);
        this.setPreserveRatio(true);
        this.health = health;
        this.damageOutput = damageOutput;
        isDestroyed = false;
        canScoreFromCollision = false;
    }



    /**
     * Updates the actor's state e.g. position.
     */
    public void updateActor() {
        updatePosition();
    }

    

    /**
     * Takes damage and updates the actor's health.
     *
     * @param damage 
	 * The amount of damage to be taken.
	 * If the damage parameter is set to -1, the actor is destroyed regardless of health.
     */
    public void takeDamage(int damage) {
        if (damage == -1) {
            this.destroy();
        } else {
            health -= damage;
            if (healthAtZero()) {
                this.destroy();
            }
        }
    }

    /**
     * Gets the damage output of the actor.
     *
     * @return the damage output of the actor during collision with other actors
     */
    public int getDamageOutput() {
        return damageOutput;
    }


	/**
     * Checks if the actor can score from a collision, provided the other actor is destroyed.
     *
     * @return true if the actor can score from a collision, false otherwise
     */
    public boolean canScoreFromCollision() {
        return canScoreFromCollision;
    }


    /**
     * Gets the health of the actor.
     *
     * @return the health of the actor
     */
    public int getHealth() {
        return health;
    }

    /**
     * Destroys the actor.
     */
    public void destroy() {
        setDestroyed(true);
    }

	/**
     * Checks if the actor is destroyed.
     *
     * @return true if the actor is destroyed, false otherwise
     */
    public boolean isDestroyed() {
        return isDestroyed;
    }



//--------------------------------------------------
//------------PROTECTED METHODS----------------------

    /**
     * Sets the destroyed state of the actor.
     *
     * @param isDestroyed the destroyed state to be set
     */
    protected void setDestroyed(boolean isDestroyed) {
        this.isDestroyed = isDestroyed;
    }

    

    /**
     * Sets whether the actor can score from a collision.
     *
     * @param canScoreFromCollision the scoreable state to be set
     */
    protected void setScoreableFromCollision(boolean canScoreFromCollision) {
        this.canScoreFromCollision = canScoreFromCollision;
    }

    

    /**
     * Moves the actor horizontally.
     *
     * @param horizontalMove the distance to move horizontally
     */
    protected void moveHorizontally(double horizontalMove) {
        this.setTranslateX(getTranslateX() + horizontalMove);
    }

    /**
     * Moves the actor vertically.
     *
     * @param verticalMove the distance to move vertically
     */
    protected void moveVertically(double verticalMove) {
        this.setTranslateY(getTranslateY() + verticalMove);
    }


//--------------------------------------------------
//------------PRIVATE METHODS------------------------

	/**
     * Checks if the actor's health is zero or lesser.
     *
     * @return true if the actor's health <= 0, false otherwise
     */
    private boolean healthAtZero() {
        return health <= 0;
    }


//--------------------------------------------------
//------------ABSTRACT METHODS----------------------

	/**
     * Updates the position of the actor.
     */
    public abstract void updatePosition();

	
}