package com.example.demo.levels;


import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.util.Duration;

import com.example.demo.activeActors.planes.UserPlane;
import com.example.demo.eventListeners.CollisionEventListener;
import com.example.demo.eventListeners.InputEventListener;
import com.example.demo.eventListeners.LevelEventListener;
import com.example.demo.handlers.*;
import com.example.demo.levelViews.LevelViewParent;

/**
 * LevelParent class
 */
public abstract class LevelParent implements CollisionEventListener, InputEventListener{


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
	protected LevelViewParent levelView;
	private final String CURRENT_LEVEL;

	protected ActiveActorManager activeActorManager;
	private InputHandler inputHandler;
	private CollisionHandler collisionHandler;
	
	private static int currentScore = 0;

	private LevelEventListener eventListener;


	//-----------------Abstract methods to be implemented by subclasses-----------------//
	protected abstract void checkIfGameOver();

	protected abstract void spawnEnemyUnits();

	protected abstract void instantiateLevelView();

	public abstract void destroyInstance();


	//-----------------------------------------------------------------------------------//

	/**
	 * Constructor for LevelParent
	 * @param screenHeight
	 * @param screenWidth
	 * @param playerInitialHealth
	 */
	public LevelParent(double screenHeight, double screenWidth, String currentLevel) {
		
		this.CURRENT_LEVEL = currentLevel;
		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.timeline = new Timeline();
		this.user = UserPlane.getInstance();
		user.resetPosition();

		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;

		
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
		instantiateLevelView();
		
		//attach activeActorManager and inputHandler to scene
		activeActorManager = new ActiveActorManager(root);
		inputHandler = new InputHandler(background, user);
		collisionHandler = new CollisionHandler(screenWidth, activeActorManager);

		inputHandler.initializeUserControls();
		inputHandler.setActiveActorManagerListener(activeActorManager);
		inputHandler.setLevelParentListener(this);
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
		levelView.showHud();
		//displays level specific images
		levelView.addImagesToRoot();
	}

	public Scene getScene() {
		return scene;
	}

	//-----------------------------------------------------------------------------------//
	

	private void updateScene() {
		spawnEnemyUnits();
		activeActorManager.updateActors();
		activeActorManager.generateEnemyFire();
		collisionHandler.handleAllBoundsAndCollisions();
		activeActorManager.removeAllDestroyedActors();
		updateLevelView();
		checkIfGameOver();
	}

	
	public void addEventListener(LevelEventListener listener) {
		eventListener = listener;
	}

	public void removeEventListener(LevelEventListener listener){
		eventListener = null;
	}

	

	
	//----------------------------------------------------
	//------------Scene graphics related functions------------
	

	protected Group getRoot() {
		return root;
	}


	private void updateLevelView() {
		levelView.updateHearts(user.getHealth());
	}
	


	//----------------------------------------------
	//------------Game state related functions------------

	public void startGame() {
		background.requestFocus();
		timeline.play();
	}

	public void resumeGame() {
		timeline.play();
	}

	@Override
	public void pauseGame() {
		timeline.pause();

		try{
			eventListener.goToFXML(null, CURRENT_LEVEL, "pause");
		} catch (Exception e){
			e.printStackTrace();
		}
	}


	public void goToNextLevel(String levelName) {
		timeline.stop();
		timeline.getKeyFrames().clear();

		//removes all nodes from root and suggests garbage collection
		//since all elements in levels are rendered upon load, don't need anything from previous level
		activeActorManager.clearAllActors();

		eventListener.changeLevel(this, levelName);
	}

	protected void winGame() {
		timeline.stop();
		activeActorManager.clearAllActors();
		user.destroyInstance();

		try{
			eventListener.goToFXML(this, null, "win");
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	protected void loseGame() {
		timeline.stop();
		activeActorManager.clearAllActors();
		user.destroyInstance();
		
		try{
			eventListener.goToFXML(this, CURRENT_LEVEL, "gameOver");
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}


	@Override
	public void updateScore(int score){
		currentScore += score;
		levelView.updateScore(currentScore);
	}



	protected int getCurrentNumberOfEnemies() {
		return activeActorManager.getEnemyUnits().size();
	}

	protected int getCurrentScore() {
		return currentScore;
	}

	public static void resetScore(){
		currentScore = 0;
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
	public void userDamaged(int damage){
		user.takeDamage(damage);
	}


}
