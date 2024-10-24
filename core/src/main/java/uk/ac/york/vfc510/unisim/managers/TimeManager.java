package uk.ac.york.vfc510.unisim.managers;

public class TimeManager {
    private static int currentTime;
    private int timeStep;

    public TimeManager(int timeStep, int currentTime) {
        TimeManager.currentTime = 0;
        this.timeStep = 1;
    }

    public void incrementTime() {
        currentTime += timeStep;
    }

    public static int getCurrentTime() {
        return currentTime;
    }
}
