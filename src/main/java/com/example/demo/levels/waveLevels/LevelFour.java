package com.example.demo.levels.waveLevels;


import com.example.demo.activeActors.ActiveActorDestructible;
import com.example.demo.activeActors.FighterPlane;
import com.example.demo.activeActors.planes.enemyPlanes.EnemyPlaneT3;
import com.example.demo.activeActors.planes.enemyPlanes.EnemyPlaneT2;
import com.example.demo.levels.WaveLevel;
import com.example.demo.levels.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LevelFour extends WaveLevel {
	
	private static LevelFour instance;

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background4.jpg";
	private static final String CURRENT_LEVEL = LevelFour.class.getName();
	private static final String NEXT_LEVEL = LevelFive.class.getName();
	private int waveSize = 40;
	private static final double T3_ENEMY_SPAWN_PROBABILITY = 0.03;
	private static final double T2_ENEMY_SPAWN_PROBABILITY = 0.05;

	private int T3EnemyCount = 15;
	private int T2EnemyCount = waveSize - T3EnemyCount;


	private LevelFour(double screenHeight, double screenWidth) {
		super(screenHeight, screenWidth, CURRENT_LEVEL, NEXT_LEVEL);

		//background is declared in super class, but since it is different for each level, it is initialized here
		background = new ImageView(new Image(getClass().getResource(BACKGROUND_IMAGE_NAME).toExternalForm()));
	}

	public static LevelFour getInstance(double screenHeight, double screenWidth) {
		if (instance == null) {
			instance = new LevelFour(screenHeight, screenWidth);
		}
		return instance;
	}

	public void destroyInstance() {
		instance = null;
	}



	@Override
	protected void spawnEnemyUnits() {
		if(!waveHasEnded()){
			if (Math.random() < T2_ENEMY_SPAWN_PROBABILITY && T2EnemyCount > 0) {
				double newEnemyInitialYPosition = (Math.random() * (FighterPlane.Y_LOWER_BOUND - FighterPlane.Y_UPPER_BOUND))+FighterPlane.Y_UPPER_BOUND;
				ActiveActorDestructible newEnemy = new EnemyPlaneT2(getScreenWidth(), newEnemyInitialYPosition);
				activeActorManager.addEnemyUnit(newEnemy);
				waveSize--;
				T2EnemyCount--;
			}

			if (Math.random() < T3_ENEMY_SPAWN_PROBABILITY && T3EnemyCount > 0) {
				double newEnemyInitialYPosition = (Math.random() * (FighterPlane.Y_LOWER_BOUND - FighterPlane.Y_UPPER_BOUND))+FighterPlane.Y_UPPER_BOUND;
				ActiveActorDestructible newEnemy = new EnemyPlaneT3(getScreenWidth(), newEnemyInitialYPosition);
				activeActorManager.addEnemyUnit(newEnemy);
				waveSize--;
				T3EnemyCount--;
			}
		}
		updateWavesRemaining(waveSize + getCurrentNumberOfEnemies());
	}

	@Override
	protected boolean waveHasEnded() {
		return waveSize == 0;
	}

	@Override
	protected int getWaveSize() {
		return waveSize;
	}


}
