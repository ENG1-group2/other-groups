package io.github.universityTycoon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.universityTycoon.PlaceableObjects.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashMap;
import static java.lang.Math.floorDiv;


/**
 * MainScreen is an implementation of the screen interface.
 * It is used for the main screen of the game.
 *
 * @param gameModel An instance of the GameModel class, which stores most information about the state of the game.
 * @param batch The batch which draws textures.
 * @param sr An instance of ShapeRenderer, Renders shapes on screen.
 * @param shapeRenderer An instance of ShapeRenderer, Renders shapes on screen.
 * @param viewport The viewport things are displayed on.
 * @param mapObjTextures Maintain a dict of paths -> Textures for map objects so that they are only loaded once
 *
 * Not going to list all the textures as they are self-explanatory
 *
 * @param currentBuilding Has the current type of building the drag and drop button is displaying.
 *
 * All the rectangles are hitboxes for their respectively named buttons.
 *
 * @param mousePos The vector position of the mouse.
 * @param mouseDown The updated when the mouse is clicked.
 * @param placeMode Used as a condition to check whether to start attempting to place a building.
 * @param playerInputHandler An instance of the PlayerInputHandler class, which handles inputs.
 *
 * @param time Takes the time as seconds from gameModel
 * @param dateTimeString Takes the time as a date from gameModel, and
 *
 * @param music The music.
 *
 * @param game An instance of the ScreenManager class, used in the constructor so that stuff works.
 */
public class MainScreen implements Screen {

    GameModel gameModel;
    SpriteBatch batch;
    ShapeRenderer shapeRenderer;
    ShapeRenderer sr;
    Viewport viewport;
    HashMap<String, Texture> mapObjTextures;

    Texture backgroundTexture;
    Texture pauseTexture;
    Texture playTexture;
    Texture constructionTexture;
    Texture squareTexture;
    Texture rightArrowTexture;
    Texture leftArrowTexture;
    Texture percentTexture;

    BuildingTypes currentBuilding;
    Rectangle pausePlayBox;
    Rectangle buildingIcon;
    Rectangle rightButton;
    Rectangle leftButton;
    Rectangle background;

    Vector2 mousePos;
    boolean mouseDown;
    boolean placeMode;
    PlayerInputHandler playerInputHandler;


    String time;
    String dateTimeString;

    Music music = Gdx.audio.newMusic(Gdx.files.internal("music/main.mp3"));


    final ScreenManager game;

    public MainScreen(ScreenManager main) {
        this.game = main;
        gameModel = new GameModel();
    }

    /**
     * Show is responsible for setting all variables.
     * It is effectively the constructor.
     */

    @Override
    public void show() {
        batch = new SpriteBatch();
        viewport = new FitViewport(16, 9);
        sr = new ShapeRenderer();
        shapeRenderer = new ShapeRenderer();

        mousePos = new Vector2(0,0);
        playerInputHandler = new PlayerInputHandler();

        backgroundTexture = new Texture("images/map.png");
        pauseTexture = new Texture("ui/pause.png");
        playTexture = new Texture("ui/play.png");
        constructionTexture = new Texture("images/scaffold.png");
        squareTexture = new Texture("images/Gridsquare.png");
        rightArrowTexture = new Texture("ui/right-arrow.png");
        leftArrowTexture = new Texture("ui/left-arrow.png");
        percentTexture = new Texture("images/percent_plate.png");
        mapObjTextures = new HashMap<>();

        currentBuilding = gameModel.DEFAULT_SELECTED_BUILDING_TYPE;
        buildingIcon = new Rectangle();
        rightButton = new Rectangle();
        leftButton = new Rectangle();
        pausePlayBox = new Rectangle();
        background = new Rectangle();

        // start the playback of the background music when the screen is shown
        music.setVolume(0.5f);
        music.setLooping(true);
        music.play();
    }


    /**
     * Calls three functions which are used to split up the rendering method.
     *
     * @param v Not sure what this does, but it's part of the screen interface ¯\_(ツ)_/¯
     */
    @Override
    public void render(float v) {
        gameModel.runGame(v);
        input();
        logic();
        draw();
    }


