package com.example.demo.imageObjects.hud;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Represents a display in the HUD to show the number of waves left.
 */
public class WavesLeftDisplay {
    
    private double containerXPosition;
    private double containerYPosition;
    private final String LABEL_TEXT_STYLE = "-fx-font-size: 40px; -fx-font-weight: bold;";
    private final Label waveLeftLabel = new Label("Waves Left: ");
    private final Label wavesRemaining;
    private HBox container;



    /**
     * Constructs a new WavesLeftDisplay with the specified position and initial waves count.
     *
     * @param xPosition the x-coordinate of the waves left display container
     * @param yPosition the y-coordinate of the waves left display container
     * @param wavesCount the initial number of waves to display
     */
    public WavesLeftDisplay(double xPosition, double yPosition, int wavesCount) {
        this.containerXPosition = xPosition;
        this.containerYPosition = yPosition;

        wavesRemaining = new Label(Integer.toString(wavesCount));

        waveLeftLabel.setStyle(LABEL_TEXT_STYLE);
        wavesRemaining.setStyle(LABEL_TEXT_STYLE);

        initializeContainer();
    }


    /**
     * Initializes the container for the 'waves left' display.
     */
    private void initializeContainer() {
        container = new HBox();
        container.setLayoutX(containerXPosition);
        container.setLayoutY(containerYPosition);		
        container.getChildren().addAll(waveLeftLabel, wavesRemaining);
    }


    /**
     * Updates the number of waves remaining displayed on the 'waves left' display.
     *
     * @param wavesCount the new number of waves to display
     */
    public void updateRemainingWaves(int wavesCount) {
        wavesRemaining.setText(Integer.toString(wavesCount));
    }


    /**
     * Gets the container for the 'waves left' display.
     *
     * @return the container for the 'waves left' display
     */
    public HBox getContainer() {
        return container;
    }

    
}