package com.example.demo.activeActors.planes.bossPlanes.bossAbilities;

import com.example.demo.eventListeners.BossEventListener;

/**
 * Represents the shield ability of a boss plane.
 */
public class ShieldAbility {
    
    private final double BOSS_SHIELD_PROBABILITY;
    private final int MAX_FRAMES_WITH_SHIELD;

    private boolean isShielded;
    private int framesWithShieldActivated;
    private BossEventListener listener;

    /**
     * Constructs a new ShieldAbility with the specified shield probability and maximum frames with shield.
     *
     * @param bossShieldProbability the probability that the shield should be activated
     * @param maxFramesWithShield the maximum number of frames the shield can be active
     */
    public ShieldAbility(double bossShieldProbability, int maxFramesWithShield) {
        isShielded = false;
        framesWithShieldActivated = 0;
        this.BOSS_SHIELD_PROBABILITY = bossShieldProbability;
        this.MAX_FRAMES_WITH_SHIELD = maxFramesWithShield;
    }

    /**
     * Adds an event listener for shield activation and deactivation events.
     *
     * @param listener the BossEventListener to be added
     */
    public void addEventListener(BossEventListener listener) {
        this.listener = listener;
    }

    /**
     * Checks whether the shield is currently active.
     *
     * @return true if the shield is active, false otherwise
     */
    public boolean isShielded() {
        return isShielded;
    }

    /**
     * Updates the shield status. Activates or deactivates the shield based on the probability and frame count.
     */
    public void updateShield() {
        if (isShielded) {
            framesWithShieldActivated++;
        } else if (shieldShouldBeActivated()) {
            activateShield();
        }
        if (shieldExhausted()) {
            deactivateShield();
        }
    }

    /**
     * Determines whether the shield should be activated based on the probability.
     *
     * @return true if the shield should be activated, false otherwise
     */
    private boolean shieldShouldBeActivated() {
        return Math.random() < BOSS_SHIELD_PROBABILITY;
    }

    /**
     * Determines whether the shield has been active for the maximum number of frames.
     *
     * @return true if the shield has been active for the maximum number of frames, false otherwise
     */
    private boolean shieldExhausted() {
        return framesWithShieldActivated == MAX_FRAMES_WITH_SHIELD;
    }

    /**
     * Activates the shield and notifies the listener.
     */
    private void activateShield() {
        isShielded = true;
        listener.shieldActivated();
    }

    /**
     * Deactivates the shield and notifies the listener.
     */
    private void deactivateShield() {
        isShielded = false;
        framesWithShieldActivated = 0;
        listener.shieldDeactivated();
    }

	
}