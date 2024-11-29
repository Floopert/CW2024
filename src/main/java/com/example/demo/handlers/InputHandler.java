package com.example.demo.handlers;

import com.example.demo.activeActors.planes.UserPlane;
import com.example.demo.eventListeners.InputEventListener;
import com.example.demo.levels.LevelParent;

import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;

/**
 * InputHandler Class
 * This class is responsible for handling user input and updating the UserPlane object accordingly.
 */
public class InputHandler {
    
    private final UserPlane user;
    private final ImageView inputHandler;

	//private final List<InputEventListener> listeners = new ArrayList<InputEventListener>();
	private InputEventListener activeActorManagerListener;
	private InputEventListener levelParentListener;

	/**
	 * Constructor for InputHandler Class
	 * @param activeActorManager
	 * @param inputHandler
	 * @param user
	 */
    public InputHandler(ImageView inputHandler, UserPlane user) {
        this.user = user;
        this.inputHandler = inputHandler;
    }


    public void initializeUserControls() {
		inputHandler.setFocusTraversable(true);
		inputHandler.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				KeyCode kc = e.getCode();
				if (kc == KeyCode.UP) user.moveUp();
				if (kc == KeyCode.DOWN) user.moveDown();
				if (kc == KeyCode.LEFT) user.moveLeft();
				if (kc == KeyCode.RIGHT) user.moveRight();
				if (kc == KeyCode.SPACE) activeActorManagerListener.fireProjectile();
				if (kc == KeyCode.ESCAPE) levelParentListener.pauseGame();
			}
		});
		inputHandler.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				KeyCode kc = e.getCode();
				if (kc == KeyCode.UP || kc == KeyCode.DOWN) user.stopVerticalMovement();
				if (kc == KeyCode.LEFT || kc == KeyCode.RIGHT) user.stopHorizontalMovement();
			}
		});
	}

	public void setActiveActorManagerListener(ActiveActorManager listener) {
		this.activeActorManagerListener = listener;
	}

	public void setLevelParentListener(LevelParent listener) {
		this.levelParentListener = listener;
	}

}
