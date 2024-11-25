package com.example.demo.eventListeners;

import com.example.demo.levels.LevelParent;

public interface LevelEventListener {
    public void changeLevel(LevelParent currentLevel, String levelName);
}
