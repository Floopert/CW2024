package com.example.demo.levels;

import com.example.demo.activeActors.planes.bossPlanes.BossT2;
import com.example.demo.eventListeners.BossEventListener;
import com.example.demo.levelViews.LevelViewLevelTwo;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LevelFive extends LevelParent implements BossEventListener{

	private static LevelFive instance;
	private static final String CURRENT_LEVEL = LevelFive.class.getName();
	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background5.jpg";
	private final BossT2 boss;

	private LevelViewLevelTwo levelView;

	private LevelFive(double screenHeight, double screenWidth) {
		super(screenHeight, screenWidth, CURRENT_LEVEL);

		//background is declared in super class, but since it is different for each level, it is initialized here
		background = new ImageView(new Image(getClass().getResource(BACKGROUND_IMAGE_NAME).toExternalForm()));

		boss = new BossT2();
		boss.addEventListener(this);

	}

	public static LevelFive getInstance(double screenHeight, double screenWidth) {
		if (instance == null) {
			instance = new LevelFive(screenHeight, screenWidth);
		}
		return instance;
	}

	public void destroyInstance() {
		instance = null;
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
		this.levelView = new LevelViewLevelTwo(getRoot(), getUser().getHealth(), getCurrentScore());
		this.levelView.instantiateShield(boss.getLayoutX(), boss.getLayoutY());
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
