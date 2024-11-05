package io.github.universityTycoon;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class GameModel {
    public enum GameState {
        inProgress,
        paused,
        inMenu,
        // ... extend as necessary
    }

    // Game variables
    private final float YEARS_PER_MINUTE = 1f;
    private final int STARTING_YEAR = 2024; // Don't make this < 1970 I beg

    // Objects
    GameState gameState;
    EventManager eventManager;
    GameEventListener eventListener;
    PlayerInputHandler playerInputHandler;
    ScoreCalculator scoreCalculator;
    AudioSelector audioSelector;
    MapController mapController;

    public GameModel(PlayerInputHandler inputHandler) {

        eventListener = new GameEventListener(this::handleEvent); // If you're confused, look into "Java listener pattern" (I am also confused)
        eventManager = new EventManager(eventListener);
        playerInputHandler = inputHandler;
        scoreCalculator = new ScoreCalculator();
        audioSelector = new AudioSelector();
        mapController = new MapController();
    }

    public GameState getGameState() {
        return gameState;
    }

    public int getTimeSeconds() {
        return 0;
    }

    public void handleEvent(GameEvent event) {
        // Do lots of things probably
    }

    // Converts the value in the timer to the relative game time (for example, after 2 minutes of real world time, the game year might be 2026)
    // MAKE SURE TO USE TIME ELAPSED, NOT TIME REMAINING
    public LocalDateTime getGameTimeGMT(float timeElapsedSeconds) {
        // Not bothering with leap days for now. Only difference will be the game ends an in-game day or two early, but time will still be 5 minutes
        long inGameSeconds = (long)(timeElapsedSeconds * (365 * 24 * 60) * YEARS_PER_MINUTE);
        long startOfYearSeconds = LocalDateTime.of(STARTING_YEAR, 1, 1, 0, 0).toEpochSecond(ZoneOffset.UTC);

        return LocalDateTime.ofEpochSecond(inGameSeconds + startOfYearSeconds, 0, ZoneOffset.UTC);
    }
}
