package com.example.demo.handlers;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.activeActors.ActiveActorDestructible;
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



	public void handlePlaneCollisions() {
		handleCollisions(activeActorManager.getFriendlyUnits(), activeActorManager.getEnemyUnits());
	}

	public void handleUserProjectileCollisions() {
		handleCollisions(activeActorManager.getUserProjectiles(), activeActorManager.getEnemyUnits());
	}

	public void handleEnemyProjectileCollisions() {
		handleCollisions(activeActorManager.getEnemyProjectiles(), activeActorManager.getFriendlyUnits());
	}

	

	public void handleEnemyPenetration() {
		for (ActiveActorDestructible enemy : activeActorManager.getEnemyUnits()) {
			if (enemyHasPenetratedDefenses(enemy)) {
				for (CollisionEventListener listener : listeners) {
                    listener.userDamaged();
                }
				enemy.destroy();
			}
		}
	}


	//loops through all the projectiles generated, and checks if they are out of bounds
	//if yes then flag them as destroyed for removal (removeDestroyedActors method will remove all destroyed objects)
	public void handleProjectileOutOfBounds() {
		for (ActiveActorDestructible projectile : activeActorManager.getUserProjectiles()) {
			destroyOutofBoundsProjectile(projectile);
		};
		for (ActiveActorDestructible projectile : activeActorManager.getEnemyProjectiles()) {
			destroyOutofBoundsProjectile(projectile);
		};
	}

	//set projectile as destroyed if it is out of screen
	private void destroyOutofBoundsProjectile(ActiveActorDestructible projectile) {
		if (isOutOfScreen(projectile)) {
			projectile.destroy();
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
					actor.takeDamage();
					otherActor.takeDamage();
					
					if( ( actor.canScoreFromCollision() || otherActor.canScoreFromCollision() ) && actor.isDestroyed() && otherActor.isDestroyed() ) {
						for (CollisionEventListener listener : listeners) {
                            listener.updateKillCount();
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
