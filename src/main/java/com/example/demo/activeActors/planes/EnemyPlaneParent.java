package com.example.demo.activeActors.planes;

import com.example.demo.activeActors.FighterPlane;
import com.example.demo.eventListeners.DropsEventListener;

public abstract class EnemyPlaneParent extends FighterPlane{
    
	private final int SCORE_VALUE;

    public EnemyPlaneParent(String imageName, int imageHeight, double initialXPos, double initialYPos, int health, int damageOutput) {
		super(imageName, imageHeight, initialXPos, initialYPos, health, damageOutput);
		SCORE_VALUE = health;
	}

    public abstract void addEventListener(DropsEventListener listener);

    protected abstract int getHorizontalVelocity();
    protected abstract double getHeartDropRate();
    protected abstract double getProjectileUpDropRate();
    protected abstract DropsEventListener getDropsEventListener();

    @Override
	public void updatePosition() {
		int horizontalVelocity = getHorizontalVelocity();
        moveHorizontally(horizontalVelocity);
	}

    @Override
	public void destroy() {
		super.destroy();

        
        double heartDropRate = getHeartDropRate();
        double projectileUpDropRate = getProjectileUpDropRate();
        DropsEventListener dropsEventListener = getDropsEventListener();
		double currentProbabilityRoll = Math.random();

		if (currentProbabilityRoll < heartDropRate) {
			double currentX = this.getLayoutX() + this.getTranslateX();
			double currentY = this.getLayoutY() + this.getTranslateY();
			dropsEventListener.spawnHeartPowerUp(currentX, currentY);
		}

		if (currentProbabilityRoll > heartDropRate && currentProbabilityRoll < heartDropRate+projectileUpDropRate) {
			double currentX = this.getLayoutX() + this.getTranslateX();
			double currentY = this.getLayoutY() + this.getTranslateY();
			dropsEventListener.spawnProjectilePowerUp(currentX, currentY);
		}
	}

	@Override
	public int getScoreValue() {
		return SCORE_VALUE;
	}

}
