package io.github.universityTycoon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.universityTycoon.PlaceableObjects.MapObject;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class GameModel {

    // Game variables
    private final float YEARS_PER_MINUTE = 1f;
    private final int STARTING_YEAR = 2024; // Don't make this < 1970 I beg

    final float START_TIME_SECONDS = 300;
    public float timeRemainingSeconds = START_TIME_SECONDS;


    public BitmapFont font;

    public int tilesWide = 64;
    public int tilesHigh = 28;

    int noBuildingTypes;

    public boolean isPaused;
    // Objects
    GameState gameState;
    EventManager eventManager;
    GameEventListener eventListener;
    ScoreCalculator scoreCalculator;
    AudioSelector audioSelector;
    MapController mapController;

    public enum GameState {
        inProgress,
        paused,
        inMenu,
        // ... extend as necessary
    }

    public GameModel() {

        eventListener = new GameEventListener(this::handleEvent); // If you're confused, look into "Java listener pattern" (I am also confused)
        eventManager = new EventManager(eventListener);
        scoreCalculator = new ScoreCalculator();
        audioSelector = new AudioSelector();
        mapController = new MapController(64, 28);

        isPaused = false;

        noBuildingTypes = 4;

        font = new BitmapFont(Gdx.files.internal("ui/font.fnt"),
            Gdx.files.internal("ui/font.png"), false);

        //font has 15pt, but we need to scale it to our viewport by ratio of viewport height to screen height
        font.setUseIntegerPositions(false);
        font.getData().setScale(0.003f, 0.003f);
    }

    // Everything that should be executed every frame
    public void runGame(float delta) {
        mapController.updateBuildings(getGameTimeGMT());
        if (!getIsPaused()) {
            timeRemainingSeconds -= Gdx.graphics.getDeltaTime();
        }
    }

    public MapObject[][] getMapObjects() {
        return mapController.mapObjects;
    }

    public float getTimeElapsed() {
        return START_TIME_SECONDS - timeRemainingSeconds;
    }

    public float getTimeRemainingSeconds() {
        return timeRemainingSeconds;
    }

    public int getTilesWide() {
        return tilesWide;
    }

    public int getTilesHigh() {
        return tilesHigh;
    }

    public boolean getIsPaused() {
        return isPaused;
    }

    public GameState getGameState() {
        return gameState;
    }

    public int getNoBuildingTypes() {
        return noBuildingTypes;
    }

    public void handleEvent(GameEvent event) {
        // Do lots of things probably
    }

    // Converts the value in the timer to the relative game time (for example, after 2 minutes of real world time, the game year might be 2026)
    public LocalDateTime getGameTimeGMT() {
        // Not bothering with leap days for now. Only difference will be the game ends an in-game day or two early, but time will still be 5 minutes
        long inGameSeconds = (long)(getTimeElapsed() * (365 * 24 * 60) * YEARS_PER_MINUTE);
        long startOfYearSeconds = LocalDateTime.of(STARTING_YEAR, 1, 1, 0, 0).toEpochSecond(ZoneOffset.UTC);

        return LocalDateTime.ofEpochSecond(inGameSeconds + startOfYearSeconds, 0, ZoneOffset.UTC);
    }
}
