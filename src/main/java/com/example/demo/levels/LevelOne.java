package com.example.demo.levels;


import com.example.demo.activeActors.ActiveActorDestructible;
import com.example.demo.activeActors.FighterPlane;
import com.example.demo.activeActors.planes.enemyPlanes.EnemyPlaneT1;
import com.example.demo.levelViews.LevelViewLevelOne;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LevelOne extends LevelParent {
	
	private static LevelOne instance;

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background1.jpg";
	private static final String CURRENT_LEVEL = LevelOne.class.getName();
	private static final String NEXT_LEVEL = LevelTwo.class.getName();
	private static final int TOTAL_ENEMIES = 5;
	private static final int KILLS_TO_ADVANCE = 10;
	private static final double ENEMY_SPAWN_PROBABILITY = .20;




	private LevelOne(double screenHeight, double screenWidth) {
		super(screenHeight, screenWidth, CURRENT_LEVEL);

		//background is declared in super class, but since it is different for each level, it is initialized here
		background = new ImageView(new Image(getClass().getResource(BACKGROUND_IMAGE_NAME).toExternalForm()));
	}

	public static LevelOne getInstance(double screenHeight, double screenWidth) {
		if (instance == null) {
			instance = new LevelOne(screenHeight, screenWidth);
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
		else if (userHasReachedKillTarget()){
			goToNextLevel(NEXT_LEVEL);
		}
			
	}


	@Override
	protected void spawnEnemyUnits() {
		int currentNumberOfEnemies = getCurrentNumberOfEnemies();
		for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
			if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
				double newEnemyInitialYPosition = (Math.random() * (FighterPlane.Y_LOWER_BOUND - FighterPlane.Y_UPPER_BOUND))+FighterPlane.Y_UPPER_BOUND;
				ActiveActorDestructible newEnemy = new EnemyPlaneT1(getScreenWidth(), newEnemyInitialYPosition);
				activeActorManager.addEnemyUnit(newEnemy);
			}
		}
	}


	private boolean userHasReachedKillTarget() {
		return getKillCount() >= KILLS_TO_ADVANCE;
	}

	@Override
	protected void instantiateLevelView(){
		//reference to levelView is stored in super class to access methods that are generated the same for all levels
		super.levelView = new LevelViewLevelOne(getRoot(), getUser().getHealth());
	};

}
