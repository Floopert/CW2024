package com.example.demo.levels;

import com.example.demo.activeActors.planes.bossPlanes.BossT2;
import com.example.demo.eventListeners.BossEventListener;
import com.example.demo.levelViews.LevelViewLevelFive;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents Level Five in the game with a boss enemy.
 */
public class LevelFive extends LevelParent implements BossEventListener {

    private static LevelFive instance;
    private static final String CURRENT_LEVEL = LevelFive.class.getName();
    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background5.jpg";
    private final BossT2 boss;

    private LevelViewLevelFive levelView;

    /**
     * Constructs a new LevelFive with the specified screen dimensions.
     *
     * @param screenHeight the height of the screen
     * @param screenWidth the width of the screen
     */
    private LevelFive(double screenHeight, double screenWidth) {
        super(screenHeight, screenWidth, CURRENT_LEVEL);

        // Background is declared in the superclass, but since it is different for each level, it is initialized here
        background = new ImageView(new Image(getClass().getResource(BACKGROUND_IMAGE_NAME).toExternalForm()));

        boss = new BossT2();
        boss.addEventListener(this);
    }

    /**
     * Gets the singleton instance of LevelFive.
     *
     * @param screenHeight the height of the screen
     * @param screenWidth the width of the screen
     * @return the singleton instance of LevelFive
     */
    public static LevelFive getInstance(double screenHeight, double screenWidth) {
        if (instance == null) {
            instance = new LevelFive(screenHeight, screenWidth);
        }
        return instance;
    }

    /**
     * Destroys the singleton instance of LevelFive.
     */
    public void destroyInstance() {
        instance = null;
    }

    /**
     * Checks if the game is over by determining if the user or the boss is destroyed.
     */
    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (boss.isDestroyed()) {
            winGame();
        }
    }

    /**
     * Spawns enemy units, specifically the boss, if it hasn't been spawned yet.
     */
    @Override
    protected void spawnEnemyUnits() {
        if (getCurrentNumberOfEnemies() == 0) {
            activeActorManager.addEnemyUnit(boss);
        }
    }

    /**
     * Instantiates the level view for Level Five.
     */
    @Override
    protected void instantiateLevelView() {
        // this.levelView is to access level-specific methods to generate level-specific images
        this.levelView = new LevelViewLevelFive(getRoot(), getUser().getHealth(), getCurrentScore());
        this.levelView.instantiateShield(boss.getLayoutX(), boss.getLayoutY());
        // Another reference to levelView is stored in the superclass to access methods that are generated the same for all levels
        super.levelView = this.levelView;
    }


	
    //----------------------------------------------------------------------//
    // -----------------BossEventListener interface methods-----------------//

    /**
     * Called when the boss's shield is activated.
	 * This method will trigger the method in LevelViewLevelFive to show the shield.
     */
    @Override
    public void shieldActivated() {
        instance.levelView.showShield();
    }

    /**
     * Called when the boss's shield is deactivated.
	 * This method will trigger the method in LevelViewLevelFive to hide the shield.
     */
    @Override
    public void shieldDeactivated() {
        instance.levelView.hideShield();
    }

    /**
     * Updates the position of the boss's shield.
     *
     * @param yPosition the new y-coordinate position of the shield
     */
    @Override
    public void updateShieldPosition(double yPosition) {
        instance.levelView.updateShieldPosition(yPosition);
    }
}