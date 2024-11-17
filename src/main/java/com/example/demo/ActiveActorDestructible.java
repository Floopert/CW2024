package com.example.demo;

public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {

	private boolean isDestroyed;
	private boolean canScoreFromCollision;

	public ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		isDestroyed = false;
		canScoreFromCollision = false;
	}

	public void updateActor(){
		updatePosition();
	};

	public abstract void updatePosition();

	@Override
	public abstract void takeDamage();

	@Override
	public void destroy() {
		setDestroyed(true);
	}

	protected void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}

	public boolean isDestroyed() {
		return isDestroyed;
	}

	protected void setScoreableFromCollision(boolean canScoreFromCollision) {
		this.canScoreFromCollision = canScoreFromCollision;
	}

	public boolean canScoreFromCollision() {
		return canScoreFromCollision;
	}
	
}
