package com.example.demo.activeActors;

public abstract class Projectile extends ActiveActorDestructible {

	private static final int HEALTH = 1;

	public Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos, int damageOutput) {
		super(imageName, imageHeight, initialXPos, initialYPos, HEALTH, damageOutput);
	}


}
