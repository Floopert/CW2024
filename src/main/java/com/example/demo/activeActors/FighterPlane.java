package com.example.demo.activeActors;

public abstract class FighterPlane extends ActiveActorDestructible {

	public static final int Y_UPPER_BOUND = 80;
	public static final int Y_LOWER_BOUND = 670;
	private static final int NO_SCORE_VALUE = 0;

	public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health, int damageOutput) {
		super(imageName, imageHeight, initialXPos, initialYPos, health, damageOutput);
	}

	public abstract ActiveActorDestructible fireProjectile();
	

	protected double getProjectileXPosition(double xPositionOffset) {
		return getLayoutX() + getTranslateX() + xPositionOffset;
	}

	protected double getProjectileYPosition(double yPositionOffset) {
		return getLayoutY() + getTranslateY() + yPositionOffset;
	}

	public int getScoreValue() {
		return NO_SCORE_VALUE;
	}
		
}
