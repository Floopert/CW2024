package com.example.demo.activeActors.powerUpTypes;

import com.example.demo.activeActors.PowerUp;
import com.example.demo.activeActors.planes.UserPlane;
import com.example.demo.eventListeners.PowerUpEffectEventListener;

public class HeartUp extends PowerUp {

	private static final String IMAGE_NAME = "addHeart.png";
	private static final int IMAGE_HEIGHT = 30;
	private static final int HORIZONTAL_VELOCITY = -6;

	private PowerUpEffectEventListener eventListener;

	public HeartUp(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
		eventListener = UserPlane.getInstance();
	}

	@Override
	public int getHorizontalVelocity() {
		return HORIZONTAL_VELOCITY;
	}
	
	@Override
	public void takeDamage(int damage) {
		super.takeDamage(damage);
		eventListener.heartPowerUpEffect();
	}

	
}
