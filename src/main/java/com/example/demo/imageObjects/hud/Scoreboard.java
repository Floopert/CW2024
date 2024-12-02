package com.example.demo.imageObjects.hud;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class Scoreboard {
    
    private double containerXPosition;
    private double containerYPosition;
    private final String LABEL_TEXT_STYLE = "-fx-font-size: 40px; -fx-font-weight: bold;";
    private final Label scoreLabel = new Label("Score: ");
    private final Label scoreValue;
    

    private HBox container;

    public Scoreboard(double xPosition, double yPosition, int score) {
        this.containerXPosition = xPosition;
        this.containerYPosition = yPosition;

        scoreValue = new Label(Integer.toString(score));

        scoreLabel.setStyle(LABEL_TEXT_STYLE);
        scoreValue.setStyle(LABEL_TEXT_STYLE);

        initializeContainer();
    }

    private void initializeContainer() {
		container = new HBox();
		container.setLayoutX(containerXPosition);
		container.setLayoutY(containerYPosition);		
        container.getChildren().addAll(scoreLabel, scoreValue);
	}

    public void updateScoreValue(int score) {
        scoreValue.setText(Integer.toString(score));
	}
	
	public HBox getContainer() {
		return container;
	}

}
