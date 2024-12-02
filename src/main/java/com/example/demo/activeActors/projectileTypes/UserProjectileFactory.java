package com.example.demo.activeActors.projectileTypes;

import java.lang.reflect.Constructor;
import java.util.*;

import com.example.demo.activeActors.ActiveActorDestructible;
import com.example.demo.activeActors.projectileTypes.userProjectiles.*;



public class UserProjectileFactory {
    
    private static UserProjectileFactory instance;

    private final List<String> projectileClassNames = new ArrayList<String>(
        Arrays.asList(
            UserProjectileT1.class.getName(),
            UserProjectileT2.class.getName(),
            UserProjectileT3.class.getName(),
            UserProjectileT4.class.getName()
        )
    );
    
    private UserProjectileFactory(){}
    
    public static UserProjectileFactory getInstance() {
        if (instance == null) {
            instance = new UserProjectileFactory();
        }
        return instance;
    }

    
    public ActiveActorDestructible createProjectile(int projectileLevel, double initialXPos, double initialYPos) {
        return getProjectileClass(projectileLevel, initialXPos, initialYPos);
    }

    private ActiveActorDestructible getProjectileClass(int projectileLevel, double initialXPos, double initialYPos){
        if (projectileLevel >= 1 && projectileLevel <= projectileClassNames.size()){
            
            try{
                
                String className = projectileClassNames.get(projectileLevel - 1);
                Class<?> myClass = Class.forName(className);
                Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
                return ((ActiveActorDestructible) constructor.newInstance(initialXPos, initialYPos));
            
            } catch (Exception e){
                e.printStackTrace();
                throw new RuntimeException("Error creating projectile");
            }

        } else {
            throw new IndexOutOfBoundsException("Invalid projectile level: " + projectileLevel);
        }
        
    }
    
    
    public int getTotalProjectileTypes(){
        return projectileClassNames.size();
    }

}
