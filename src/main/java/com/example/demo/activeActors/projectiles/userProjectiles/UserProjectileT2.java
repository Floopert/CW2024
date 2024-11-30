package com.example.demo.activeActors.projectiles.userProjectiles;

import com.example.demo.activeActors.Projectile;

public class UserProjectileT2 extends Projectile {

	private static final String IMAGE_NAME = "userfire2.png";
	private static final int IMAGE_HEIGHT = 20;
	private static final int HORIZONTAL_VELOCITY = 15;
	private static final int DAMAGE_OUTPUT = 2;

	public UserProjectileT2(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, DAMAGE_OUTPUT);
		this.setScoreableFromCollision(true);
	}

	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}
	
	
}
