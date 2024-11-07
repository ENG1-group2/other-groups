package com.badlogic.UniSim2.GUImanager;

import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.gdx.Gdx;

/**
 * A timer which can count up from 0 seconds to 5 minutes. Will hold the
 * current elapsed time and can be {@link #update() updated} until the
 * max time is reached.
 */
public class Timer {
    private float elapsedTime;
    private final float maxTime;
    private boolean reachedMaxTime;

    public Timer() {
        this.maxTime = Consts.MAX_TIME;
        this.elapsedTime = 0;
        reachedMaxTime = false;
    }

    /**
     * Update the timer by the amount of time since the last frame and checks
     * whether the time has reached its maximum time limit.
     */
    public void update() {
        // Checks if the time has reached the limit
        if (elapsedTime < maxTime) {
            // If not it updates the time
            elapsedTime += Gdx.graphics.getDeltaTime();
        }
        if (elapsedTime >= maxTime) {
            reachedMaxTime = true;
        }
    }

    public float getElapsedTime(){
        return elapsedTime;
    }

    public boolean hasReachedMaxTime(){
        return reachedMaxTime;
    }
}

