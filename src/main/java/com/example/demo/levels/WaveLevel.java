package com.example.demo.levels;

import com.example.demo.levelViews.LevelViewWaveLevel;

public abstract class WaveLevel extends LevelParent{
    
    private final String nextLevel;

    public WaveLevel(double screenHeight, double screenWidth, String currentLevel, String nextLevel) {
        super(screenHeight, screenWidth, currentLevel);
        this.nextLevel = nextLevel;
    }

    protected abstract int getWaveSize();
    protected abstract boolean waveHasEnded();

    @Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		}
		else if (waveHasEnded() && allEnemiesKilled()){
			goToNextLevel(nextLevel);
		}
			
	}


    private boolean allEnemiesKilled(){
        return getCurrentNumberOfEnemies() == 0;
    };


	@Override
	protected void instantiateLevelView(){
		//reference to levelView is stored in super class to access methods that are generated the same for all levels
		super.levelView = new LevelViewWaveLevel(getRoot(), getUser().getHealth(), getCurrentScore(), getWaveSize());
	};

    protected void updateWavesRemaining(int wavesRemaining) {
		((LevelViewWaveLevel)super.levelView).updateWavesLeft(wavesRemaining);
	}

}
