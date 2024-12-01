package com.example.demo.handlers;

import java.util.*;
import java.util.stream.Collectors;

import com.example.demo.activeActors.ActiveActorDestructible;
import com.example.demo.activeActors.FighterPlane;
import com.example.demo.activeActors.planes.EnemyPlaneParent;
import com.example.demo.activeActors.planes.UserPlane;
import com.example.demo.activeActors.powerUpTypes.HeartUp;
import com.example.demo.activeActors.powerUpTypes.ProjectileUp;
import com.example.demo.eventListeners.DropsEventListener;
import com.example.demo.eventListeners.InputEventListener;

import javafx.scene.Group;


/**
 * This is a class that manages all the active actors in the game.
 * All addition and removals of active actors are done in this class.
 * This class also is in charge for all actions that need to be done on all active actors.
 */
public class ActiveActorManager implements InputEventListener, DropsEventListener{
    
	private final List<ActiveActorDestructible> friendlyUnits;
	private final List<ActiveActorDestructible> enemyUnits;
	private final List<ActiveActorDestructible> userProjectiles;
	private final List<ActiveActorDestructible> enemyProjectiles;
	private final List<ActiveActorDestructible> powerUps;

    private final Group root;


	/**
	 * Constructor for ActiveActorManager Class
	 * @param root
	 * This is the root group of the scene, where all the active actors are added to so that they are showing in the scene.
	 */
    public ActiveActorManager(Group root) {
        friendlyUnits = new ArrayList<>();
        enemyUnits = new ArrayList<>();
        userProjectiles = new ArrayList<>();
        enemyProjectiles = new ArrayList<>();
		powerUps = new ArrayList<>();

        this.root = root;
    }

	

    //---------------------------------------------------------
    //-------------------------GETTERS-------------------------

    public List<ActiveActorDestructible> getFriendlyUnits() {
        return friendlyUnits;
    }

    public List<ActiveActorDestructible> getEnemyUnits() {
        return enemyUnits;
    }

    public List<ActiveActorDestructible> getUserProjectiles() {
        return userProjectiles;
    }

    public List<ActiveActorDestructible> getEnemyProjectiles() {
        return enemyProjectiles;
    }

	public List<ActiveActorDestructible> getPowerUps() {
		return powerUps;
	}

    //---------------------------------------------------------
    //-------------------------SETTERS-------------------------
    


	public void addFriendlyUnit(ActiveActorDestructible unit) {
		friendlyUnits.add(unit);
        root.getChildren().add(unit);
	}

	public void addEnemyUnit(ActiveActorDestructible enemy) {
		enemyUnits.add(enemy);

		if (enemy instanceof EnemyPlaneParent) {
			((EnemyPlaneParent) enemy).addEventListener(this);
		}

		root.getChildren().add(enemy);
	}

    public void addUserProjectile(ActiveActorDestructible projectile){
        userProjectiles.add(projectile);
        root.getChildren().add(projectile);
    }

	public void generateEnemyFire() {
		enemyUnits.forEach(enemy -> addEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
	}

	private void addEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			enemyProjectiles.add(projectile);
            root.getChildren().add(projectile);
		}
	}

	public void addPowerUp(ActiveActorDestructible powerUp) {
		this.powerUps.add(powerUp);
		root.getChildren().add(powerUp);
	}


	//---------------------------------------------------------
    //functions to handle all the active actors in the game


	public void updateActors() {
		friendlyUnits.forEach(plane -> plane.updateActor());
		enemyUnits.forEach(enemy -> enemy.updateActor());
		userProjectiles.forEach(projectile -> projectile.updateActor());
		enemyProjectiles.forEach(projectile -> projectile.updateActor());
		powerUps.forEach(powerUp -> powerUp.updateActor());
	}

	@Override
	public void fireProjectile() {
		for (ActiveActorDestructible plane : friendlyUnits) {
			UserPlane userPlane = (UserPlane) plane;
			ActiveActorDestructible projectile = userPlane.fireProjectile();
			addUserProjectile(projectile);
		}
	}


	public void removeAllDestroyedActors() {
		removeDestroyedActors(friendlyUnits);
		removeDestroyedActors(enemyUnits);
		removeDestroyedActors(userProjectiles);
		removeDestroyedActors(enemyProjectiles);
		removeDestroyedActors(powerUps);
	}

	private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
		List<ActiveActorDestructible> destroyedActors = actors.stream().filter(actor -> actor.isDestroyed())
				.collect(Collectors.toList());
		root.getChildren().removeAll(destroyedActors);
		actors.removeAll(destroyedActors);
		
		destroyedActors.clear();
	}

	//called when moving to next level, to clear all objects from all the array lists
	public void clearAllActors() {
		root.getChildren().clear();
		friendlyUnits.clear();
		enemyUnits.clear();
		userProjectiles.clear();
		enemyProjectiles.clear();
		powerUps.clear();
		System.gc();
	}



	//---------------------------------------------------------
	//functions to handle power up drops

	@Override
	public void spawnHeartPowerUp(double initialXPos, double initialYPos) {
		ActiveActorDestructible powerUp = new HeartUp(initialXPos, initialYPos);
		addPowerUp(powerUp);
	}

	@Override
	public void spawnProjectilePowerUp(double initialXPos, double initialYPos) {
		ActiveActorDestructible powerUp = new ProjectileUp(initialXPos, initialYPos);
		addPowerUp(powerUp);
	}

}
