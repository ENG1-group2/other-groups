package eng1.unisim.managers;

public class TimeManager {
    private static int currentTime;
    private int timeStep;
    private int timeLimit;

    public TimeManager(int timeStep, int currentTime) {
        TimeManager.currentTime = 0;
        this.timeStep = 1;
        this.timeLimit = 300;
        // 5 minutes = 300 seconds
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

    public int getTimeLimit() {
        return timeLimit;
    }

    public boolean isEndOfGame() {
        return currentTime >= timeLimit;
    }

}
