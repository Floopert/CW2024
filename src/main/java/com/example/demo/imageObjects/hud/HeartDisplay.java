package com.example.demo.imageObjects.hud;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a display of hearts in the HUD to show the player's health.
 */
public class HeartDisplay {
    
    private static final String HEART_IMAGE_NAME = "/com/example/demo/images/heart.png";
    private static final int HEART_HEIGHT = 50;
    private static final int INDEX_OF_FIRST_ITEM = 0;
    private HBox container;
    private double containerXPosition;
    private double containerYPosition;
    private int numberOfHeartsToDisplay;
    

    /**
     * Constructs a new HeartDisplay with the specified position and initial number of hearts to display.
     *
     * @param xPosition the x-coordinate of the heart display container
     * @param yPosition the y-coordinate of the heart display container
     * @param heartsToDisplay the initial number of hearts to display
     */
    public HeartDisplay(double xPosition, double yPosition, int heartsToDisplay) {
        this.containerXPosition = xPosition;
        this.containerYPosition = yPosition;
        this.numberOfHeartsToDisplay = heartsToDisplay;
        initializeContainer();
        initializeHearts();
    }
    
    
    
    /**
     * Removes a heart from the display.
     */
    public void removeHeart() {
        if (!container.getChildren().isEmpty())
            container.getChildren().remove(INDEX_OF_FIRST_ITEM);
    }



    /**
     * Adds a heart to the display.
     */
    public void addHeart() {
        container.getChildren().add(instantiateHeartImage());
    }
    

    /**
     * Gets the container for the heart display.
     *
     * @return the container for the heart display
     */
    public HBox getContainer() {
        return container;
    }




//-----------------------------------------------------------------------------------------
//----------------------------PRIVATE HELPER METHODS---------------------------------------


	/**
     * Initializes the container for the heart display.
     */
    private void initializeContainer() {
        container = new HBox();
        container.setLayoutX(containerXPosition);
        container.setLayoutY(containerYPosition);		
    }
    
    /**
     * Instantiates a new heart image.
     *
     * @return the instantiated heart image
     */
    private ImageView instantiateHeartImage() {
        ImageView heart = new ImageView(new Image(getClass().getResource(HEART_IMAGE_NAME).toExternalForm()));
        heart.setFitHeight(HEART_HEIGHT);
        heart.setPreserveRatio(true);
        return heart;
    }

    /**
     * Initializes the hearts in the container.
     */
    private void initializeHearts() {
        for (int i = 0; i < numberOfHeartsToDisplay; i++) {
            container.getChildren().add(instantiateHeartImage());
        }
    }

	
}