package com.example.demo.handlers;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.activeActors.ActiveActorDestructible;
import com.example.demo.activeActors.FighterPlane;
import com.example.demo.eventListeners.CollisionEventListener;

public class CollisionHandler {
    
    private List<CollisionEventListener> listeners = new ArrayList<CollisionEventListener>();
    private final double screenWidth;
    private final ActiveActorManager activeActorManager;

    public CollisionHandler(double screenWidth, ActiveActorManager activeActorManager){
        this.screenWidth = screenWidth;
        this.activeActorManager = activeActorManager;
    }


    public void addEventListener(CollisionEventListener listener) {
		listeners.add(listener);
	}

	public void handleAllBoundsAndCollisions() {
		handleEnemyPenetration();
		handleUserProjectileCollisions();
		handleEnemyProjectileCollisions();
		handlePlaneCollisions();
		handleObjectsOutOfBounds();
		handlePowerUpCollisions();
	}
	

	public void handlePlaneCollisions() {
		handleCollisions(activeActorManager.getFriendlyUnits(), activeActorManager.getEnemyUnits());
	}

	public void handleUserProjectileCollisions() {
		handleCollisions(activeActorManager.getUserProjectiles(), activeActorManager.getEnemyUnits());
	}

	public void handleEnemyProjectileCollisions() {
		handleCollisions(activeActorManager.getEnemyProjectiles(), activeActorManager.getFriendlyUnits());
	}

	public void handlePowerUpCollisions() {
		handleCollisions(activeActorManager.getPowerUps(), activeActorManager.getFriendlyUnits());
	}

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


	//loops through all the projectiles generated, and checks if they are out of bounds
	//if yes then flag them as destroyed for removal (removeDestroyedActors method will remove all destroyed objects)
	public void handleObjectsOutOfBounds() {
		for (ActiveActorDestructible projectile : activeActorManager.getUserProjectiles()) {
			destroyOutofBoundsObjects(projectile);
		};
		for (ActiveActorDestructible projectile : activeActorManager.getEnemyProjectiles()) {
			destroyOutofBoundsObjects(projectile);
		};
		for (ActiveActorDestructible powerUp : activeActorManager.getPowerUps()) {
			destroyOutofBoundsObjects(powerUp);
		};
	}

	//set projectile as destroyed if it is out of screen
	private void destroyOutofBoundsObjects(ActiveActorDestructible objects) {
		if (isOutOfScreen(objects)) {
			objects.destroy();
		}
	}

	private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
		return isOutOfScreen(enemy);
	}






    public void handleCollisions(List<ActiveActorDestructible> actors1, 
                                 List<ActiveActorDestructible> actors2)
    {
		for (ActiveActorDestructible actor : actors2) {
			for (ActiveActorDestructible otherActor : actors1) {
				if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent()) && !actor.isDestroyed()
						&& !otherActor.isDestroyed()) {
					actor.takeDamage(otherActor.getDamageOutput());
					otherActor.takeDamage(actor.getDamageOutput());
					
					if( ( actor.canScoreFromCollision() || otherActor.canScoreFromCollision() ) && actor.isDestroyed() && otherActor.isDestroyed() ) {
						for (CollisionEventListener listener : listeners) {
                            int score;
							if (actor instanceof FighterPlane){
								score = ((FighterPlane) actor).getScoreValue();
							} else {
								score = ((FighterPlane) otherActor).getScoreValue();
							}
							listener.updateScore(score);
                        }
					}

				}
			}
		}
	}


	public boolean isOutOfScreen(ActiveActorDestructible actor) {
		return actor.getLayoutX() + actor.getTranslateX() > screenWidth + actor.getBoundsInParent().getWidth() || actor.getLayoutX() + actor.getTranslateX() < 0 - actor.getBoundsInParent().getWidth();
	}


}
