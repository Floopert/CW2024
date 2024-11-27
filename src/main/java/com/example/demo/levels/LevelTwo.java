package com.example.demo.levels;

import com.example.demo.eventListeners.BossEventListener;
import com.example.demo.levelViews.LevelViewLevelTwo;
import com.example.demo.planes.Boss;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LevelTwo extends LevelParent implements BossEventListener{

	private static LevelTwo instance;

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final Boss boss;

	private LevelViewLevelTwo levelView;

	private LevelTwo(double screenHeight, double screenWidth) {
		super(screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);

		//background is declared in super class, but since it is different for each level, it is initialized here
		background = new ImageView(new Image(getClass().getResource(BACKGROUND_IMAGE_NAME).toExternalForm()));

		boss = new Boss();
		boss.addEventListener(this);

	}

	public static LevelTwo getInstance(double screenHeight, double screenWidth) {
		if (instance == null) {
			instance = new LevelTwo(screenHeight, screenWidth);
		}
		return instance;
	}

	


	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		}
		else if (boss.isDestroyed()) {
			winGame();
		}
	}

	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0) {
			activeActorManager.addEnemyUnit(boss);
		}
	}


	@Override
	protected void instantiateLevelView(){
		//this.levelView is to access level specific methods to generate level specific images
		this.levelView = new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH, boss.getLayoutX(), boss.getLayoutY());
		//another reference to levelView is stored in super class to access methods that are generated the same for all levels
		super.levelView = this.levelView;
	};

	

	//------------------------------------------------------------//
	// -----------------BossEventListener interface methods-----------------//

	@Override
	public void shieldActivated() {
		instance.levelView.showShield();
	}

	@Override
	public void shieldDeactivated() {
		instance.levelView.hideShield();
	}

	@Override
	public void updateShieldPosition(double yPosition) {
		instance.levelView.updateShieldPosition(yPosition);
	}

}
