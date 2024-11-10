package io.github.universityTycoon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import io.github.universityTycoon.PlaceableObjects.MapObject;

import java.time.LocalDateTime;
import java.time.ZoneOffset;


/**
 * GameModel holds all the variables the game uses that may need access across multiple classes.
 *
 * @param YEARS_PER_MINUTE Conversion rate from real-world time to in-game years.
 * @param STARTING_YEAR The in-game starting year.
 *
 * @param DEFAULT_SELECTED_BUILDING_TYPE The default building type selected when the game starts.
 *
 * @param START_TIME_SECONDS Initial game time in seconds.
 * @param timeRemainingSeconds Remaining time in the game in seconds.
 *
 * @param font Primary font used for UI text rendering.
 * @param smallerFont Smaller version of the primary font for UI rendering.
 * @param blackFont Black-colored font used for certain UI elements.
 *
 * @param tilesWide Width of the map in tiles.
 * @param tilesHigh Height of the map in tiles.
 *
 * @param noBuildingTypes The total number of building types available in the game.
 * @param cafeteriaBuildingCount Number of cafeteria buildings.
 * @param accommodationBuildingCount Number of accommodation buildings.
 * @param leisureBuildingCount Number of leisure buildings.
 * @param teachingBuildingCount Number of teaching buildings.
 *
 * @param satisfactionScore The current satisfaction score.
 *
 * @param isPaused Indicates if the game is currently paused.
 *
 * @param gameState The current state of the game (in progress, paused, in menu, etc.).
 * @param eventManager Manages and dispatches in-game events.
 * @param eventListener Listener for game events, invoking actions in response to events.
 * @param scoreCalculator Calculates player scores based on game state.
 * @param audioSelector Selects which audio to play.
 * @param mapController Controls the map and its objects, updating the state of buildings.
 */
public class GameModel {

    // Game variables
    private final float YEARS_PER_MINUTE = 1f;
    private final int STARTING_YEAR = 2024;

    public final BuildingTypes DEFAULT_SELECTED_BUILDING_TYPE = BuildingTypes.Accommodation;

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

    /**
     * Enum representing the possible states of the game.
     */
    public enum GameState {
        inProgress,
        paused,
        inMenu,
        // ... extend as necessary
    }

    /**
     * The constructor is responsible for setting all variables.
     */
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

        // font is 150x150 pixels, but we need to scale it down to fit on screen
        // We don't use a smaller initial font, as it's then hard to read when scaled up.
        font.setUseIntegerPositions(false);
        font.getData().setScale(0.003f, 0.003f);

        smallerFont.setUseIntegerPositions(false);
        smallerFont.getData().setScale(0.0015f, 0.0015f);

        blackFont.setUseIntegerPositions(false);
        blackFont.getData().setScale(0.002f, 0.002f);
    }

    /**
     * Updates everything that should be updated every frame.
     * @param delta The time since the last frame.
     */
    public void runGame(float delta) {
        if (!getIsPaused()) {
            timeRemainingSeconds -= Gdx.graphics.getDeltaTime();
            mapController.updateBuildings(getGameTimeGMT());
        }
    }

    /**
     * Returns the current state of the map's objects.
     * @return A 2D array of MapObject representing objects on the map.
     */
    public MapObject[][] getMapObjects() {
        return mapController.mapObjects;
    }

    /**
     * Calculates the elapsed game time since the start.
     * @return The elapsed game time in seconds.
     */
    public float getTimeElapsed() {
        return START_TIME_SECONDS - timeRemainingSeconds;
    }

    /**
     * Gets the remaining time in the game.
     * @return The time remaining in seconds.
     */
    public float getTimeRemainingSeconds() {
        return timeRemainingSeconds;
    }

    /**
     * Gets the width of the map in tiles.
     * @return The width in tiles.
     */
    public int getTilesWide() {
        return tilesWide;
    }

    /**
     * Gets the height of the map in tiles.
     * @return The height in tiles.
     */
    public int getTilesHigh() {
        return tilesHigh;
    }

    /**
     * Returns whether the game is currently paused.
     * @return True if the game is paused, otherwise false.
     */
    public boolean getIsPaused() {
        return isPaused;
    }

    /**
     * Gets the count of cafeteria buildings.
     * @return The number of cafeteria buildings.
     */
    public int getCafeteriaBuildingCount() {
        return cafeteriaBuildingCount;
    }

    /**
     * Gets the count of accommodation buildings.
     * @return The number of accommodation buildings.
     */
    public int getAccommodationBuildingCount() {
        return accommodationBuildingCount;
    }

    /**
     * Gets the count of leisure buildings.
     * @return The number of leisure buildings.
     */
    public int getLeisureBuildingCount() {
        return leisureBuildingCount;
    }

    /**
     * Gets the count of teaching buildings.
     * @return The number of teaching buildings.
     */
    public int getTeachingBuildingCount() {
        return teachingBuildingCount;
    }

    /**
     * Returns the current player satisfaction score.
     * @return The satisfaction score as a float.
     */
    public float getSatisfactionScore() {
        return satisfactionScore;
    }

    /**
     * Gets the current game state.
     * @return The current GameState.
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Gets the number of building types.
     * @return The number of building types in the game.
     */
    public int getNoBuildingTypes() {
        return noBuildingTypes;
    }

    /**
     * Handles game events by doing something.
     * @param event The GameEvent to handle.
     */
    public void handleEvent(GameEvent event) {
        // Do lots of things probably
    }

    /**
     * Converts the value in the timer to the relative game time
     * (for example, after 2 minutes of real world time, the game year might be 2026)
     * @return The current in-game time as a LocalDateTime object.
     */
    public LocalDateTime getGameTimeGMT() {
        // Not bothering with leap days for now. Only difference will be the game ends an in-game day or two early, but time will still be 5 minutes
        long inGameSeconds = (long)(getTimeElapsed() * (365 * 24 * 60) * YEARS_PER_MINUTE);
        long startOfYearSeconds = LocalDateTime.of(STARTING_YEAR, 1, 1, 0, 0).toEpochSecond(ZoneOffset.UTC);

        return LocalDateTime.ofEpochSecond(inGameSeconds + startOfYearSeconds, 0, ZoneOffset.UTC);
    }
}
