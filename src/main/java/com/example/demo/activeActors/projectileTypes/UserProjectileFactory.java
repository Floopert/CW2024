package com.example.demo.activeActors.projectileTypes;

import java.lang.reflect.Constructor;
import java.util.*;

import com.example.demo.activeActors.ActiveActorDestructible;
import com.example.demo.activeActors.projectileTypes.userProjectiles.*;

/**
 * Factory class for creating user projectiles.
 */
public class UserProjectileFactory {
    
    private static UserProjectileFactory instance;

    private final List<String> projectileClassNames;



    /**
     * Private constructor to prevent instantiation.
     */
    private UserProjectileFactory() {
        projectileClassNames = new ArrayList<String>(
            Arrays.asList(
                UserProjectileT1.class.getName(),
                UserProjectileT2.class.getName(),
                UserProjectileT3.class.getName(),
                UserProjectileT4.class.getName()
            )
        );
    }

//--------------------------------------------------------------------------------------------
//-----------------------------------SINGLETON METHODS----------------------------------------

    /**
     * Gets the singleton instance of the UserProjectileFactory.
     *
     * @return the singleton instance of the UserProjectileFactory
     */
    public static UserProjectileFactory getInstance() {
        if (instance == null) {
            instance = new UserProjectileFactory();
        }
        return instance;
    }


//--------------------------------------------------------------------------------------------

    /**
     * Creates a projectile of the specified level at the given position.
     *
     * @param projectileLevel the level of the projectile to create
     * @param initialXPos the initial x-coordinate of the projectile
     * @param initialYPos the initial y-coordinate of the projectile
     * @return the created projectile
     */
    public ActiveActorDestructible createProjectile(int projectileLevel, double initialXPos, double initialYPos) {
        return getProjectileInstance(projectileLevel, initialXPos, initialYPos);
    }


    /**
     * Gets the total number of projectile types.
     *
     * @return the total number of projectile types
     */
    public int getTotalProjectileTypes() {
        return projectileClassNames.size();
    }




//--------------------------------------------------------------------------------------------
//-----------------------------------PRIVATE HELPER METHODS-----------------------------------

    /**
     * Gets the instance of the projectile based on the specified level and position.
     *
     * @param projectileLevel the level of the projectile
     * @param initialXPos the initial x-coordinate of the projectile
     * @param initialYPos the initial y-coordinate of the projectile
     * @return the created projectile
     */
    private ActiveActorDestructible getProjectileInstance(int projectileLevel, double initialXPos, double initialYPos) {
        if (projectileLevel >= 1 && projectileLevel <= projectileClassNames.size()) {
            try {
                String className = projectileClassNames.get(projectileLevel - 1);
                Class<?> myClass = Class.forName(className);
                Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
                return (ActiveActorDestructible) constructor.newInstance(initialXPos, initialYPos);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error creating projectile");
            }
        } else {
            throw new IndexOutOfBoundsException("Invalid projectile level: " + projectileLevel);
        }
    }

    
}