    /**
     * Resizes the viewport
     *
     * @param width The new width
     * @param height The new height
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }



    /**
     * Does things when inputs are received.
     */
    private void input() {

        if (playerInputHandler.getIsPauseJustPressed()) {
            if (gameModel.getIsPaused()) {
                resume();
            } else {
                pause();
            }  // Toggle the pause state
        }

        if (playerInputHandler.getIsMouseDown()) {
            mousePos = playerInputHandler.getMousePos();
            mouseDown = true;
        }
        else {
            mouseDown = false;
        }
    }


    /**
     * Deals with the logic of the screen.
     */
    private void logic() {
        Vector3 touch = new Vector3(mousePos.x, mousePos.y, 0);
        viewport.getCamera().unproject(touch);

        // Checks to see if the building icon has been clicked
        if (mouseDown && buildingIcon.contains(touch.x, touch.y)) {
            placeMode = true;
        }
        // Places the building when the user has stopped dragging the mouse in place mode (ABOVE the Menu Bar)
        else if (!mouseDown && placeMode && mousePos.y < 810) {
            placeBuilding();
        }
        // Cancels place mode if the user lets go of the mouse in the Menu Bar
        else if (!mouseDown && placeMode) {
            placeMode = false;
        }

        // Increments the current building value if the right arrow is clicked
        if (playerInputHandler.mouseJustClicked() && rightButton.contains(touch.x, touch.y)) {
            currentBuilding = BuildingTypes.values()[(currentBuilding.ordinal() + 1) % BuildingTypes.values().length];
        }
        // Decrements the current building value if the left arrow is clicked
        else if (playerInputHandler.mouseJustClicked() && leftButton.contains(touch.x, touch.y)) {
            if (currentBuilding.ordinal() - 1 < 0)
                currentBuilding = BuildingTypes.values()[BuildingTypes.values().length - 1];
            else
                currentBuilding = BuildingTypes.values()[currentBuilding.ordinal() - 1];
        }

        if (playerInputHandler.mouseJustClicked() && pausePlayBox.contains(touch.x, touch.y)) {
            if (gameModel.getIsPaused()) {
                resume();
            } else {
                pause();
            }
        }


        time = String.valueOf(floorDiv((int) gameModel.getTimeRemainingSeconds(), 60))
            + ":" + String.format("%02d", (int) gameModel.getTimeRemainingSeconds() % 60);
        dateTimeString = gameModel.getGameTimeGMT().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
    }

