package com.example.demo.activeActors.projectileTypes.bossProjectiles;

import com.example.demo.activeActors.Projectile;

public class BossProjectileT2 extends Projectile {
	
	private static final String IMAGE_NAME = "fireball2.png";
	private static final int IMAGE_HEIGHT = 75;
	private static final int HORIZONTAL_VELOCITY = -15;
	private static final int DAMAGE_OUTPUT = 4;

	public BossProjectileT2(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, DAMAGE_OUTPUT);
	}

	protected int getHorizontalVelocity(){
		return HORIZONTAL_VELOCITY;
	}
	
}
