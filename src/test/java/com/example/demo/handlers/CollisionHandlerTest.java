package com.example.demo.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.scene.Group;
import javafx.stage.Stage;

import com.example.demo.activeActors.ActiveActorDestructible;
import com.example.demo.activeActors.planes.enemyPlanes.EnemyPlaneT1;



public class CollisionHandlerTest extends ApplicationTest{
    private CollisionHandler collisionHandler;
    private ActiveActorManager activeActorManager;
    private ActiveActorDestructible enemy;
    private Group root;
    private static final double SCREEN_WIDTH = 1000;

    @Override
    public void start (Stage stage) {
        root = new Group();
        activeActorManager = new ActiveActorManager(root);
        collisionHandler = new CollisionHandler(SCREEN_WIDTH, activeActorManager);
        enemy = new EnemyPlaneT1(SCREEN_WIDTH, 300);
    }


    @Test
    public void testIsOutOfScreenCondition1() {
        // enemy plane's tail is just out of the screen
        interact(() ->{
            enemy.setTranslateX(-1*(SCREEN_WIDTH + enemy.getBoundsInParent().getWidth() + 1));
            assertEquals(true, collisionHandler.isOutOfScreen(enemy));
        });
    }

    @Test
    public void testIsOutOfScreenCondition2() {
        // enemy plane's tail is just at the edge of the screen
        interact(() ->{
            enemy.setTranslateX(-1*(SCREEN_WIDTH + enemy.getBoundsInParent().getWidth()));
            assertEquals(false, collisionHandler.isOutOfScreen(enemy));
        });
    }

    @Test
    public void testIsOutOfScreenCondition3() {
        // enemy plane's tail is still inside the screen
        interact(() ->{
            enemy.setTranslateX(-1*(SCREEN_WIDTH + enemy.getBoundsInParent().getWidth() - 1));
            assertEquals(false, collisionHandler.isOutOfScreen(enemy));
        });
    }

    @Test
    public void testIsOutOfScreenCondition4() {
        interact(() ->{
            //moving the enemy plane with update position from initial position
            enemy.setTranslateX(0);
            enemy.updatePosition();
            assertEquals(false, collisionHandler.isOutOfScreen(enemy));
        });
    }

}
