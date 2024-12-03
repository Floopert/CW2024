package com.example.demo.activeActors.planes.enemyPlanes;

import com.example.demo.activeActors.ActiveActorDestructible;
import com.example.demo.activeActors.planes.EnemyPlaneParent;
import com.example.demo.activeActors.projectileTypes.enemyProjectiles.EnemyProjectileT2;
import com.example.demo.eventListeners.DropsEventListener;

public class EnemyPlaneT2 extends EnemyPlaneParent {

	private static final String IMAGE_NAME = "enemyplaneT2.png";
	private static final int IMAGE_HEIGHT = 50;
	private static final int HORIZONTAL_VELOCITY = -5;
	private static final double PROJECTILE_X_POSITION_OFFSET = -60.0;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 25.0;
	private static final int INITIAL_HEALTH = 3;
	private static final int DAMAGE_OUTPUT = INITIAL_HEALTH;
	private static final double FIRE_RATE = .007;

	private static final double heartDropRate = 0.25;
	private static final double projectileUpDropRate = 0.25;

	private DropsEventListener dropsEventListener;

	public EnemyPlaneT2(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH, DAMAGE_OUTPUT);
	}


	@Override
	public ActiveActorDestructible fireProjectile() {
		if (Math.random() < FIRE_RATE) {
			double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
			double projectileYPostion = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
			return new EnemyProjectileT2(projectileXPosition, projectileYPostion);
		}
		return null;
	}

	

	public void addEventListener(DropsEventListener listener) {
		dropsEventListener = listener;
	}

	@Override
	protected int getHorizontalVelocity(){
		return HORIZONTAL_VELOCITY;
	}

	@Override
	protected double getHeartDropRate(){
		return heartDropRate;
	}

	@Override
	protected double getProjectileUpDropRate(){
		return projectileUpDropRate;
	}

	@Override
	protected DropsEventListener getDropsEventListener(){
		return dropsEventListener;
	}
	

}
