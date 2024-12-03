package com.example.demo.handlers;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.scene.Group;
import javafx.stage.Stage;

import com.example.demo.activeActors.ActiveActorDestructible;
import com.example.demo.activeActors.planes.UserPlane;
import com.example.demo.activeActors.planes.enemyPlanes.EnemyPlaneT1;



public class CollisionHandlerTest extends ApplicationTest{
    private CollisionHandler collisionHandler;
    private ActiveActorManager activeActorManager;
    private UserPlane user;
    private ActiveActorDestructible enemy;
    private Group root;
    private static final double SCREEN_WIDTH = 1000;

    @Override
    public void start (Stage stage) {
        /* 
        root = new Group();
        activeActorManager = new ActiveActorManager(root);
        collisionHandler = new CollisionHandler(SCREEN_WIDTH, activeActorManager);
        user = UserPlane.getInstance();
        enemy = new EnemyPlaneT1(SCREEN_WIDTH, 300);
        activeActorManager.addFriendlyUnit(user);
        */
    }

    @BeforeEach
    public void setUp() {
        interact(() -> {
            root = new Group();
            activeActorManager = new ActiveActorManager(root);
            collisionHandler = new CollisionHandler(SCREEN_WIDTH, activeActorManager);
            user = UserPlane.getInstance();
            enemy = new EnemyPlaneT1(SCREEN_WIDTH, 300);
            activeActorManager.addFriendlyUnit(user);
        });
    }

    @Test
    public void testIsOutOfScreen() {
        interact(() ->{
            enemy.setTranslateX(-1*(SCREEN_WIDTH + enemy.getBoundsInParent().getWidth() + 1));
            assertEquals(true, collisionHandler.isOutOfScreen(enemy) , "collisionHandler.isOutOfScreen(enemy)");
        });
        
    }

}
