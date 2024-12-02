package com.example.demo.activeActors.planes;

import com.example.demo.activeActors.ActiveActorDestructible;
import com.example.demo.activeActors.FighterPlane;
import com.example.demo.activeActors.projectileTypes.UserProjectileFactory;
import com.example.demo.eventListeners.PowerUpEffectEventListener;

public class UserPlane extends FighterPlane implements PowerUpEffectEventListener{

	private static final String IMAGE_NAME = "userplane.png";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private static final int PLAYER_MAX_HEALTH = 10;
	private static final int DAMAGE_OUTPUT = -1;
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
	private int projectileLevel;
	private static UserPlane instance;
	private static final UserProjectileFactory projectileFactory = UserProjectileFactory.getInstance();
	

	private UserPlane() {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, PLAYER_INITIAL_HEALTH, DAMAGE_OUTPUT);
		yVelocityMultiplier = 0;
		projectileLevel = 1;
	}


	public static UserPlane getInstance() {
		
		if (instance == null) {
			instance = new UserPlane();
		}

		return instance;
		
	}

	public void destroyInstance() {
		instance = null;
	}


	
	@Override
	public void updatePosition() {
		if (isMovingVertically()) {
			double initialTranslateY = getTranslateY();
			this.moveVertically(VERTICAL_VELOCITY * yVelocityMultiplier);
			double newYPosition = getLayoutY() + getTranslateY();
			if (newYPosition < FighterPlane.Y_UPPER_BOUND || newYPosition > FighterPlane.Y_LOWER_BOUND) {
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
		return projectileFactory.createProjectile(projectileLevel, getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET), getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET));
	}

	public void resetPosition(){
		instance.setTranslateX(0);
		instance.setTranslateY(0);
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


	//----------------------------------------------------------
	// methods for power up effects

	@Override
	public void heartPowerUpEffect() {
		if (health < PLAYER_MAX_HEALTH) {
			health++;
		}
	}

	@Override
	public void projectilePowerUpEffect() {
		if (projectileLevel < projectileFactory.getTotalProjectileTypes()) {
			projectileLevel++;
		}
	}


}
