package com.example.demo.handlers;

import com.example.demo.ActiveActorDestructible;
import com.example.demo.UserPlane;

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
    private final ActiveActorManager activeActorManager;


	/**
	 * Constructor for InputHandler Class
	 * @param activeActorManager
	 * @param inputHandler
	 * @param user
	 */
    public InputHandler(ActiveActorManager activeActorManager, ImageView inputHandler, UserPlane user) {
        this.user = user;
        this.inputHandler = inputHandler;
        this.activeActorManager = activeActorManager;
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
				if (kc == KeyCode.SPACE) fireProjectile();
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

    private void fireProjectile() {
		ActiveActorDestructible projectile = user.fireProjectile();
		activeActorManager.addUserProjectile(projectile);
	}

}
