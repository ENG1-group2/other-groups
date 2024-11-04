package com.badlogic.UniSim2;

import com.badlogic.gdx.Gdx;

public class Timer {
    private float elapsedTime;
    private final float maxTime;
    private boolean reachedMaxTime;

    public Timer() {
        this.maxTime = Consts.MAX_TIME;
        this.elapsedTime = 0;
        reachedMaxTime = false;
    }

    // Updates the timer
    public void update() {
        // Checks if the time has reached the limit
        if (elapsedTime < maxTime) {
            // If not it updates the time
            elapsedTime += Gdx.graphics.getDeltaTime();
        }
        else{
            reachedMaxTime = true;
        }
    }

    public float getElapsedTime(){
        return elapsedTime;
    }

    public boolean getReachedMaxTime(){
        return reachedMaxTime;
    }
}

