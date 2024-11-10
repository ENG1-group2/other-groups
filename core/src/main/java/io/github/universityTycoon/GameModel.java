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

    public final BuildingTypes DEFAULT_SELECTED_BUILDING_TYPE = BuildingTypes.Accommodation;
    public final float BUTTON_COOLDOWN_TIMER = 0.5f;

    final float START_TIME_SECONDS = 300;
    public float timeRemainingSeconds = START_TIME_SECONDS;

    public static BitmapFont font;
    public static BitmapFont smallerFont;
    public static BitmapFont blackFont;

    public int tilesWide = 32;
    public int tilesHigh = 14;

    int noBuildingTypes;
    public int cafeteriaBuildingCount;
    public int accommodationBuildingCount;
    public int leisureBuildingCount;
    public int teachingBuildingCount;

    public float satisfactionScore;

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
        mapController = new MapController(tilesWide, tilesHigh);

        isPaused = false;

        noBuildingTypes = 4;

        font = new BitmapFont(Gdx.files.internal("ui/font.fnt"),
            Gdx.files.internal("ui/font.png"), false);

        smallerFont = new BitmapFont(Gdx.files.internal("ui/font.fnt"),
            Gdx.files.internal("ui/font.png"), false);

        blackFont = new BitmapFont(Gdx.files.internal("ui/arial.fnt"),
            Gdx.files.internal("ui/arial.png"), false);

        //font is 150x150 pixels, but we need to scale it to our viewport by ratio of viewport height to screen height
        font.setUseIntegerPositions(false);
        font.getData().setScale(0.003f, 0.003f);

        smallerFont.setUseIntegerPositions(false);
        smallerFont.getData().setScale(0.0015f, 0.0015f);

        blackFont.setUseIntegerPositions(false);
        blackFont.getData().setScale(0.002f, 0.002f);
    }

    // Everything that should be executed every frame
    public void runGame(float delta) {
        if (!getIsPaused()) {
            timeRemainingSeconds -= Gdx.graphics.getDeltaTime();
            mapController.updateBuildings(getGameTimeGMT());
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

    public int getCafeteriaBuildingCount() {
        return cafeteriaBuildingCount;
    }

    public int getAccommodationBuildingCount() {
        return accommodationBuildingCount;
    }

    public int getLeisureBuildingCount() {
        return leisureBuildingCount;
    }

    public int getTeachingBuildingCount() {
        return teachingBuildingCount;
    }

    public float getSatisfactionScore() {
        return satisfactionScore;
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
