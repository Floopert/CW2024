package com.example.demo.handlers;

import java.util.*;
import java.util.stream.Collectors;

import com.example.demo.ActiveActorDestructible;
import com.example.demo.FighterPlane;


import javafx.scene.Group;


/**
 * This is a class that manages all the active actors in the game.
 * All addition and removals of active actors are done in this class.
 * This class also is in charge for all actions that need to be done on all active actors.
 */

public class ActiveActorManager {
    
	private final List<ActiveActorDestructible> friendlyUnits;
	private final List<ActiveActorDestructible> enemyUnits;
	private final List<ActiveActorDestructible> userProjectiles;
	private final List<ActiveActorDestructible> enemyProjectiles;

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


    //---------------------------------------------------------
    //-------------------------SETTERS-------------------------
    


	public void addFriendlyUnit(ActiveActorDestructible unit) {
		friendlyUnits.add(unit);
        root.getChildren().add(unit);
	}

	public void addEnemyUnit(ActiveActorDestructible enemy) {
		enemyUnits.add(enemy);
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


    //---------------------------------------------------------
    //-------------------------REMOVERS-------------------------

    public void removeFriendlyUnit(ActiveActorDestructible unit) {
        friendlyUnits.remove(unit);
    }

    public void removeEnemyUnit(ActiveActorDestructible unit) {
        enemyUnits.remove(unit);
    }

    public void removeUserProjectile(ActiveActorDestructible projectile) {
        userProjectiles.remove(projectile);
    }

    public void removeEnemyProjectile(ActiveActorDestructible projectile) {
        enemyProjectiles.remove(projectile);
    }





	//---------------------------------------------------------
    //functions to handle all the active actors in the game


	public void updateActors() {
		friendlyUnits.forEach(plane -> plane.updateActor());
		enemyUnits.forEach(enemy -> enemy.updateActor());
		userProjectiles.forEach(projectile -> projectile.updateActor());
		enemyProjectiles.forEach(projectile -> projectile.updateActor());
	}

	public void removeAllDestroyedActors() {
		removeDestroyedActors(friendlyUnits);
		removeDestroyedActors(enemyUnits);
		removeDestroyedActors(userProjectiles);
		removeDestroyedActors(enemyProjectiles);
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
		System.gc();
	}

}
