package com.example.demo.activeActors.planes.bossPlanes.bossAbilities;

import com.example.demo.eventListeners.BossEventListener;

public class ShieldAbility {
    
    private boolean isShielded;
    private int framesWithShieldActivated;
    private final double BOSS_SHIELD_PROBABILITY;
    private final int MAX_FRAMES_WITH_SHIELD;
    private BossEventListener listener;

    public ShieldAbility(double bossShieldProbability, int maxFramesWithShield) {
        isShielded = false;
        framesWithShieldActivated = 0;
        this.BOSS_SHIELD_PROBABILITY = bossShieldProbability;
        this.MAX_FRAMES_WITH_SHIELD = maxFramesWithShield;
    }

    public void addEventListener(BossEventListener listener) {
        this.listener = listener;
    }

    public boolean isShielded() {
        return isShielded;
    }

    public void updateShield() {
		if (isShielded) framesWithShieldActivated++;
		else if (shieldShouldBeActivated()) activateShield();	
		if (shieldExhausted()) deactivateShield();
	}

	private boolean shieldShouldBeActivated() {
		return Math.random() < BOSS_SHIELD_PROBABILITY;
	}

	private boolean shieldExhausted() {
		return framesWithShieldActivated == MAX_FRAMES_WITH_SHIELD;
	}

	private void activateShield() {
		isShielded = true;
		listener.shieldActivated();
	}

	private void deactivateShield() {
		isShielded = false;
		framesWithShieldActivated = 0;
		listener.shieldDeactivated();
	}

}
