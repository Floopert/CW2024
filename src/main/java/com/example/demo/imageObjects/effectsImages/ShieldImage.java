package com.example.demo.imageObjects.effectsImages;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ShieldImage extends ImageView {
	
	private static final String IMAGE_NAME = "/com/example/demo/images/shield.png";
	private static final int SHIELD_SIZE = 200;
	private static final double SHIELD_X_POSITION_OFFSET = -50;
	private static final double SHIELD_Y_POSITION_OFFSET = -30;
	
	public ShieldImage(double xPosition, double yPosition) {
		this.setLayoutX(xPosition + SHIELD_X_POSITION_OFFSET);
		this.setLayoutY(yPosition + SHIELD_Y_POSITION_OFFSET);
		this.setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));
		this.setVisible(false);
		this.setFitHeight(SHIELD_SIZE);
		this.setPreserveRatio(true);
	}

	public void showShield() {
		this.setVisible(true);
	}
	
	public void hideShield() {
		this.setVisible(false);
	}

}
