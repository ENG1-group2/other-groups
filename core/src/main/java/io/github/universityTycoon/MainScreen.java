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
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.universityTycoon.PlaceableObjects.AccommodationBuilding;
import io.github.universityTycoon.PlaceableObjects.MapObject;

import static java.lang.Math.floorDiv;

public class MainScreen implements Screen {

    GameModel gameModel;
    SpriteBatch batch;
    ShapeRenderer shapeRenderer;
    Viewport viewport;

    Texture backgroundTexture;
    Texture testBuildingTexture;

    Rectangle[] buildingButtons;


    Vector2 mousePos;
    boolean mouseDown;
    PlayerInputHandler playerInputHandler;


    String time;

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
        testBuildingTexture = new Texture("images/new_uni_style_assets.png");
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

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.P)) {
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

    }


    private void draw() {

        batch.begin();

        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);


        batch.draw(backgroundTexture, 0, 2, 16, 7);
        game.font.draw(batch, time, 7.6f, 8.5f);

        drawMapObjects();

        //drawBuildingMenu();

        batch.end();
    }

    private void placeBuilding() {
        int tileLocationX = (int)(gameModel.getTilesWide() * mousePos.x / viewport.getScreenWidth());
        int tileLocationY = (int)(gameModel.getTilesHigh() * mousePos.y / viewport.getScreenHeight());

        // Defaulting to accommodation building for now
        gameModel.mapController.addBuilding(new AccommodationBuilding(gameModel.getGameTimeGMT()), tileLocationX, tileLocationY);
    }

    private void drawMapObjects() {
        MapObject[][] mapObjects = gameModel.getMapObjects();
        for (int i = 0; i < mapObjects.length; i++) {
            for (int j = 0; j < mapObjects[i].length; j++) {
                if (mapObjects[i][j] != null) {
                    Vector2 screenPos = new Vector2((float) i / gameModel.getTilesWide() * viewport.getWorldWidth(), viewport.getWorldHeight() - (float) j / gameModel.getTilesHigh() * viewport.getWorldHeight());
                    batch.draw(testBuildingTexture, screenPos.x, screenPos.y, 1, 1);
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
        shapeRenderer.rect(background.x, background.y, background.width, background.height);

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

