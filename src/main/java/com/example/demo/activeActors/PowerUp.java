package com.example.demo.activeActors;

public abstract class PowerUp extends ActiveActorDestructible {

	private static final int HEALTH = 1;
	private static final int DAMAGE_OUTPUT = 0;

	public PowerUp(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos, HEALTH, DAMAGE_OUTPUT);
	}

	protected abstract int getHorizontalVelocity();

	@Override
	public void updatePosition() {
		int horizontalVelocity = getHorizontalVelocity();
		moveHorizontally(horizontalVelocity);
	}

}
