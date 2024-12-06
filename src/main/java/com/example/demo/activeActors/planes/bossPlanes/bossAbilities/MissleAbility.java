package com.example.demo.activeActors.planes.bossPlanes.bossAbilities;

import com.example.demo.activeActors.projectileTypes.bossProjectiles.BossMissleProjectile;

/**
 * Represents the missile firing ability of a boss plane.
 */
public class MissleAbility {
    
    private boolean shouldFire;
    private final double FIRE_PROBABILITY;

    /**
     * Constructs a new MissleAbility with the specified fire probability.
     *
     * @param fireProbability the probability that the missile should fire
     */
    public MissleAbility(double fireProbability) {
        shouldFire = false;
        this.FIRE_PROBABILITY = fireProbability;
    }

    /**
     * Checks whether the missile should fire.
     *
     * @return true if the missile should fire, false otherwise
     */
    public boolean shouldFire() {
        return shouldFire;
    }
    
    /**
     * Fires a missile from the specified position.
     *
     * @param xPosition the x-coordinate of the missile's initial position
     * @param yPosition the y-coordinate of the missile's initial position
     * @return a new BossMissleProjectile object representing the fired missile
     */
    public BossMissleProjectile fireMissle(double xPosition, double yPosition) {
        shouldFire = false;
        return new BossMissleProjectile(xPosition, yPosition);
    }

    /**
     * Updates the firing status based on the fire probability.
     */
	public void updateFire() {
		shouldFire = (Math.random() < FIRE_PROBABILITY);
	}


}
