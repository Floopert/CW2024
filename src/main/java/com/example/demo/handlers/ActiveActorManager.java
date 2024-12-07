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

	/**
     * Gets the list of friendly units.
     *
     * @return the list of friendly units
     */
    public List<ActiveActorDestructible> getFriendlyUnits() {
        return friendlyUnits;
    }

	/**
     * Gets the list of enemy units.
     *
     * @return the list of enemy units
     */
    public List<ActiveActorDestructible> getEnemyUnits() {
        return enemyUnits;
    }

    /**
     * Gets the list of user projectiles.
     *
     * @return the list of user projectiles
     */
    public List<ActiveActorDestructible> getUserProjectiles() {
        return userProjectiles;
    }

    /**
     * Gets the list of enemy projectiles.
     *
     * @return the list of enemy projectiles
     */
    public List<ActiveActorDestructible> getEnemyProjectiles() {
        return enemyProjectiles;
    }

    /**
     * Gets the list of power-ups.
     *
     * @return the list of power-ups
     */
    public List<ActiveActorDestructible> getPowerUps() {
        return powerUps;
    }

    //---------------------------------------------------------
    //-------------------------SETTERS-------------------------
    


	/**
     * Adds a friendly unit to the list and to the scene.
     *
     * @param unit the friendly unit to be added
     */
    public void addFriendlyUnit(ActiveActorDestructible unit) {
        friendlyUnits.add(unit);
        root.getChildren().add(unit);
    }

    /**
     * Adds an enemy unit to the list and to the scene.
     *
     * @param enemy the enemy unit to be added
     */
    public void addEnemyUnit(ActiveActorDestructible enemy) {
        enemyUnits.add(enemy);

        if (enemy instanceof EnemyPlaneParent) {
            ((EnemyPlaneParent) enemy).addEventListener(this);
        }

        root.getChildren().add(enemy);
    }

    /**
     * Adds a user projectile to the list and to the scene.
     *
     * @param projectile the user projectile to be added
     */
    public void addUserProjectile(ActiveActorDestructible projectile){
        userProjectiles.add(projectile);
        root.getChildren().add(projectile);
    }

    /**
     * Generates enemy fire by adding enemy projectiles to the scene.
     */
    public void generateEnemyFire() {
        enemyUnits.forEach(enemy -> addEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
    }

    /**
     * Adds an enemy projectile to the list and to the scene.
     *
     * @param projectile the enemy projectile to be added
     */
    private void addEnemyProjectile(ActiveActorDestructible projectile) {
        if (projectile != null) {
            enemyProjectiles.add(projectile);
            root.getChildren().add(projectile);
        }
    }

    /**
     * Adds a power-up to the list and to the scene.
     *
     * @param powerUp the power-up to be added
     */
    public void addPowerUp(ActiveActorDestructible powerUp) {
        this.powerUps.add(powerUp);
        root.getChildren().add(powerUp);
    }


	//---------------------------------------------------------
    //functions to handle all the active actors in the game


	/**
     * Updates all active actors in the game by calling their updateActor() method.
     */
    public void updateActors() {
        friendlyUnits.forEach(plane -> plane.updateActor());
        enemyUnits.forEach(enemy -> enemy.updateActor());
        userProjectiles.forEach(projectile -> projectile.updateActor());
        enemyProjectiles.forEach(projectile -> projectile.updateActor());
        powerUps.forEach(powerUp -> powerUp.updateActor());
    }

    /**
     * Fires a projectile from the user plane.
     */
    @Override
    public void fireProjectile() {
        for (ActiveActorDestructible plane : friendlyUnits) {
            UserPlane userPlane = (UserPlane) plane;
            ActiveActorDestructible projectile = userPlane.fireProjectile();
            addUserProjectile(projectile);
        }
    }

    /**
     * Removes all destroyed actors from the scene and lists.
     */
    public void removeAllDestroyedActors() {
        removeDestroyedActors(friendlyUnits);
        removeDestroyedActors(enemyUnits);
        removeDestroyedActors(userProjectiles);
        removeDestroyedActors(enemyProjectiles);
        removeDestroyedActors(powerUps);
    }

    /**
     * Removes destroyed actors from the specified list and the scene.
     *
     * @param actors the list of actors to check for destruction
     */
    private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
        List<ActiveActorDestructible> destroyedActors = actors.stream().filter(actor -> actor.isDestroyed())
                .collect(Collectors.toList());
        root.getChildren().removeAll(destroyedActors);
        actors.removeAll(destroyedActors);
        
        destroyedActors.clear();
    }

    /**
     * Clears all actors from the scene and lists.
     */
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
	//functions that listens and handle power up drops

	/**
     * Spawns a heart power-up at the specified position and adds it to the scene.
     *
     * @param initialXPos the initial x-coordinate of the heart power-up
     * @param initialYPos the initial y-coordinate of the heart power-up
     */
    @Override
    public void spawnHeartPowerUp(double initialXPos, double initialYPos) {
        ActiveActorDestructible powerUp = new HeartUp(initialXPos, initialYPos);
        addPowerUp(powerUp);
    }

    /**
     * Spawns a projectile power-up at the specified position and adds it to the scene.
     *
     * @param initialXPos the initial x-coordinate of the projectile power-up
     * @param initialYPos the initial y-coordinate of the projectile power-up
     */
    @Override
    public void spawnProjectilePowerUp(double initialXPos, double initialYPos) {
        ActiveActorDestructible powerUp = new ProjectileUp(initialXPos, initialYPos);
        addPowerUp(powerUp);
    }

}
