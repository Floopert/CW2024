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
 * Represents a parent class for all levels in the game.
 */
public abstract class LevelParent implements CollisionEventListener, InputEventListener {

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
    private static int initialScore = 0;

    private LevelEventListener eventListener;

    //-----------------Abstract methods to be implemented by subclasses-----------------//
    /**
     * Checks if the game is over.
     */
    protected abstract void checkIfGameOver();

    /**
     * Spawns enemy units.
     */
    protected abstract void spawnEnemyUnits();

    /**
     * Instantiates the level view.
     */
    protected abstract void instantiateLevelView();

    /**
     * Destroys the instance of the level.
     */
    public abstract void destroyInstance();

    //-----------------------------------------------------------------------------------//

    /**
     * Constructs a new LevelParent with the specified screen dimensions and current level.
     *
     * @param screenHeight the height of the screen
     * @param screenWidth the width of the screen
     * @param currentLevel the class path of the current level
     */
    public LevelParent(double screenHeight, double screenWidth, String currentLevel) {
        LevelParent.currentScore = LevelParent.initialScore;
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

    /**
     * Initializes the timeline for the game loop.
     */
    private void initializeTimeline() {
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
        timeline.getKeyFrames().add(gameLoop);
    }

    /**
     * Initializes the scene by attaching the ActiveActorManager and InputHandler, and setting up the graphical elements.
     *
     * @return the initialized scene
     */
    public Scene initializeScene() {
        instantiateLevelView();
        
        // Attach activeActorManager and inputHandler to scene
        activeActorManager = new ActiveActorManager(root);
        inputHandler = new InputHandler(background, user);
        collisionHandler = new CollisionHandler(screenWidth, activeActorManager);

        inputHandler.initializeUserControls();
        inputHandler.setActiveActorManagerListener(activeActorManager);
        inputHandler.setLevelParentListener(this);
        collisionHandler.addEventListener(this);

        // Sets background height and width, then add into root node for rendering
        showBackground();
        showForegroundImages();

        // Adds user in root node and activeActorManager
        activeActorManager.addFriendlyUnit(user);
        
        return scene;
    }

    /**
     * Shows the background image.
     */
    private void showBackground() {
        background.setFitHeight(screenHeight);
        background.setFitWidth(screenWidth);
        root.getChildren().add(background);
    }

    /**
     * Shows the foreground images e.g. HUD, level-specific images.
     */
    private void showForegroundImages() {
        // Displays general graphics relevant for all levels
        levelView.showHud();
        // Displays level-specific images
        levelView.addImagesToRoot();
    }


    /**
     * Gets the current level's scene.
     *
     * @return the scene
     */
    public Scene getScene() {
        return scene;
    }


    /**
     * Updates the scene by spawning enemy units, updating actors, handling collisions, and checking if the game is over.
     */
    private void updateScene() {
        spawnEnemyUnits();
        activeActorManager.updateActors();
        activeActorManager.generateEnemyFire();
        collisionHandler.handleAllBoundsAndCollisions();
        activeActorManager.removeAllDestroyedActors();
        updateLevelView();
        checkIfGameOver();
    }

    /**
     * Adds a level event listener. Allows a class to subscribe to level events e.g. if a level change is triggered.
     *
     * @param listener the level event listener to be added
     */
    public void addEventListener(LevelEventListener listener) {
        eventListener = listener;
    }

    /**
     * Removes a level event listener.
     *
     * @param listener the level event listener to be removed
     */
    public void removeEventListener(LevelEventListener listener) {
        eventListener = null;
    }

    /**
     * Gets the root group.
     *
     * @return the root group
     */
    protected Group getRoot() {
        return root;
    }

    /**
     * Updates the level view e.g. update the current hearts to display, current score to display.
     */
    private void updateLevelView() {
        levelView.updateHearts(user.getHealth());
		levelView.updateScore(currentScore);
    }

    /**
     * Starts the game by playing the timeline.
     */
    public void startGame() {
        background.requestFocus();
        timeline.play();
    }

    /**
     * Resumes the game.
     */
    public void resumeGame() {
        timeline.play();
    }

    /**
     * Pauses the game.
     */
    @Override
    public void pauseGame() {
        timeline.pause();

        try {
            eventListener.goToFXML(null, CURRENT_LEVEL, "pause");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Goes to the next level.
     *
     * @param levelName the name of the next level
     */
    public void goToNextLevel(String levelName) {
        LevelParent.initialScore = LevelParent.currentScore;
        timeline.stop();
        timeline.getKeyFrames().clear();

        // Removes all nodes from root and suggests garbage collection
        // Since all elements in levels are rendered upon load, don't need anything from previous level
        activeActorManager.clearAllActors();

        eventListener.changeLevel(this, levelName);
    }

    /**
     * Handles the win game scenario by redirecting user to the win FXML page.
     */
    protected void winGame() {
        timeline.stop();
        activeActorManager.clearAllActors();
        user.destroyInstance();

        try {
            eventListener.goToFXML(this, null, "win");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the lose game scenario by redirecting user to the lose FXML page.
     */
    protected void loseGame() {
        timeline.stop();
        activeActorManager.clearAllActors();
        user.destroyInstance();
        
        try {
            eventListener.goToFXML(this, CURRENT_LEVEL, "gameOver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Increases the current score.
     *
     * @param score the score to be added
     */
    @Override
    public void addScore(int score) {
        currentScore += score;
    }

    /**
     * Gets the current number of enemies shown on screen.
     *
     * @return the current number of enemies
     */
    protected int getCurrentNumberOfEnemies() {
        return activeActorManager.getEnemyUnits().size();
    }

    /**
     * Gets the current score.
     *
     * @return the current score
     */
    public static int getCurrentScore() {
        return currentScore;
    }

    /**
     * Resets the score.
     */
    public static void resetScore() {
        initialScore = 0;
    }

    /**
     * Gets the maximum Y position for enemies.
     *
     * @return the maximum Y position for enemies
     */
    protected double getEnemyMaximumYPosition() {
        return enemyMaximumYPosition;
    }

    /**
     * Gets the screen width.
     *
     * @return the screen width
     */
    protected double getScreenWidth() {
        return screenWidth;
    }

    /**
     * Gets the user plane.
     *
     * @return the user plane
     */
    protected UserPlane getUser() {
        return user;
    }

    /**
     * Checks if the user is destroyed.
     *
     * @return true if the user is destroyed, false otherwise
     */
    protected boolean userIsDestroyed() {
        return user.isDestroyed();
    }

    /**
     * Reduces the user's health by a specified amount.
     *
     * @param damage the amount of damage taken by the user
     */
    @Override
    public void userDamaged(int damage) {
        user.takeDamage(damage);
    }
}