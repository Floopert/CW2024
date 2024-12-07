package com.example.demo.eventListeners;

/**
 * Interface for listening to boss events such as shield activation, deactivation, and position updates.
 */
public interface BossEventListener {
    
    /**
     * Called when the shield is activated.
     */
    void shieldActivated();

    /**
     * Called when the shield is deactivated.
     */
    void shieldDeactivated();

    /**
     * Updates the position of the shield.
     *
     * @param yPosition the new y-coordinate position of the shield
     */
    void updateShieldPosition(double yPosition);

    
}