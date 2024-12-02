package com.example.demo.imageObjects.hud;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class WavesLeftDisplay {
    
    private double containerXPosition;
    private double containerYPosition;
    private final String LABEL_TEXT_STYLE = "-fx-font-size: 40px; -fx-font-weight: bold;";
    private final Label waveLeftLabel = new Label("Waves Left: ");
    private final Label wavesRemaining;
    

    private HBox container;

    public WavesLeftDisplay(double xPosition, double yPosition, int wavesCount) {
        this.containerXPosition = xPosition;
        this.containerYPosition = yPosition;

        wavesRemaining = new Label(Integer.toString(wavesCount));

        waveLeftLabel.setStyle(LABEL_TEXT_STYLE);
        wavesRemaining.setStyle(LABEL_TEXT_STYLE);

        initializeContainer();
    }

    private void initializeContainer() {
		container = new HBox();
		container.setLayoutX(containerXPosition);
		container.setLayoutY(containerYPosition);		
        container.getChildren().addAll(waveLeftLabel, wavesRemaining);
	}

    public void updateRemainingWaves(int wavesCount) {
        wavesRemaining.setText(Integer.toString(wavesCount));
	}
	
	public HBox getContainer() {
		return container;
	}

}
