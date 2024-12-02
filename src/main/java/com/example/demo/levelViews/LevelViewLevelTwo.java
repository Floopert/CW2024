package com.example.demo.levelViews;

import com.example.demo.imageObjects.effectsImages.ShieldImage;

import javafx.scene.Group;

public class LevelViewLevelTwo extends LevelViewParent {

	private final Group root;
	private ShieldImage shieldImage;
	
	public LevelViewLevelTwo(Group root, int heartsToDisplay, int score) {
		super(root, heartsToDisplay, score);
		this.root = root;
	}
	
	public void instantiateShield(double shieldXPosition, double shieldYPosition) {
		this.shieldImage = new ShieldImage(shieldXPosition, shieldYPosition);
	}

	@Override
	public void addImagesToRoot() {
		root.getChildren().addAll(shieldImage);
	}
	
	public void showShield() {
		shieldImage.showShield();
	}

	public void hideShield() {
		shieldImage.hideShield();
	}

	public void updateShieldPosition(double yPosition) {
		shieldImage.setTranslateY(yPosition);
	}

}
