package io.github.universityTycoon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static java.lang.Math.floorDiv;

public class MainScreen implements Screen {
    SpriteBatch batch;
    ShapeRenderer shapeRenderer;
    FitViewport viewport;

    Texture backgroundTexture;

    Rectangle[] buildingButtons;
    int noBuildingTypes = 4;

    int tileSize = 30;
    Rectangle[][] activeTiles;

    Vector2 mousePos;
    boolean mouseDown;
    PlayerInputHandler playerInputHandler;

    BitmapFont font;

    final float START_TIME_SECONDS = 300;
    public float timeRemainingSeconds = START_TIME_SECONDS;

    String time;
    boolean isPaused;

    Music music = Gdx.audio.newMusic(Gdx.files.internal("music/main.mp3"));

    public float getTimeSeconds() {
        return timeRemainingSeconds;
    }

    // Everything that goes in create for an application listener, goes in here
    // Meaning all asset/variable assignments
    final ScreenManager game;
    public MainScreen(ScreenManager main) {
        this.game = main;
        isPaused = false;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        viewport = new FitViewport(16, 9);

        mousePos = new Vector2(0,0);
        playerInputHandler = new PlayerInputHandler();

        backgroundTexture = new Texture("images/map.png");
        buildingButtons = new Rectangle[noBuildingTypes];
        activeTiles = new Rectangle[1920 / tileSize][840 / tileSize];

        // start the playback of the background music when the screen is shown
        music.setVolume(0.5f);
        music.setLooping(true);
        music.play();
    }

    @Override
    public void render(float v) {
        input();
        logic();
        batch.begin();
        draw();
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }


    private void input() {
        float delta = Gdx.graphics.getDeltaTime();

        if (playerInputHandler.getIsPauseJustPressed()) {
            if (isPaused) {
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
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        float delta = Gdx.graphics.getDeltaTime();
        if (!isPaused) {
            timeRemainingSeconds -= delta;
        }
        // This is an example of how the game can be paused
        // To do so in a Main, use gameScreen.timeSeconds
        // and gameScreen.getTimeSeconds
        /*
        if (timeSeconds < 290) {
            pause();
            System.out.println(getTimeSeconds());
        }
        */
        time = String.valueOf(floorDiv((int) timeRemainingSeconds, 60)) + ":" + (String.valueOf((int) timeRemainingSeconds % 60));

    }


    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();


        batch.setProjectionMatrix(viewport.getCamera().combined);


        batch.draw(backgroundTexture, 0, 2, 16, 7);
        game.font.draw(batch, time, 7.6f, 8.5f);
        drawBuildingMenu();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Rectangle[] activeTile : activeTiles) {
            for (Rectangle tile : activeTile) {
                if (tile != null) {
                    shapeRenderer.setColor(Color.RED);
                    shapeRenderer.rect(tile.x, tile.y, tile.width, tile.height);
                }
            }
        }
        shapeRenderer.end();

        if (mouseDown && mousePos.y < 810) {
            createTile();
        }
    }

    private void createTile() {
        int tileLocationX = ((int) mousePos.x / tileSize);
        int tileLocationY = ((int) mousePos.y / tileSize);
        Vector2 screenLocation = new Vector2(tileLocationX * tileSize, tileLocationY * tileSize);

        Rectangle rect = new Rectangle();
        rect.set(screenLocation.x, 1080 - screenLocation.y - tileSize, tileSize, tileSize);
        activeTiles[tileLocationX][tileLocationY] = rect;


        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
        shapeRenderer.end();
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
        isPaused = true;
        music.pause();
    }

    @Override
    public void resume() {
        isPaused = false;
        music.play();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public float getTimeElapsed() {
        return START_TIME_SECONDS - timeRemainingSeconds;
    }
}

