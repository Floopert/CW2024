package com.example.demo.eventListeners;

public interface BossEventListener {
    
    void shieldActivated();
    void shieldDeactivated();
    void updateShieldPosition(double yPosition);
}
