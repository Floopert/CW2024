package com.example.demo.activeActors.planes.bossPlanes.bossAbilities;

import com.example.demo.activeActors.projectileTypes.bossProjectiles.BossMissleProjectile;


public class MissleAbility {
    
    private boolean shouldFire;
    private final double FIRE_PROBABILITY;


    public MissleAbility(double fireProbability) {
        shouldFire = false;
        this.FIRE_PROBABILITY = fireProbability;
    }


    public boolean shouldFire() {
        return shouldFire;
    }

    public BossMissleProjectile fireMissle(double xPosition, double yPosition) {
		shouldFire = false;
		return new BossMissleProjectile(xPosition, yPosition);
	}


	public void updateFire() {
		shouldFire = (Math.random() < FIRE_PROBABILITY);
	}


}
