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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.universityTycoon.PlaceableObjects.AccommodationBuilding;
import io.github.universityTycoon.PlaceableObjects.Building;
import io.github.universityTycoon.PlaceableObjects.MapObject;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashMap;

import static java.lang.Math.floorDiv;

public class MainScreen implements Screen {

    GameModel gameModel;
    SpriteBatch batch;
    ShapeRenderer shapeRenderer;
    Viewport viewport;
    HashMap<String, Texture> mapObjTextures; // Maintain a dict of paths -> Textures for map objects so that they are only loaded once

    Texture backgroundTexture;
    Texture constructionTexture;
    Texture squareTexture;

    Rectangle[] buildingButtons;


    Vector2 mousePos;
    boolean mouseDown;
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
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        viewport = new FitViewport(16, 9);

        mousePos = new Vector2(0,0);
        playerInputHandler = new PlayerInputHandler();

        backgroundTexture = new Texture("images/map.png");
        constructionTexture = new Texture("images/scaffold.png");
        squareTexture = new Texture("images/Gridsquare.png");
        mapObjTextures = new HashMap<>();
        buildingButtons = new Rectangle[gameModel.getNoBuildingTypes()];

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
        if (mouseDown && mousePos.y < 810) {
            placeBuilding();
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


        batch.draw(backgroundTexture, 0, 2, 16, 7);

        drawMapObjects();

        GameModel.font.draw(batch, time, 7.6f, 8.5f);
        GameModel.font.draw(batch, dateTimeString, 0.2f, 8.8f);



        GameModel.smaller_font.draw(batch, "Leisure Buildings: " + gameModel.getLeisureBuildingCount(), 13.15f, 8.9f);
        GameModel.smaller_font.draw(batch, "Teaching Buildings: " + gameModel.getTeachingBuildingCount(), 13.15f, 8.7f);
        GameModel.smaller_font.draw(batch, "Cafeteria Buildings: " + gameModel.getCafeteriaBuildingCount(), 13.15f, 8.5f);
        GameModel.smaller_font.draw(batch, "Accommodation Buildings: " + gameModel.getAccommodationBuildingCount(), 13.15f, 8.3f);






        batch.end();
        // Can't do a batch and ShapeRenderer that overlap, you have to begin and end one before beginning the other
        // So batch.end() must go before anything with ShapeRenderer
        drawBuildingMenu();

        // Draws a box around the building counters
        ShapeRenderer sr = new ShapeRenderer();
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.rectLine(1570, 948, 1570, 1080, 5, Color.BLACK, Color.BLACK);
        sr.rectLine(1568, 950, 1920, 950, 4, Color.BLACK, Color.BLACK);
        sr.end();
    }

    private void placeBuilding() {
        int tileLocationX = (int)(gameModel.getTilesWide() * mousePos.x / viewport.getScreenWidth() );
        int tileLocationY = (int)(1 +gameModel.getTilesHigh() * mousePos.y /(viewport.getScreenHeight() * 7f/9f)); // Multiply by 7/9 because the map covers 7/9ths of the screen
        // Defaulting to accommodation building for now
        // if statement prevents placing buildings on the top 2 tiles, this keeps text visible
        if (tileLocationY >2) {
            gameModel.mapController.addBuilding(new AccommodationBuilding(gameModel.getGameTimeGMT()), tileLocationX, tileLocationY);
        }
        System.out.println(tileLocationX + " " + tileLocationY);
    }

    private void drawMapObjects() {
        MapObject[][] mapObjects = gameModel.getMapObjects();
        for (int i = 0; i < mapObjects.length; i++) {
            for (int j = 0; j < mapObjects[i].length; j++) {
                float tileSizeOnScreen = viewport.getWorldWidth() / gameModel.getTilesWide() ;
                Vector2 screenPos = new Vector2((float) i * tileSizeOnScreen, viewport.getWorldHeight() - ((float) (j + 1) * tileSizeOnScreen));
                // Optional line to show the grid (mainly for testing)
                //batch.draw(squareTexture, screenPos.x, screenPos.y, tileSizeOnScreen, tileSizeOnScreen);
                // Could make this a more general MapObject for decoration object implementation
                if (mapObjects[i][j] instanceof Building building) {
                    // Get the texture for the object
                    String texturePath = mapObjects[i][j].getTexturePath();
                    if (!mapObjTextures.containsKey(texturePath))
                        mapObjTextures.put(texturePath, new Texture(texturePath));

                    batch.draw(mapObjTextures.get(texturePath), screenPos.x, screenPos.y, tileSizeOnScreen * building.getSize(), tileSizeOnScreen * building.getSize());

                    // Construction visualisation
                    if (building.isUnderConstruction) {
                        batch.draw(constructionTexture, screenPos.x, screenPos.y, tileSizeOnScreen * building.getSize(), tileSizeOnScreen * building.getSize());
                        GameModel.smaller_font.draw(batch, building.getConstructionPercent(gameModel.getGameTimeGMT()), screenPos.x, screenPos.y );
                    }
                }
            }
        }
    }

    private void drawBuildingMenu() {
        Rectangle background = new Rectangle();
        background.set(0, 0, viewport.getScreenWidth(), 244);

        Rectangle buildingButton1 = new Rectangle();
        buildingButton1.set(22, 22, 200, 200);

        Rectangle buildingButton2 = new Rectangle();
        buildingButton2.set(45 + buildingButton1.width, 22, 200, 200);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor((float) 79 / 255, (float) 79 / 255, (float) 79 / 255, 1);
        // when not in full screen, the +45 is needed to make the grey background go far enough to the right
        shapeRenderer.rect(background.x, background.y, background.width +45, background.height);

        shapeRenderer.setColor((float) 120 / 255, (float) 120 / 255, (float) 120 / 255, 1);
        shapeRenderer.rect(buildingButton1.x, buildingButton1.y, buildingButton1.width, buildingButton1.height);

        shapeRenderer.setColor((float) 120 / 255, (float) 120 / 255, (float) 120 / 255, 1);
        shapeRenderer.rect(buildingButton2.x, buildingButton2.y, buildingButton2.width, buildingButton2.height);

        shapeRenderer.end();
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

    }
}

