package com.example.demo.activeActors.projectileTypes.bossProjectiles;

import com.example.demo.activeActors.Projectile;


public class BossMissleProjectile extends Projectile {
	
	private static final String IMAGE_NAME = "missle.png";
	private static final int IMAGE_HEIGHT = 30;
	private static final int HORIZONTAL_VELOCITY = -10;
	private static final int DAMAGE_OUTPUT = 8;


	public BossMissleProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, DAMAGE_OUTPUT);
	}



	protected int getHorizontalVelocity(){
		return HORIZONTAL_VELOCITY;
	}
	
}
