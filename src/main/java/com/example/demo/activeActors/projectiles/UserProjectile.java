package com.example.demo.activeActors.projectiles;

import com.example.demo.activeActors.Projectile;

public class UserProjectile extends Projectile {

	private static final String IMAGE_NAME = "userfire.png";
	private static final int IMAGE_HEIGHT = 12;
	private static final int HORIZONTAL_VELOCITY = 15;
	private static final int DAMAGE_OUTPUT = 1;

	public UserProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, DAMAGE_OUTPUT);
		this.setScoreableFromCollision(true);
	}

	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}
	
	
}
