package com.example.demo;

public class UserPlane extends FighterPlane {

	private static final String IMAGE_NAME = "userplane.png";
	private static final double Y_UPPER_BOUND = 0;
	private static final double Y_LOWER_BOUND = 670.0;
	private static final double X_LOWER_BOUND = 0;
	private static final double X_UPPER_BOUND = 1125.0;
	private static final double INITIAL_X_POSITION = 5.0;
	private static final double INITIAL_Y_POSITION = 300.0;
	private static final int IMAGE_HEIGHT = 50;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int HORIZONTAL_VELOCITY = 8;
	private static final int PROJECTILE_X_POSITION_OFFSET = 150;
	private static final int PROJECTILE_Y_POSITION_OFFSET = 20;
	private int yVelocityMultiplier;
	private int xVelocityMultiplier;
	private int numberOfKills;

	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		yVelocityMultiplier = 0;
	}
	
	@Override
	public void updatePosition() {
		if (isMovingVertically()) {
			double initialTranslateY = getTranslateY();
			this.moveVertically(VERTICAL_VELOCITY * yVelocityMultiplier);
			double newYPosition = getLayoutY() + getTranslateY();
			if (newYPosition < Y_UPPER_BOUND || newYPosition > Y_LOWER_BOUND) {
				this.setTranslateY(initialTranslateY);
			}
		}

		if (isMovingHorizontally()) {
			double initialTranslateX = getTranslateX();
			this.moveHorizontally(HORIZONTAL_VELOCITY * xVelocityMultiplier);
			double newXPosition = getLayoutX() + getTranslateX();
			if (newXPosition > X_UPPER_BOUND || newXPosition < X_LOWER_BOUND) {
				this.setTranslateX(initialTranslateX);
			}

		}

	}
	
	
	@Override
	public ActiveActorDestructible fireProjectile() {
		return new UserProjectile(getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET), getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET));
	}

	private boolean isMovingVertically() {
		return yVelocityMultiplier != 0;
	}

	private boolean isMovingHorizontally() {
		return xVelocityMultiplier != 0;
	}

	public void moveUp() {
		yVelocityMultiplier = -1;
	}

	public void moveDown() {
		yVelocityMultiplier = 1;
	}

	public void moveLeft() {
		xVelocityMultiplier = -1;
	}

	public void moveRight() {
		xVelocityMultiplier = 1;
	}

	public void stopVerticalMovement() {
		yVelocityMultiplier = 0;
	}

	public void stopHorizontalMovement() {
		xVelocityMultiplier = 0;
	}

	public int getNumberOfKills() {
		return numberOfKills;
	}

	public void incrementKillCount() {
		numberOfKills++;
	}

}