    /**
     * Draws all textures on the screen.
     * Note: Batch.begin() and batch.end() must contain all draw statements, and cannot overlap with other begin/ends.
     * This is also true for shapeRenderer. Meaning batch.end() must come before shapeRenderer.begin() for example.
     */
    private void draw() {

        batch.begin();

        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        // Draws either a pause or play button depending on the game state.
        pausePlayBox.set(-0.5f, 8.1f, 1.5f, 0.75f);
        batch.draw(backgroundTexture, 0, 2, 16, 7);
        if (!gameModel.isPaused) {
            batch.draw(pauseTexture, -0.5f, 8.1f, 1.5f, 0.75f);
        }
        else {
            batch.draw(playTexture, -0.5f, 8.1f, 1.5f, 0.75f);
        }

        // Draws all the objects that are placed on the map
        drawMapObjects();

        // Draws the time remaining, the satisfaction score and the date.
        GameModel.font.draw(batch, time, 7.6f, 8.5f);
        GameModel.font.draw(batch,"Satisfaction score: " + String.format("%.2f", gameModel.getSatisfactionScore()), 5.8f, 8.9f);
        GameModel.smallerFont.draw(batch, dateTimeString, 0.05f, 8.95f);


        // Draws the building counters
        GameModel.smallerFont.draw(batch, "Leisure Buildings: " + gameModel.getLeisureBuildingCount(), 13.15f, 8.9f);
        GameModel.smallerFont.draw(batch, "Teaching Buildings: " + gameModel.getTeachingBuildingCount(), 13.15f, 8.7f);
        GameModel.smallerFont.draw(batch, "Cafeteria Buildings: " + gameModel.getCafeteriaBuildingCount(), 13.15f, 8.5f);
        GameModel.smallerFont.draw(batch, "Accommodation Buildings: " + gameModel.getAccommodationBuildingCount(), 13.15f, 8.3f);

        batch.end();
        // Can't do a batch and ShapeRenderer that overlap, you have to begin and end one before beginning the other
        // So batch.end() must go before anything with ShapeRenderer

        // Draws all the textures for the building menu (bottom portion of the screen)
        drawBuildingMenu();

        // This text goes over the building menu, so must be drawn after.
        // This also therefore needs a new batch begin and end statement.
        batch.begin();

        // Draws all the relevant information about the currently selected building.
        GameModel.blackFont.draw(batch, "Building type:" + currentBuilding.toString(),  0.01f, 1.98f);

        switch (currentBuilding) {
            case Accommodation:
                GameModel.blackFont.draw(batch, "Number of rooms: " + AccommodationBuilding.getBuildingCapacity(),  0.01f, 1.68f);
                GameModel.blackFont.draw(batch, "Satisfaction score bonus: " + AccommodationBuilding.getSatisfactionBonus(),  0.01f, 1.38f);
                GameModel.blackFont.draw(batch, "Rent per month: " + AccommodationBuilding.getRentPricePPM(),  0.01f, 1.08f);
                GameModel.blackFont.draw(batch, "Number of common rooms: " + AccommodationBuilding.getCommonRoomsCount(),  0.01f, 0.78f);
                // Prints the duration as the number of days.
                GameModel.blackFont.draw(batch, "Time to build: " + AccommodationBuilding.getConstructionGameTime().getSeconds() / 86400 + " days",  0.01f, 0.48f);
                break;
            case Leisure:
                GameModel.blackFont.draw(batch, "Building capacity: " + LeisureBuilding.getBuildingCapacity() + " students",  0.01f, 1.68f);
                GameModel.blackFont.draw(batch, "Satisfaction score bonus: " + LeisureBuilding.getSatisfactionBonus(),  0.01f, 1.38f);
                GameModel.blackFont.draw(batch, "Time to build: " + LeisureBuilding.getConstructionGameTime().getSeconds() / 86400 + " days",  0.01f, 1.08f);
                break;
            case Cafeteria:
                GameModel.blackFont.draw(batch, "People fed per hour: " + Cafeteria.getBuildingCapacity(),  0.01f, 1.68f);
                GameModel.blackFont.draw(batch, "Satisfaction score bonus: " + Cafeteria.getSatisfactionBonus(),  0.01f, 1.38f);
                GameModel.blackFont.draw(batch, "Food quality: " + Cafeteria.getFoodQuality() + "/10",  0.01f, 1.08f);
                GameModel.blackFont.draw(batch, "Hygiene rating: " + Cafeteria.getHygieneRating() + "/5",  0.01f, 0.78f);
                GameModel.blackFont.draw(batch, "Time to build: " + Cafeteria.getConstructionGameTime().getSeconds() / 86400 + " days",  0.01f, 0.48f);
                break;
            case Teaching:
                GameModel.blackFont.draw(batch, "Building capacity: " + TeachingBuilding.getBuildingCapacity() + " students",  0.01f, 1.68f);
                GameModel.blackFont.draw(batch, "Satisfaction score bonus: " + TeachingBuilding.getSatisfactionBonus(),  0.01f, 1.38f);
                GameModel.blackFont.draw(batch, "Number of Lecture halls: " + TeachingBuilding.getLectureHallCount(),  0.01f, 1.08f);
                GameModel.blackFont.draw(batch, "Number of lab rooms: " + TeachingBuilding.getLabCount(),  0.01f, 0.78f);
                GameModel.blackFont.draw(batch, "Number of classrooms: " + TeachingBuilding.getClassroomCount(),  0.01f, 0.48f);
                GameModel.blackFont.draw(batch, "Time to build: " + TeachingBuilding.getConstructionGameTime().getSeconds() / 86400 + " days",  9.51f, 1.98f);
                break;
        }

        // Show building when dragging
        if (placeMode && mouseDown) {
            Building buildingToAdd = Building.getObjectFromEnum(currentBuilding, gameModel.getGameTimeGMT());
            Vector2 mouseGridPos = getMouseGridPos(mousePos);
            Vector2 screenPos = new Vector2(viewport.getWorldWidth() * mouseGridPos.x / gameModel.getTilesWide(), viewport.getWorldHeight() - ((viewport.getWorldHeight() - 2) * (mouseGridPos.y + 1) / gameModel.getTilesHigh()));
            float tileSizeOnScreen = viewport.getWorldWidth() / gameModel.getTilesWide();
            String texturePath = buildingToAdd.getTexturePath();
            if (!mapObjTextures.containsKey(texturePath)) {
                mapObjTextures.put(texturePath, new Texture(texturePath));
            }
            batch.draw(mapObjTextures.get(buildingToAdd.getTexturePath()), screenPos.x, screenPos.y, tileSizeOnScreen * buildingToAdd.getSize(), tileSizeOnScreen * buildingToAdd.getSize());
        }


        batch.end();

        // Draws a box around the building counters
        // Again, note that sr.begin() must come after all other end() statements.

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rectLine(1570, 948, 1570, 1080, 5, Color.BLACK, Color.BLACK);
        shapeRenderer.rectLine(1568, 950, 1920, 950, 4, Color.BLACK, Color.BLACK);
        shapeRenderer.end();
    }

