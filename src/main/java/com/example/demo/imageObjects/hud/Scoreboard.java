package com.example.demo.imageObjects.hud;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Represents a scoreboard in the HUD to show the player's score.
 */
public class Scoreboard {
    
    private double containerXPosition;
    private double containerYPosition;
    private final String LABEL_TEXT_STYLE = "-fx-font-size: 40px; -fx-font-weight: bold;";
    private final Label scoreLabel = new Label("Score: ");
    private final Label scoreValue;
    private HBox container;



    /**
     * Constructs a new Scoreboard with the specified position and initial score.
     *
     * @param xPosition the x-coordinate of the scoreboard container
     * @param yPosition the y-coordinate of the scoreboard container
     * @param score the initial score to display
     */
    public Scoreboard(double xPosition, double yPosition, int score) {
        this.containerXPosition = xPosition;
        this.containerYPosition = yPosition;

        scoreValue = new Label(Integer.toString(score));

        scoreLabel.setStyle(LABEL_TEXT_STYLE);
        scoreValue.setStyle(LABEL_TEXT_STYLE);

        initializeContainer();
    }

    
    /**
     * Initializes the container for the scoreboard.
     */
    private void initializeContainer() {
        container = new HBox();
        container.setLayoutX(containerXPosition);
        container.setLayoutY(containerYPosition);		
        container.getChildren().addAll(scoreLabel, scoreValue);
    }


    /**
     * Updates the score value displayed on the scoreboard.
     *
     * @param score the new score to display
     */
    public void updateScoreValue(int score) {
        scoreValue.setText(Integer.toString(score));
    }


    /**
     * Gets the container for the scoreboard.
     *
     * @return the container for the scoreboard
     */
    public HBox getContainer() {
        return container;
    }


}