package com.example.demo;

public class LevelTwo extends LevelParent implements BossEventListener{

	private static LevelTwo instance;

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final Boss boss;
	private LevelViewLevelTwo levelView;

	private LevelTwo(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
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
	public void shieldActivated() {
		instance.levelView.showShield();
	}

	@Override
	public void shieldDeactivated() {
		instance.levelView.hideShield();
	}


	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
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
			addEnemyUnit(boss);
		}
	}

	@Override
	protected LevelView instantiateLevelView() {
		levelView = new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH);
		return levelView;
	}

}