    /**
     * Checks if addBuilding was successful, and if so, updates satisfaction score, and the relevant building counter.
     *
     * @param gridPos Gets the mouse position as a specific tile.
     * @param buildingToAdd turns the building type in currentBuilding, into a Building object that can be used.
     */
    private void placeBuilding() {
        Vector2 gridPos = getMouseGridPos(mousePos);

        Building buildingToAdd = Building.getObjectFromEnum(currentBuilding, gameModel.getGameTimeGMT());
        if (gameModel.mapController.addBuilding(buildingToAdd, (int)gridPos.x, (int)gridPos.y)) {
            gameModel.satisfactionScore += buildingToAdd.getSatisfactionBonus();
            switch (currentBuilding) {
                case Accommodation -> gameModel.accommodationBuildingCount += 1;
                case Leisure -> gameModel.leisureBuildingCount += 1;
                case Cafeteria -> gameModel.cafeteriaBuildingCount += 1;
                case Teaching -> gameModel.teachingBuildingCount += 1;
            }
        }
    }

    /**
     * Takes the mouses pixel coordinates on the screen, and returns the grid coordinate.
     * @param mouseScreenPos The mouses pixel coordinate on the screen
     * @return The grid location of the mouse.
     */
    public Vector2 getMouseGridPos(Vector2 mouseScreenPos) {
        Vector2 mouseWorldPos = viewport.unproject(new Vector2(mouseScreenPos.x, viewport.getScreenHeight() - mouseScreenPos.y));
        int tileLocationX = (int)(gameModel.getTilesWide() * mouseWorldPos.x / viewport.getWorldWidth());
        int tileLocationY = (int)(gameModel.getTilesHigh() * mouseWorldPos.y / (viewport.getWorldHeight() - 2)); // Subtract 2 since the map is 2 world units up
        return new Vector2(tileLocationX, tileLocationY);
    }

