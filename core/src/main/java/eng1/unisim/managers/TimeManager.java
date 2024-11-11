package eng1.unisim.managers;

/**
 * manages the game time system, tracking elapsed time and handling time-based game events
 */
public class TimeManager {
    // tracks the current game time in seconds
    private static int currentTime;
    // how many seconds to increment per time step
    private int timeStep;
    // maximum time limit for the game in seconds
    private int timeLimit;

    /**
     * creates a new time manager with specified time step
     * @param timeStep seconds to increment per step
     * @param currentTime starting time (currently unused)
     */
    public TimeManager(int timeStep, int currentTime) {
        TimeManager.currentTime = 0;
        this.timeStep = 1;
        this.timeLimit = 300;
        // 5 minutes = 300 seconds
    }

    // advances the game time by one time step
    public void incrementTime() {
        currentTime += timeStep;
    }

    // returns the current game time in seconds
    public static int getCurrentTime() {
        return currentTime;
    }

    // returns how many seconds are added per time step
    public int getTimeStep() {
        return timeStep;
    }

    // returns the total time limit in seconds
    public int getTimeLimit() {
        return timeLimit;
    }

    // checks if the game time has exceeded the time limit
    public boolean isEndOfGame() {
        return currentTime >= timeLimit;
    }
}
