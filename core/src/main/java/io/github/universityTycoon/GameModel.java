package io.github.universityTycoon;

import com.badlogic.gdx.utils.Timer;

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
}
