package com.example.demo.planes;

import com.example.demo.activeActors.ActiveActorDestructible;
import com.example.demo.activeActors.FighterPlane;
import com.example.demo.projectiles.EnemyProjectile;

public class EnemyPlane extends FighterPlane {

	private static final String IMAGE_NAME = "enemyplane.png";
	private static final int IMAGE_HEIGHT = 50;
	private static final int HORIZONTAL_VELOCITY = -6;
	private static final double PROJECTILE_X_POSITION_OFFSET = -60.0;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 25.0;
	private static final int INITIAL_HEALTH = 1;
	private static final double FIRE_RATE = .01;

	public EnemyPlane(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
	}

	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
		if (Math.random() < FIRE_RATE) {
			double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
			double projectileYPostion = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
			return new EnemyProjectile(projectileXPosition, projectileYPostion);
		}
		return null;
	}


}