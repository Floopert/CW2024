package com.example.demo.activeActors.planes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import com.example.demo.activeActors.ActiveActorDestructible;
import com.example.demo.activeActors.projectileTypes.userProjectiles.*;


import javafx.stage.Stage;


public class UserPlaneTest extends ApplicationTest{
    private UserPlane user;

    @Override
    public void start (Stage stage) {
        user = UserPlane.getInstance();
    }


    @Test
    public void testHeartPowerUpEffectCondition1() {
        interact(() -> {
            int initialHealth = user.getHealth();
            user.heartPowerUpEffect();
            assertEquals(initialHealth+1, user.getHealth(), "Health should increase by 1");
        });
    }

    @Test
    public void testHeartPowerUpEffectCondition2() {
        interact(() -> {
            for (int i = 0; i < 11; i++) {
                user.heartPowerUpEffect();
            }
            assertEquals(10, user.getHealth(), "Health should not exceed 10");
        });
    }


    @Test
    public void testFireProjectileCondition1(){
        interact(() -> {
            ActiveActorDestructible actualProjectile = user.fireProjectile();
            assertEquals(true, actualProjectile instanceof UserProjectileT1, "Projectile created should be of type UserProjectileT1");
        });
    }


    @Test
    public void testFireProjectileCondition2(){
        interact(() -> {
            user.projectilePowerUpEffect();
            ActiveActorDestructible actualProjectile = user.fireProjectile();
            assertEquals(true, actualProjectile instanceof UserProjectileT2, "Projectile created should be of type UserProjectileT2");
        });
    }


    @Test
    public void testFireProjectileCondition3(){
        interact(() -> {
            for (int i = 0; i < 8; i++) {
                user.projectilePowerUpEffect();
            }
            ActiveActorDestructible actualProjectile = user.fireProjectile();
            assertEquals(true, actualProjectile instanceof UserProjectileT4, "Projectile created should be of type UserProjectileT4 even after more than 4 power ups");
        });
    }


    @Test
    public void testTakeDamage(){
        interact(() -> {
            int initialHealth = user.getHealth();
            user.takeDamage(1);
            assertEquals(initialHealth-1, user.getHealth(), "Health should decrease by 1");
        });
    }


    @Test
    public void testIsDestroyedCondition1(){
        interact(() -> {
            user.destroyInstance();
            user = UserPlane.getInstance();
            int initialHealth = user.getHealth();
            user.takeDamage(initialHealth+1);
            assertEquals(true, user.isDestroyed(), "User plane should be destroyed");
        });
    }

    @Test
    public void testIsDestroyedCondition2(){
        interact(() -> {
            user.destroyInstance();
            user = UserPlane.getInstance();
            user.takeDamage(1);
            assertEquals(false, user.isDestroyed(), "User plane should NOT be destroyed");
        });
    }

}