package com.example.demo.activeActors.projectileTypes.enemyProjectiles;

import com.example.demo.activeActors.Projectile;

public class EnemyProjectileT1 extends Projectile {
	
	private static final String IMAGE_NAME = "enemyFire.png";
	private static final int IMAGE_HEIGHT = 25;
	private static final int HORIZONTAL_VELOCITY = -10;
	private static final int DAMAGE_OUTPUT = 1;

	public EnemyProjectileT1(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, DAMAGE_OUTPUT);
	}

	protected int getHorizontalVelocity(){
		return HORIZONTAL_VELOCITY;
	};



}