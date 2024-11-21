package com.example.demo.activeActors;

import javafx.scene.image.*;

public abstract class ActiveActorDestructible extends ImageView implements Destructible {

	private boolean isDestroyed;
	private boolean canScoreFromCollision;

	private static final String IMAGE_LOCATION = "/com/example/demo/images/";

	public ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		this.setImage(new Image(getClass().getResource(IMAGE_LOCATION + imageName).toExternalForm()));
		this.setLayoutX(initialXPos);
		this.setLayoutY(initialYPos);
		this.setFitHeight(imageHeight);
		this.setPreserveRatio(true);
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

	protected void moveHorizontally(double horizontalMove) {
		this.setTranslateX(getTranslateX() + horizontalMove);
	}

	protected void moveVertically(double verticalMove) {
		this.setTranslateY(getTranslateY() + verticalMove);
	}
	
}
