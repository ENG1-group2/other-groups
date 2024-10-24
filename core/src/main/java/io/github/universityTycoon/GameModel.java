package io.github.universityTycoon;

import com.badlogic.gdx.utils.Timer;

import java.time.LocalDateTime;

public class GameModel {
    public enum GameState {
        inProgress,
        paused,
        inMenu,
        // ... extend as necessary
    }

    GameState gameState;
    Timer timer;
    EventManager eventManager;
    GameEventListener eventListener;

    public GameModel() {
        timer = new Timer();

        eventListener = new GameEventListener(this::handleEvent); // If you're confused, look into "Java listener pattern" (I am also confused)
        eventManager = new EventManager(eventListener);
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
    public LocalDateTime getGameTime() {
        return LocalDateTime.of(2024, 10, 24, 18, 29);
    }
}
