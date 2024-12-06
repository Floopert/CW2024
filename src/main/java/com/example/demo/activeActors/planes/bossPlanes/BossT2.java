package com.example.demo.activeActors.planes.bossPlanes;

import com.example.demo.activeActors.ActiveActorDestructible;
import com.example.demo.activeActors.FighterPlane;
import com.example.demo.activeActors.planes.bossPlanes.bossAbilities.MissleAbility;
import com.example.demo.activeActors.planes.bossPlanes.bossAbilities.ShieldAbility;
import com.example.demo.activeActors.planes.movePatternLogic.RandomYMovePattern;
import com.example.demo.activeActors.projectileTypes.bossProjectiles.BossMissleProjectile;
import com.example.demo.activeActors.projectileTypes.bossProjectiles.BossProjectileT2;
import com.example.demo.eventListeners.BossEventListener;

/**
 * Represents the BossT2 plane with specific abilities and behaviors.
 */
public class BossT2 extends FighterPlane {

    private static final String IMAGE_NAME = "bossplaneT2.png";
    private static final double INITIAL_X_POSITION = 950.0;
    private static final double INITIAL_Y_POSITION = 300;
    private static final double PROJECTILE_Y_POSITION_OFFSET = 40.0;
    private static final double PROJECTILE_X_POSITION_OFFSET = 0;
    private static final double MISSLE_PROJECTILE_Y_POSITION_OFFSET = 40;
    private static final double MISSLE_PROJECTILE_X_POSITION_OFFSET = -100;
    private static final double BOSS_FIRE_RATE = .04;
    private static final double BOSS_SHIELD_PROBABILITY = 0.002;
    private static final double BOSS_MISSLE_FIRE_PROBABILITY = 0.004;
    private static final int IMAGE_HEIGHT = 100;
    private static final int VERTICAL_VELOCITY = 8;
    private static final int HEALTH = 500;
    private static final int DAMAGE_OUTPUT = HEALTH;
    private static final int MOVE_FREQUENCY_PER_CYCLE = 5;
    private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;
    private static final int Y_LOWER_BOUND_ADJUSTMENT = 50;
    private static final int MAX_FRAMES_WITH_SHIELD = 100;

    private RandomYMovePattern movePattern;
    private ShieldAbility shieldAbility;
    private MissleAbility missleAbility;
    private BossEventListener listener;

    /**
     * Constructs a new BossT2 plane with specific abilities and behaviors.
     */
    public BossT2() {
        super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH, DAMAGE_OUTPUT);
        movePattern = new RandomYMovePattern(MOVE_FREQUENCY_PER_CYCLE, MAX_FRAMES_WITH_SAME_MOVE, VERTICAL_VELOCITY);
        movePattern.initializeMovePattern();
        shieldAbility = new ShieldAbility(BOSS_SHIELD_PROBABILITY, MAX_FRAMES_WITH_SHIELD);
        missleAbility = new MissleAbility(BOSS_MISSLE_FIRE_PROBABILITY);
    }

    /**
     * Adds an event listener for boss events.
     *
     * @param listener the BossEventListener to be added
     */
    public void addEventListener(BossEventListener listener) {
        this.listener = listener;
        shieldAbility.addEventListener(listener);
    }

    /**
     * Updates the position of the boss plane based on its move pattern.
     */
    @Override
    public void updatePosition() {
        double initialTranslateY = this.getTranslateY();
        moveVertically(movePattern.getNextMove());
        double currentYPosition = this.getLayoutY() + this.getTranslateY();
        if (currentYPosition < FighterPlane.Y_UPPER_BOUND || currentYPosition > FighterPlane.Y_LOWER_BOUND - Y_LOWER_BOUND_ADJUSTMENT) {
            this.setTranslateY(initialTranslateY);
        }

        listener.updateShieldPosition(this.getTranslateY());
    }


    /**
     * Updates the state of the boss plane, including its shield and missle abilities.
     */
    @Override
    public void updateActor() {
        super.updateActor();
        shieldAbility.updateShield();
        missleAbility.updateFire();
    }


    /**
     * Fires a projectile from the boss plane.
     *
     * @return a new BossMissleProjectile if the missle should fire in the current frame
	 * if not, check if normal projectile should fire in the current frame and returns BossProjectileT2
	 * if neither should fire, return null
     */
    @Override
    public ActiveActorDestructible fireProjectile() {
        double projectileXPosition;
        double projectileYPostion;
        if (missleAbility.shouldFire()) {
            projectileXPosition = getProjectileXPosition(MISSLE_PROJECTILE_X_POSITION_OFFSET);
            projectileYPostion = getProjectileYPosition(MISSLE_PROJECTILE_Y_POSITION_OFFSET);
            return new BossMissleProjectile(projectileXPosition, projectileYPostion);
        } else if (bossFiresInCurrentFrame()) {
            projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
            projectileYPostion = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
            return new BossProjectileT2(projectileXPosition, projectileYPostion);
        } else {
            return null;
        }
    }

    /**
     * Takes damage if the shield is not active.
     *
     * @param damage the amount of damage to be taken
     */
    @Override
    public void takeDamage(int damage) {
        boolean isShielded = shieldAbility.isShielded();
        if (!isShielded) {
            super.takeDamage(damage);
        }
    }

    /**
     * Gets the score value of the boss plane.
     *
     * @return the score value of the boss plane
     */
    @Override
    public int getScoreValue() {
        return HEALTH;
    }

    /**
     * Determines whether the boss fires in the current frame based on the fire rate.
     *
     * @return true if the boss fires in the current frame, false otherwise
     */
    private boolean bossFiresInCurrentFrame() {
        return Math.random() < BOSS_FIRE_RATE;
    }


}