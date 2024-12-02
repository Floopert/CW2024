package com.example.demo.eventListeners;

public interface CollisionEventListener {
    public void updateKillCount();
    public void userDamaged(int damage);
    public void updateScore(int score);
}
