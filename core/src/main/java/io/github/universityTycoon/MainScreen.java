package io.github.universityTycoon;

import com.badlogic.gdx.Game;
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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.universityTycoon.PlaceableObjects.*;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static java.lang.Math.floorDiv;

public class MainScreen implements Screen {

    GameModel gameModel;
    SpriteBatch batch;
    ShapeRenderer shapeRenderer;
    Viewport viewport;
    HashMap<String, Texture> mapObjTextures; // Maintain a dict of paths -> Textures for map objects so that they are only loaded once
    List<int[]> mapObjUnderConstruction;
    Texture backgroundTexture;
    Texture pauseTexture;
    Texture playTexture;
    Texture constructionTexture;
    Texture squareTexture;
    Texture rightArrowTexture;
    Texture leftArrowTexture;

    BuildingTypes currentBuilding;
    Rectangle pausePlayBox;
    Rectangle buildingIcon;
    Rectangle rightButton;
    Rectangle leftButton;

    Vector2 mousePos;
    boolean mouseDown;
    boolean placeMode;
    float buttonCooldownTimer;
    PlayerInputHandler playerInputHandler;


    String time;
    String dateTimeString;

    Music music = Gdx.audio.newMusic(Gdx.files.internal("music/main.mp3"));


    // Everything that goes in create for an application listener, goes in here
    // Meaning all asset/variable assignments
    final ScreenManager game;
    public MainScreen(ScreenManager main) {
        this.game = main;
        gameModel = new GameModel();
        mapObjUnderConstruction = new ArrayList<>();
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        viewport = new FitViewport(16, 9);

        mousePos = new Vector2(0,0);
        playerInputHandler = new PlayerInputHandler();

        backgroundTexture = new Texture("images/map.png");
        pauseTexture = new Texture("ui/pause.png");
        playTexture = new Texture("ui/play.png");
        constructionTexture = new Texture("images/scaffold.png");
        squareTexture = new Texture("images/Gridsquare.png");
        rightArrowTexture = new Texture("ui/right-arrow.png");
        leftArrowTexture = new Texture("ui/left-arrow.png");
        mapObjTextures = new HashMap<>();

        currentBuilding = gameModel.DEFAULT_SELECTED_BUILDING_TYPE;
        buildingIcon = new Rectangle();
        rightButton = new Rectangle();
        leftButton = new Rectangle();
        pausePlayBox = new Rectangle();

        buttonCooldownTimer = 0f;

        // start the playback of the background music when the screen is shown
        music.setVolume(0.5f);
        music.setLooping(true);
        music.play();
    }

