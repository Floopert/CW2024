package com.example.demo.activeActors.planes;

import java.util.*;

import com.example.demo.activeActors.ActiveActorDestructible;
import com.example.demo.activeActors.FighterPlane;
import com.example.demo.activeActors.projectiles.BossProjectile;
import com.example.demo.eventListeners.BossEventListener;


public class Boss extends FighterPlane {

	private static final String IMAGE_NAME = "bossplane.png";
	private static final double INITIAL_X_POSITION = 950.0;
	private static final double INITIAL_Y_POSITION = 300;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 40.0;
	private static final double PROJECTILE_X_POSITION_OFFSET = 0;
	private static final double BOSS_FIRE_RATE = .04;
	private static final double BOSS_SHIELD_PROBABILITY = 0.002;
	private static final int IMAGE_HEIGHT = 100;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int HEALTH = 100;
	private static final int DAMAGE_OUTPUT = HEALTH;
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5;
	private static final int ZERO = 0;
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;
	private static final int Y_LOWER_BOUND_ADJUSTMENT = 50;
	private static final int MAX_FRAMES_WITH_SHIELD = 100;
	private final List<Integer> movePattern;
	private boolean isShielded;
	private int consecutiveMovesInSameDirection;
	private int indexOfCurrentMove;
	private int framesWithShieldActivated;

	private List<BossEventListener> listeners = new ArrayList<BossEventListener>();

	public Boss() {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH, DAMAGE_OUTPUT);
		movePattern = new ArrayList<>();
		consecutiveMovesInSameDirection = 0;
		indexOfCurrentMove = 0;
		framesWithShieldActivated = 0;
		isShielded = false;
		initializeMovePattern();
	}

	

	public void addEventListener(BossEventListener listener) {
		listeners.add(listener);
	}




	@Override
	public void updatePosition() {
		double initialTranslateY = this.getTranslateY();
		moveVertically(getNextMove());
		double currentYPosition = this.getLayoutY() + this.getTranslateY();
		if (currentYPosition < FighterPlane.Y_UPPER_BOUND || currentYPosition > FighterPlane.Y_LOWER_BOUND-Y_LOWER_BOUND_ADJUSTMENT) {
			this.setTranslateY(initialTranslateY);
		}

		for (BossEventListener listener : listeners) {
			listener.updateShieldPosition(this.getTranslateY());
		}
	}
	
	@Override
	public void updateActor() {
		super.updateActor();
		updateShield();
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
		double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
		double projectileYPostion = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
		return bossFiresInCurrentFrame() ? new BossProjectile(projectileXPosition, projectileYPostion) : null;
	}
	
	@Override
	public void takeDamage(int damage) {
		if (!isShielded) {
			super.takeDamage(damage);
		}
	}

	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY);
			movePattern.add(-VERTICAL_VELOCITY);
			movePattern.add(ZERO);
		}
		Collections.shuffle(movePattern);
	}

	private void updateShield() {
		if (isShielded) framesWithShieldActivated++;
		else if (shieldShouldBeActivated()) activateShield();	
		if (shieldExhausted()) deactivateShield();
	}

	private int getNextMove() {
		int currentMove = movePattern.get(indexOfCurrentMove);
		consecutiveMovesInSameDirection++;
		if (consecutiveMovesInSameDirection == MAX_FRAMES_WITH_SAME_MOVE) {
			Collections.shuffle(movePattern);
			consecutiveMovesInSameDirection = 0;
			indexOfCurrentMove++;
		}
		if (indexOfCurrentMove == movePattern.size()) {
			indexOfCurrentMove = 0;
		}
		return currentMove;
	}

	private boolean bossFiresInCurrentFrame() {
		return Math.random() < BOSS_FIRE_RATE;
	}


	private boolean shieldShouldBeActivated() {
		return Math.random() < BOSS_SHIELD_PROBABILITY;
	}

	private boolean shieldExhausted() {
		return framesWithShieldActivated == MAX_FRAMES_WITH_SHIELD;
	}

	private void activateShield() {
		isShielded = true;
		for (BossEventListener listener : listeners) {
			listener.shieldActivated();
		}
	}

	private void deactivateShield() {
		isShielded = false;
		framesWithShieldActivated = 0;
		for (BossEventListener listener : listeners) {
			listener.shieldDeactivated();
		}

	}

}
