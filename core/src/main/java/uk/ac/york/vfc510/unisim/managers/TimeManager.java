package uk.ac.york.vfc510.unisim.managers;

public class TimeManager {
    private static int currentTime;
    private int timeStep;
    private int timeLimit;

    public TimeManager(int timeStep, int currentTime) {
        TimeManager.currentTime = 0;
        this.timeStep = 1;
        this.timeLimit = 100;
    }

    public void incrementTime() {
        currentTime += timeStep;
    }

    public static int getCurrentTime() {
        return currentTime;
    }

    public int getTimeStep() {
        return timeStep;
    }

    public boolean isEndOfGame() {
        return currentTime >= timeLimit;
    }
}
