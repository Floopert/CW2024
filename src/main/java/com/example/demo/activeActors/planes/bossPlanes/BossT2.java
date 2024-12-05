package com.example.demo.activeActors.planes.bossPlanes;


import com.example.demo.activeActors.ActiveActorDestructible;
import com.example.demo.activeActors.FighterPlane;
import com.example.demo.activeActors.planes.bossPlanes.bossAbilities.MissleAbility;
import com.example.demo.activeActors.planes.bossPlanes.bossAbilities.ShieldAbility;
import com.example.demo.activeActors.planes.movePatternLogic.RandomYMovePattern;
import com.example.demo.activeActors.projectileTypes.bossProjectiles.BossMissleProjectile;
import com.example.demo.activeActors.projectileTypes.bossProjectiles.BossProjectileT2;
import com.example.demo.eventListeners.BossEventListener;


public class BossT2 extends FighterPlane {

	private static final String IMAGE_NAME = "bossplaneT2.png";
	private static final double INITIAL_X_POSITION = 950.0;
	private static final double INITIAL_Y_POSITION = 300;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 40.0;
	private static final double PROJECTILE_X_POSITION_OFFSET = 0;
	private static final double MISSLE_PROJECTILE_Y_POSITION_OFFSET = 40;
	private static final double MISSLE_PROJECTILE_X_POSITION_OFFSET = -100;
	private static final double BOSS_FIRE_RATE = .04;
	private static final int IMAGE_HEIGHT = 100;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int HEALTH = 500;
	private static final int DAMAGE_OUTPUT = HEALTH;
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5;
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;
	private static final int Y_LOWER_BOUND_ADJUSTMENT = 50;
	private static final double BOSS_SHIELD_PROBABILITY = 0.002;
	private static final int MAX_FRAMES_WITH_SHIELD = 100;
	private static final double BOSS_MISSLE_FIRE_PROBABILITY = 0.004;
	
	
	private RandomYMovePattern movePattern;
	private ShieldAbility shieldAbility;
	private MissleAbility missleAbility;
	private BossEventListener listener;


	public BossT2() {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH, DAMAGE_OUTPUT);
		movePattern = new RandomYMovePattern(MOVE_FREQUENCY_PER_CYCLE, MAX_FRAMES_WITH_SAME_MOVE, VERTICAL_VELOCITY);
		movePattern.initializeMovePattern();
		shieldAbility = new ShieldAbility(BOSS_SHIELD_PROBABILITY, MAX_FRAMES_WITH_SHIELD);
		missleAbility = new MissleAbility(BOSS_MISSLE_FIRE_PROBABILITY);
	}

	

	public void addEventListener(BossEventListener listener) {
		this.listener = listener;
		shieldAbility.addEventListener(listener);
	}


	@Override
	public void updatePosition() {
		double initialTranslateY = this.getTranslateY();
		moveVertically(movePattern.getNextMove());
		double currentYPosition = this.getLayoutY() + this.getTranslateY();
		if (currentYPosition < FighterPlane.Y_UPPER_BOUND || currentYPosition > FighterPlane.Y_LOWER_BOUND-Y_LOWER_BOUND_ADJUSTMENT) {
			this.setTranslateY(initialTranslateY);
		}

		listener.updateShieldPosition(this.getTranslateY());
	}
	
	@Override
	public void updateActor() {
		super.updateActor();
		shieldAbility.updateShield();
		missleAbility.updateFire();
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
		double projectileXPosition; 
		double projectileYPostion;
		if (missleAbility.shouldFire()) {
			projectileXPosition = getProjectileXPosition(MISSLE_PROJECTILE_X_POSITION_OFFSET);
			projectileYPostion = getProjectileYPosition(MISSLE_PROJECTILE_Y_POSITION_OFFSET);
			return new BossMissleProjectile(projectileXPosition, projectileYPostion);
		}
		else if (bossFiresInCurrentFrame()) {
			projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
			projectileYPostion = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
			return new BossProjectileT2(projectileXPosition, projectileYPostion);
		}
		else return null;
	}
	
	@Override
	public void takeDamage(int damage) {
		boolean isShielded = shieldAbility.isShielded();
		if (!isShielded) {
			super.takeDamage(damage);
		}
	}


	private boolean bossFiresInCurrentFrame() {
		return Math.random() < BOSS_FIRE_RATE;
	}

	@Override
	public int getScoreValue() {
		return HEALTH;
	}

}
