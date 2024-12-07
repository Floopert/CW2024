package com.example.demo.handlers;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.activeActors.ActiveActorDestructible;
import com.example.demo.activeActors.FighterPlane;
import com.example.demo.eventListeners.CollisionEventListener;

/**
 * Handles collisions and boundary checks for all active actors in the game.
 */
public class CollisionHandler {
    
    private List<CollisionEventListener> listeners = new ArrayList<CollisionEventListener>();
    private final double screenWidth;
    private final ActiveActorManager activeActorManager;

	
    /**
     * Constructs a new CollisionHandler with the specified screen width and active actor manager.
     *
     * @param screenWidth the width of the screen
     * @param activeActorManager the manager for all active actors in the game
     */
    public CollisionHandler(double screenWidth, ActiveActorManager activeActorManager) {
        this.screenWidth = screenWidth;
        this.activeActorManager = activeActorManager;
    }


    /**
     * Adds a collision event listener.
     *
     * @param listener the collision event listener to be added
     */
    public void addEventListener(CollisionEventListener listener) {
        listeners.add(listener);
    }


    /**
     * Handles all bounds and collisions for active actors.
     */
    public void handleAllBoundsAndCollisions() {
        handleEnemyPenetration();
        handleUserProjectileCollisions();
        handleEnemyProjectileCollisions();
        handlePlaneCollisions();
        handleObjectsOutOfBounds();
        handlePowerUpCollisions();
    }

    /**
     * Handles collisions between friendly units and enemy units.
     */
    public void handlePlaneCollisions() {
        handleCollisions(activeActorManager.getFriendlyUnits(), activeActorManager.getEnemyUnits());
    }

    /**
     * Handles collisions between user projectiles and enemy units.
     */
    public void handleUserProjectileCollisions() {
        handleCollisions(activeActorManager.getUserProjectiles(), activeActorManager.getEnemyUnits());
    }

    /**
     * Handles collisions between enemy projectiles and friendly units.
     */
    public void handleEnemyProjectileCollisions() {
        handleCollisions(activeActorManager.getEnemyProjectiles(), activeActorManager.getFriendlyUnits());
    }


    /**
     * Handles collisions between power-ups and friendly units.
     */
    public void handlePowerUpCollisions() {
        handleCollisions(activeActorManager.getPowerUps(), activeActorManager.getFriendlyUnits());
    }


    /**
     * Handles enemy penetration by checking if enemies have penetrated defenses.
	 * If an enemy has penetrated defenses, the user is damaged and the enemy is destroyed.
     */
    public void handleEnemyPenetration() {
        for (ActiveActorDestructible enemy : activeActorManager.getEnemyUnits()) {
            if (enemyHasPenetratedDefenses(enemy)) {
                for (CollisionEventListener listener : listeners) {
                    listener.userDamaged(enemy.getDamageOutput());
                }
                enemy.destroy();
            }
        }
    }


    /**
     * Handles ALL non-plane objects that are out of bounds by flagging them as destroyed.
     */
    public void handleObjectsOutOfBounds() {
        for (ActiveActorDestructible projectile : activeActorManager.getUserProjectiles()) {
            destroyOutofBoundsObjects(projectile);
        }
        for (ActiveActorDestructible projectile : activeActorManager.getEnemyProjectiles()) {
            destroyOutofBoundsObjects(projectile);
        }
        for (ActiveActorDestructible powerUp : activeActorManager.getPowerUps()) {
            destroyOutofBoundsObjects(powerUp);
        }
    }

    

    /**
     * Handles collisions between two lists of actors.
     *
     * @param actors1 the first list of actors
     * @param actors2 the second list of actors
     */
    public void handleCollisions(List<ActiveActorDestructible> actors1, List<ActiveActorDestructible> actors2) {
        
		for (ActiveActorDestructible actor : actors2) {
            
			for (ActiveActorDestructible otherActor : actors1) {
                if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent()) && !actor.isDestroyed()
                        && !otherActor.isDestroyed()) {
                    actor.takeDamage(otherActor.getDamageOutput());
                    otherActor.takeDamage(actor.getDamageOutput());
                    
                    if ((actor.canScoreFromCollision() || otherActor.canScoreFromCollision()) && actor.isDestroyed() && otherActor.isDestroyed()) {
                        for (CollisionEventListener listener : listeners) {
                            int score;
                            if (actor instanceof FighterPlane) {
                                score = ((FighterPlane) actor).getScoreValue();
                            } else {
                                score = ((FighterPlane) otherActor).getScoreValue();
                            }
                            listener.addScore(score);
                        }
                    }
                }
            }

        }

    }



    /**
     * Checks if an actor is out of screen bounds.
     *
     * @param actor the actor to be checked
     * @return true if the actor is out of screen bounds, false otherwise
     */
    public boolean isOutOfScreen(ActiveActorDestructible actor) {
        return actor.getLayoutX() + actor.getTranslateX() > screenWidth + actor.getBoundsInParent().getWidth() || actor.getLayoutX() + actor.getTranslateX() < -actor.getBoundsInParent().getWidth();
    }



//--------------------------------- PRIVATE HELPER METHODS ---------------------------------//

	/**
     * Flags an object as destroyed if it is out of screen bounds.
     *
     * @param object the object to be checked and potentially destroyed
     */
    private void destroyOutofBoundsObjects(ActiveActorDestructible object) {
        if (isOutOfScreen(object)) {
            object.destroy();
        }
    }

    /**
     * Checks if an enemy has penetrated defenses by being out of screen bounds.
     *
     * @param enemy the enemy to be checked
     * @return true if the enemy has penetrated defenses, false otherwise
     */
    private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
        return isOutOfScreen(enemy);
    }


}