package com.example.demo;

import java.util.*;

import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.util.Duration;

import com.example.demo.eventListeners.CollisionEventListener;
import com.example.demo.handlers.*;

/**
 * LevelParent class
 */
public abstract class LevelParent extends Observable implements CollisionEventListener{


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
	private CollisionHandler collisionHandler;
	
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
	

	
	//-----------------------------------------------------------------------------------//

	/** 
	 * This method attaches the ActiveActorManager (for managing all active actor actions) & InputHandler (for managing user input)
	 * 
	 * This method also attaches all the graphical elements of a level
	 * e.g. Background, user plane, heart display, and any other level specific graphics (such as shield)
	*/
	public Scene initializeScene() {
		
		//attach activeActorManager and inputHandler to scene
		activeActorManager = new ActiveActorManager(root);
		inputHandler = new InputHandler(background, user);
		collisionHandler = new CollisionHandler(screenWidth, activeActorManager);

		inputHandler.initializeUserControls();
		inputHandler.addEventListener(activeActorManager);

		collisionHandler.addEventListener(this);

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


	//-----------------------------------------------------------------------------------//

	public void startGame() {
		background.requestFocus();
		timeline.play();
	}

	

	private void updateScene() {
		spawnEnemyUnits();
		activeActorManager.updateActors();
		activeActorManager.generateEnemyFire();
		collisionHandler.handleEnemyPenetration();
		collisionHandler.handleUserProjectileCollisions();
		collisionHandler.handleEnemyProjectileCollisions();
		collisionHandler.handlePlaneCollisions();
		collisionHandler.handleProjectileOutOfBounds();
		activeActorManager.removeAllDestroyedActors();
		updateLevelView();
		checkIfGameOver();
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

	
	//----------------------------------------------------
	//------------Scene graphics related functions------------
	

	protected Group getRoot() {
		return root;
	}

	protected void winGame() {
		timeline.stop();
		levelView.showWinImage();
	}

	protected void loseGame() {
		timeline.stop();
		levelView.showGameOverImage();
	}

	private void updateLevelView() {
		levelView.removeHearts(user.getHealth());
	}
	


	//----------------------------------------------
	//------------Game state related functions------------

	@Override
	public void updateKillCount() {
		killCount++;
	}


	protected int getKillCount() {
		return killCount;
	}


	protected int getCurrentNumberOfEnemies() {
		return activeActorManager.getEnemyUnits().size();
	}


	//--------------------------------------------------
	//------------Screen boundary related functions------------

	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	protected double getScreenWidth() {
		return screenWidth;
	}


	//----------------------------------------------
	//------------User related functions------------

	protected UserPlane getUser() {
		return user;
	}

	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}

	@Override
	public void userDamaged(){
		user.takeDamage();
	}


}
