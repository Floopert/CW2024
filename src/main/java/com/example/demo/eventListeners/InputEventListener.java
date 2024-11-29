package com.example.demo.eventListeners;


public interface InputEventListener {
    default void fireProjectile(){};
    default void pauseGame(){};
}