    /**
     * Draws the placeable objects on the map, as well drawing the grid when the user is placing a building.
     *
     * @param mapObjects The 2D array of map objects stored in gameModel.
     */
    private void drawMapObjects() {
        MapObject[][] mapObjects = gameModel.getMapObjects();
        for (int i = 0; i < mapObjects.length; i++) {
            for (int j = 0; j < mapObjects[i].length; j++) {
                float tileSizeOnScreen = viewport.getWorldWidth() / gameModel.getTilesWide() ;
                Vector2 screenPos = new Vector2((float) i * tileSizeOnScreen, viewport.getWorldHeight() - ((float) (j + 1) * tileSizeOnScreen));
                // Show the grids (when in place mode)
                if (placeMode && mouseDown) {
                    batch.draw(squareTexture, screenPos.x, screenPos.y, tileSizeOnScreen, tileSizeOnScreen);
                }
                // Could make this a more general MapObject for decoration object implementation
                if (mapObjects[i][j] instanceof Building building) {
                    // Get the texture for the object
                    String texturePath = mapObjects[i][j].getTexturePath();
                    if (!mapObjTextures.containsKey(texturePath)) {
                        mapObjTextures.put(texturePath, new Texture(texturePath));
                    }
                    batch.draw(mapObjTextures.get(texturePath), screenPos.x, screenPos.y, tileSizeOnScreen * building.getSize(), tileSizeOnScreen * building.getSize());
                    // Draws scaffolding and the construction percentage for buildings under construction.
                    if (building.isUnderConstruction) {
                        batch.draw(constructionTexture, screenPos.x, screenPos.y, tileSizeOnScreen * building.getSize(), tileSizeOnScreen * building.getSize());
                        batch.draw(percentTexture, screenPos.x + 0.17f, screenPos.y+ 0.25f, 48 * 0.015f, 32 * 0.015f);
                        GameModel.blackFont.draw(batch, String.format("%.0f%%", building.getConstructionPercent(gameModel.getGameTimeGMT())), screenPos.x + 0.29f, screenPos.y + 0.61f);
                    }
                }
            }
        }
    }

    /**
     * Draws the building menu at the bottom of the screen.
     */
    private void drawBuildingMenu() {

        // All positions are based on the bottom left of the rectangle
        background.set(0, 0, viewport.getScreenWidth(), 2);
        buildingIcon.set(7.1f, 0.1f, 1.8f, 1.8f);
        rightButton.set(9, 0.5f, 0.5f, 1);
        leftButton.set(6.5f, 0.5f, 0.5f, 1);


        sr.setProjectionMatrix(viewport.getCamera().combined);
        sr.begin(ShapeRenderer.ShapeType.Filled);

        sr.setColor((float) 84 / 255, (float) 120 / 255, (float) 125 / 255, 1);
        sr.rect(background.x, background.y, background.width, background.height);

        sr.setColor((float) 107 / 255, (float) 153 / 255, (float) 151 / 255, 1);
        sr.rect(buildingIcon.x, buildingIcon.y, buildingIcon.width, buildingIcon.height);

        sr.setColor((float) 138 / 255, (float) 168 / 255, (float) 184 / 255, 1);
        sr.rect(rightButton.x, rightButton.y, rightButton.width, rightButton.height);
        sr.setColor((float) 138 / 255, (float) 168 / 255, (float) 184 / 255, 1);
        sr.rect(leftButton.x, leftButton.y, leftButton.width, leftButton.height);

        sr.end();

        // Renders the building texture and arrow textures on the buttons
        String currentBuildingTP = Building.getObjectFromEnum(currentBuilding, gameModel.getGameTimeGMT()).getTexturePath();
        batch.begin();
        batch.draw(new Texture(currentBuildingTP), buildingIcon.x, buildingIcon.y, buildingIcon.width, buildingIcon.height);
        batch.draw(rightArrowTexture, rightButton.x, 0.75f, rightButton.width, 0.5f);
        batch.draw(leftArrowTexture, leftButton.x, 0.75f, leftButton.width, 0.5f);
        batch.end();
    }

    /**
     * Pauses the game.
     */
    @Override
    public void pause() {
        gameModel.isPaused = true;
        music.pause();
    }

    /**
     * Resumes the game
     */
    @Override
    public void resume() {
        gameModel.isPaused = false;
        music.play();
    }

    @Override
    public void hide() {

    }

    // Disposes of all textures.
    @Override
    public void dispose() {
        batch.dispose();
        sr.dispose();
        shapeRenderer.dispose();
        GameModel.font.dispose();
        GameModel.smallerFont.dispose();
        GameModel.blackFont.dispose();
    }
}

