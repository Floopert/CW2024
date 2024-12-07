package com.example.demo.levelViews;

import com.example.demo.imageObjects.effectsImages.ShieldImage;

import javafx.scene.Group;

/**
 * Represents the view for Level Two specific images, including the shield image.
 */
public class LevelViewLevelTwo extends LevelViewParent {

    private final Group root;
    private ShieldImage shieldImage;
    
    /**
     * Constructs a new LevelViewLevelTwo with the specified root, hearts to display, and score.
     *
     * @param root the root group for the level view
     * @param heartsToDisplay the number of hearts to display
     * @param score the initial score to display
     */
    public LevelViewLevelTwo(Group root, int heartsToDisplay, int score) {
        super(root, heartsToDisplay, score);
        this.root = root;
    }
    
    /**
     * Instantiates the shield image at the specified position.
     *
     * @param shieldXPosition the x-coordinate of the shield image
     * @param shieldYPosition the y-coordinate of the shield image
     */
    public void instantiateShield(double shieldXPosition, double shieldYPosition) {
        this.shieldImage = new ShieldImage(shieldXPosition, shieldYPosition);
    }

    /**
     * Adds the level-specific images to the root group.
     */
    @Override
    public void addImagesToRoot() {
        root.getChildren().addAll(shieldImage);
    }
    
    /**
     * Shows the shield image on screen.
     */
    public void showShield() {
        shieldImage.showShield();
    }

    /**
     * Hides the shield image on screen.
     */
    public void hideShield() {
        shieldImage.hideShield();
    }

    /**
     * Updates the position of the shield image.
     *
     * @param yPosition the new y-coordinate position of the shield image
     */
    public void updateShieldPosition(double yPosition) {
        shieldImage.setTranslateY(yPosition);
    }
}