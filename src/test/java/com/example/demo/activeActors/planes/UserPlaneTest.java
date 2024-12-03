package com.example.demo.activeActors.planes;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.stage.Stage;


public class UserPlaneTest extends ApplicationTest{
    private UserPlane user;

    @Override
    public void start (Stage stage) {
        user = UserPlane.getInstance();
    }

    @BeforeEach
    public void setUp() {
        interact(() -> {
            user = UserPlane.getInstance();
        });
    }

    @Test
    public void testHeartPowerUpEffect() {
        interact(() -> {
            int initialHealth = user.getHealth();
            user.heartPowerUpEffect();
            assertEquals(initialHealth+1, user.getHealth());
        });
        
        
    }

}