    @Override
    public void render(float v) {
        gameModel.runGame(v);
        input();
        logic();
        draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }


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
        else {mouseDown = false;}
    }

    private void logic() {
        Vector3 touch = new Vector3(mousePos.x, mousePos.y, 0);
        viewport.getCamera().unproject(touch);

        buttonCooldownTimer -= Gdx.graphics.getDeltaTime();
        if (buttonCooldownTimer <= 0f) {
            buttonCooldownTimer = 0f;
        }

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
        if (mouseDown && rightButton.contains(touch.x, touch.y) && buttonCooldownTimer == 0f) {
            if (currentBuilding.ordinal() + 1 > BuildingTypes.values().length - 1) {currentBuilding = BuildingTypes.values()[0];}
            else {currentBuilding = BuildingTypes.values()[currentBuilding.ordinal() + 1];}
            buttonCooldownTimer = gameModel.BUTTON_COOLDOWN_TIMER;
        }
        // Decrements the current building value if the left arrow is clicked
        else if (mouseDown && leftButton.contains(touch.x, touch.y) && buttonCooldownTimer == 0f) {
            if (currentBuilding.ordinal() - 1 < 0) {currentBuilding = BuildingTypes.values()[BuildingTypes.values().length - 1];}
            else {currentBuilding = BuildingTypes.values()[currentBuilding.ordinal() - 1];}
            buttonCooldownTimer = gameModel.BUTTON_COOLDOWN_TIMER;
        }

        if (mouseDown && pausePlayBox.contains(touch.x, touch.y) && buttonCooldownTimer == 0f) {
            if (gameModel.getIsPaused()) {
                resume();
            } else {
                pause();
            }
            buttonCooldownTimer = gameModel.BUTTON_COOLDOWN_TIMER;
        }


        time = String.valueOf(floorDiv((int) gameModel.getTimeRemainingSeconds(), 60))
            + ":" + String.format("%02d", (int) gameModel.getTimeRemainingSeconds() % 60);
        dateTimeString = gameModel.getGameTimeGMT().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG));
    }


    private void draw() {

        batch.begin();

        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        pausePlayBox.set(-0.5f, 8.1f, 1.5f, 0.75f);
        batch.draw(backgroundTexture, 0, 2, 16, 7);
        if (!gameModel.isPaused) {
            batch.draw(pauseTexture, -0.5f, 8.1f, 1.5f, 0.75f);
        }
        else {
            batch.draw(playTexture, -0.5f, 8.1f, 1.5f, 0.75f);
        }


        drawMapObjects();

        GameModel.font.draw(batch, time, 7.6f, 8.5f);
        GameModel.font.draw(batch,"Satisfaction score: " + String.format("%.2f", gameModel.getSatisfactionScore()), 5.8f, 8.9f);
        GameModel.smaller_font.draw(batch, dateTimeString, 0.05f, 8.95f);



        GameModel.smaller_font.draw(batch, "Leisure Buildings: " + gameModel.getLeisureBuildingCount(), 13.15f, 8.9f);
        GameModel.smaller_font.draw(batch, "Teaching Buildings: " + gameModel.getTeachingBuildingCount(), 13.15f, 8.7f);
        GameModel.smaller_font.draw(batch, "Cafeteria Buildings: " + gameModel.getCafeteriaBuildingCount(), 13.15f, 8.5f);
        GameModel.smaller_font.draw(batch, "Accommodation Buildings: " + gameModel.getAccommodationBuildingCount(), 13.15f, 8.3f);


        List<int[]> found = new ArrayList<>();
        for (int[] entry : mapObjUnderConstruction) {
            MapObject[][] mapObjects = gameModel.getMapObjects();
            if (mapObjects[entry[0]][entry[1]] instanceof Building building) {
                if (building.isUnderConstruction) {
                    float tileSizeOnScreen = viewport.getWorldWidth() / gameModel.getTilesWide();
                    Vector2 screenPos = new Vector2((float) entry[0] * tileSizeOnScreen, viewport.getWorldHeight() - ((float) (entry[1] + 1) * tileSizeOnScreen));
                    GameModel.black_font.draw(batch, String.format("%.0f%%", building.getConstructionPercent(gameModel.getGameTimeGMT())), screenPos.x + 0.29f, screenPos.y + 0.61f);
                }
                else {
                    found.add(entry);
                }
            }
        }
        mapObjUnderConstruction.removeAll(found);
        batch.end();
        // Can't do a batch and ShapeRenderer that overlap, you have to begin and end one before beginning the other
        // So batch.end() must go before anything with ShapeRenderer
        drawBuildingMenu();

        //This text goes over the building menu, so must be drawn after.
        // This also therefore needs a new batch begin and end statement.
        batch.begin();
        GameModel.black_font.draw(batch, "Building type:" + currentBuilding.toString(),  0.01f, 1.98f);

        switch (currentBuilding) {
            case Accommodation:
                GameModel.black_font.draw(batch, "Number of rooms: " + AccommodationBuilding.getBuildingCapacity(),  0.01f, 1.68f);
                GameModel.black_font.draw(batch, "Satisfaction score bonus: " + AccommodationBuilding.getSatisfactionBonus(),  0.01f, 1.38f);
                GameModel.black_font.draw(batch, "Rent per month: " + AccommodationBuilding.getRentPricePPM(),  0.01f, 1.08f);
                GameModel.black_font.draw(batch, "Number of common rooms: " + AccommodationBuilding.getCommonRoomsCount(),  0.01f, 0.78f);
                break;
            case Leisure:
                GameModel.black_font.draw(batch, "Building capacity: " + LeisureBuilding.getBuildingCapacity() + " students",  0.01f, 1.68f);
                GameModel.black_font.draw(batch, "Satisfaction score bonus: " + LeisureBuilding.getSatisfactionBonus(),  0.01f, 1.38f);
                break;
            case Cafeteria:
                GameModel.black_font.draw(batch, "People fed per hour: " + Cafeteria.getBuildingCapacity(),  0.01f, 1.68f);
                GameModel.black_font.draw(batch, "Satisfaction score bonus: " + Cafeteria.getSatisfactionBonus(),  0.01f, 1.38f);
                GameModel.black_font.draw(batch, "Food quality: " + Cafeteria.getFoodQuality() + "/10",  0.01f, 1.08f);
                GameModel.black_font.draw(batch, "Hygiene rating: " + Cafeteria.getHygieneRating() + "/5",  0.01f, 0.78f);
                break;
            case Teaching:
                GameModel.black_font.draw(batch, "Building capacity: " + TeachingBuilding.getBuildingCapacity() + " students",  0.01f, 1.68f);
                GameModel.black_font.draw(batch, "Satisfaction score bonus: " + TeachingBuilding.getSatisfactionBonus(),  0.01f, 1.38f);
                GameModel.black_font.draw(batch, "Number of Lecture halls: " + TeachingBuilding.getLectureHallCount(),  0.01f, 1.08f);
                GameModel.black_font.draw(batch, "Number of lab rooms: " + TeachingBuilding.getLabCount(),  0.01f, 0.78f);
                GameModel.black_font.draw(batch, "Number of classrooms: " + TeachingBuilding.getClassroomCount(),  0.01f, 0.48f);
                break;
        }
        batch.end();

        // Draws a box around the building counters
        ShapeRenderer sr = new ShapeRenderer();
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.rectLine(1570, 948, 1570, 1080, 5, Color.BLACK, Color.BLACK);
        sr.rectLine(1568, 950, 1920, 950, 4, Color.BLACK, Color.BLACK);
        sr.end();
    }

    private void placeBuilding() {
        Vector2 mouseWorldPos = viewport.unproject(new Vector2(mousePos.x, viewport.getScreenHeight() - mousePos.y));
        int tileLocationX = (int)(gameModel.getTilesWide() * mouseWorldPos.x / viewport.getWorldWidth());
        int tileLocationY = (int)(gameModel.getTilesHigh() * mouseWorldPos.y / (viewport.getWorldHeight() - 2)); // Subtract 2 since the map is 2 world units up


        Building buildingToAdd = Building.getObjectFromEnum(currentBuilding, gameModel.getGameTimeGMT());
        if (gameModel.mapController.addBuilding(buildingToAdd, tileLocationX, tileLocationY)) {
            gameModel.satisfactionScore += buildingToAdd.getSatisfactionBonus();
            switch (currentBuilding) {
                case Accommodation -> gameModel.accommodationBuildingCount += 1;
                case Leisure -> gameModel.leisureBuildingCount += 1;
                case Cafeteria -> gameModel.cafeteriaBuildingCount += 1;
                case Teaching -> gameModel.teachingBuildingCount += 1;
            }
        }
        placeMode = false;
    }

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
                    if (building.isUnderConstruction) {
                        int[] position = new int[]{i, j};
                        mapObjUnderConstruction.add(position);
                        batch.draw(constructionTexture, screenPos.x, screenPos.y, tileSizeOnScreen * building.getSize(), tileSizeOnScreen * building.getSize());
                    }
                }
            }
        }
    }

    private void drawBuildingMenu() {
        // All positions are based on the bottom left of the rectangle

        Rectangle background = new Rectangle();
        background.set(0, 0, viewport.getScreenWidth(), 2);

        buildingIcon = new Rectangle();
        buildingIcon.set(7.1f, 0.1f, 1.8f, 1.8f);

        rightButton = new Rectangle();
        rightButton.set(9, 0.5f, 0.5f, 1);

        leftButton = new Rectangle();
        leftButton.set(6.5f, 0.5f, 0.5f, 1);


        ShapeRenderer sr = new ShapeRenderer();
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


    @Override
    public void pause() {
        gameModel.isPaused = true;
        music.pause();
    }

    @Override
    public void resume() {
        gameModel.isPaused = false;
        music.play();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        GameModel.font.dispose();
        GameModel.smaller_font.dispose();
        GameModel.black_font.dispose();
    }
}

