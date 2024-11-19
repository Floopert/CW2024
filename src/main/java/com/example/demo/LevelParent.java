package com.example.demo;

import java.util.*;

import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.util.Duration;

import com.example.demo.handlers.*;

public abstract class LevelParent extends Observable {


	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
	private static final int MILLISECOND_DELAY = 50;
	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;

	private final Group root;
	private final Timeline timeline;
	private final UserPlane user;
	private final Scene scene;
	protected ImageView background;
	protected LevelView levelView;

	protected ActiveActorManager activeActorManager;
	private InputHandler inputHandler;
	
	private int killCount = 0;


	//-----------------Abstract methods to be implemented by subclasses-----------------//
	protected abstract void checkIfGameOver();

	protected abstract void spawnEnemyUnits();

	protected abstract void instantiateLevelView();



	//-----------------------------------------------------------------------------------//

	/**
	 * Constructor for LevelParent
	 * @param screenHeight
	 * @param screenWidth
	 * @param playerInitialHealth
	 */
	public LevelParent(double screenHeight, double screenWidth, int playerInitialHealth) {
		
		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.timeline = new Timeline();
		this.user = new UserPlane(playerInitialHealth);
		
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		
		instantiateLevelView();
		initializeTimeline();
	}



	//-----------------------------------------------------------------------------------//
	
	/**
	 * This method initializes the timeline for the game loop.
	 * Called in constructor
	 */
	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
	}
	

	


	/** 
	 * This method attaches the ActiveActorManager (for managing all active actor actions) & InputHandler (for managing user input)
	 * 
	 * This method also attaches all the graphical elements of a level
	 * e.g. Background, user plane, heart display, and any other level specific graphics (such as shield)
	*/
	public Scene initializeScene() {
		
		//attach activeActorManager and inputHandler to scene
		activeActorManager = new ActiveActorManager(root);
		inputHandler = new InputHandler(activeActorManager, background, user);
		inputHandler.initializeUserControls();

		//sets background height and width, then add into root node for rendering
		showBackground();
		
		showForegroundImages();

		//adds user in root node and activeActorManager
		activeActorManager.addFriendlyUnit(user);
		
		return scene;
	}


	private void showBackground(){
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);
		root.getChildren().add(background);
	}

	private void showForegroundImages() {
		//displays general graphics relevant for all levels
		levelView.showHeartDisplay();
		//displays level specific images
		levelView.addImagesToRoot();
	}

	



	public void startGame() {
		background.requestFocus();
		timeline.play();
	}

	public void goToNextLevel(String levelName) {
		timeline.stop();
		timeline.getKeyFrames().clear();

		//removes all nodes from root and suggests garbage collection
		//since all elements in levels are rendered upon load, don't need anything from previous level
		activeActorManager.clearAllActors();
		setChanged();
		notifyObservers(levelName);
	}

	private void updateScene() {
		spawnEnemyUnits();
		activeActorManager.updateActors();
		activeActorManager.generateEnemyFire();
		handleEnemyPenetration();
		handleUserProjectileCollisions();
		handleEnemyProjectileCollisions();
		handlePlaneCollisions();
		handleProjectileOutOfBounds();
		activeActorManager.removeAllDestroyedActors();
		updateLevelView();
		checkIfGameOver();
	}

	



	private void handlePlaneCollisions() {
		handleCollisions(activeActorManager.getFriendlyUnits(), activeActorManager.getEnemyUnits());
	}

	private void handleUserProjectileCollisions() {
		handleCollisions(activeActorManager.getUserProjectiles(), activeActorManager.getEnemyUnits());
	}

	private void handleEnemyProjectileCollisions() {
		handleCollisions(activeActorManager.getEnemyProjectiles(), activeActorManager.getFriendlyUnits());
	}

	private void handleCollisions(List<ActiveActorDestructible> actors1,
			List<ActiveActorDestructible> actors2) {
		for (ActiveActorDestructible actor : actors2) {
			for (ActiveActorDestructible otherActor : actors1) {
				if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent()) && !actor.isDestroyed()
						&& !otherActor.isDestroyed()) {
					actor.takeDamage();
					otherActor.takeDamage();
					
					if( ( actor.canScoreFromCollision() || otherActor.canScoreFromCollision() ) && actor.isDestroyed() && otherActor.isDestroyed() ) {
						updateKillCount();
					}

				}
			}
		}
	}

	private void handleEnemyPenetration() {
		for (ActiveActorDestructible enemy : activeActorManager.getEnemyUnits()) {
			if (enemyHasPenetratedDefenses(enemy)) {
				user.takeDamage();
				enemy.destroy();
			}
		}
	}


	//loops through all the projectiles generated, and checks if they are out of bounds
	//if yes then flag them as destroyed for removal (removeDestroyedActors method will remove all destroyed objects)
	private void handleProjectileOutOfBounds() {
		for (ActiveActorDestructible projectile : activeActorManager.getUserProjectiles()) {
			destroyOutofBoundsProjectile(projectile);
		};
		for (ActiveActorDestructible projectile : activeActorManager.getEnemyProjectiles()) {
			destroyOutofBoundsProjectile(projectile);
		};
	}

	//set projectile as destroyed if it is out of screen
	private void destroyOutofBoundsProjectile(ActiveActorDestructible projectile) {
		if (projectileIsOutOfScreen(projectile)) {
			projectile.destroy();
		}
	}

	//check if the projectile is out of the screen
	private boolean projectileIsOutOfScreen(ActiveActorDestructible projectile) {
		return isOutOfScreen(projectile);
	}


	private void updateLevelView() {
		levelView.removeHearts(user.getHealth());
	}

	private void updateKillCount() {
		killCount++;
	}

	protected int getKillCount() {
		return killCount;
	}

	private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
		return isOutOfScreen(enemy);
	}

	private boolean isOutOfScreen(ActiveActorDestructible actor) {
		return actor.getLayoutX() + actor.getTranslateX() > screenWidth + actor.getBoundsInParent().getWidth() || actor.getLayoutX() + actor.getTranslateX() < 0 - actor.getBoundsInParent().getWidth();
	}


	protected void winGame() {
		timeline.stop();
		levelView.showWinImage();
	}

	protected void loseGame() {
		timeline.stop();
		levelView.showGameOverImage();
	}

	protected UserPlane getUser() {
		return user;
	}

	protected Group getRoot() {
		return root;
	}

	protected int getCurrentNumberOfEnemies() {
		return activeActorManager.getEnemyUnits().size();
	}


	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	protected double getScreenWidth() {
		return screenWidth;
	}

	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}


}
