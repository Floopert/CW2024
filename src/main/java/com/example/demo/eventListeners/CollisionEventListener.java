package com.example.demo.eventListeners;

/**
 * Interface for listening to collision events such as user damage and score updates.
 */
public interface CollisionEventListener {

    /**
     * Called when the user is damaged.
     *
     * @param damage the amount of damage taken by the user
     */
    void userDamaged(int damage);

    /**
     * Called to increase the score.
     *
     * @param score the amount to update the score by
     */
    void addScore(int score);
    
}