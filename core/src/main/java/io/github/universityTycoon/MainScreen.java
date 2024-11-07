package io.github.universityTycoon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static java.lang.Math.floorDiv;

public class MainScreen implements Screen {

    GameModel gameModel;
    SpriteBatch batch;
    ShapeRenderer shapeRenderer;

    Texture backgroundTexture;

    Rectangle[] buildingButtons;


    Vector2 mousePos;
    boolean mouseDown;


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
        GameModel.viewport = new FitViewport(16, 9);

        mousePos = new Vector2(0,0);

        backgroundTexture = new Texture("images/map.png");
        buildingButtons = new Rectangle[gameModel.getNoBuildingTypes()];
        gameModel.activeTiles = new Rectangle[1920 / gameModel.getTileSize()][840 / gameModel.getTileSize()];

        // start the playback of the background music when the screen is shown
        music.setVolume(0.5f);
        music.setLooping(true);
        music.play();
    }

    @Override
    public void render(float v) {
        input();
        logic();
        draw();
    }

    @Override
    public void resize(int width, int height) {
        GameModel.viewport.update(width, height, true);
    }


    private void input() {

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.P)) {
            if (gameModel.getIsPaused()) {
                resume();
            } else {
                pause();
            }  // Toggle the pause state
        }

        if (Gdx.input.isTouched()) {
            mousePos.set(Gdx.input.getX(), Gdx.input.getY());
            mouseDown = true;
        }
        else {mouseDown = false;}
    }

    private void logic() {

        if (!gameModel.getIsPaused()) {
            gameModel.timeRemainingSeconds -= Gdx.graphics.getDeltaTime();
        }
        time = String.valueOf(floorDiv((int) gameModel.getTimeRemainingSeconds(), 60))
            + ":" + String.format("%02d", (int) gameModel.getTimeRemainingSeconds() % 60);

    }


    private void draw() {

        batch.begin();

        ScreenUtils.clear(Color.BLACK);
        GameModel.viewport.apply();
        batch.setProjectionMatrix(GameModel.viewport.getCamera().combined);


        batch.draw(backgroundTexture, 0, 2, 16, 7);
        game.font.draw(batch, time, 7.6f, 8.5f);

        drawBuildingMenu();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Rectangle[] activeTile : gameModel.getActiveTiles()) {
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

        batch.end();
    }

    private void createTile() {
        int tileLocationX = ((int) mousePos.x / gameModel.getTileSize());
        int tileLocationY = ((int) mousePos.y / gameModel.getTileSize());
        Vector2 screenLocation = new Vector2(tileLocationX * gameModel.getTileSize(), tileLocationY * gameModel.getTileSize());

        Rectangle rect = new Rectangle();
        rect.set(screenLocation.x, 1080 - screenLocation.y - gameModel.getTileSize(),
            gameModel.getTileSize(), gameModel.getTileSize());
        gameModel.activeTiles[tileLocationX][tileLocationY] = rect;


        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
        shapeRenderer.end();
    }

    private void drawBuildingMenu() {
        Rectangle background = new Rectangle();
        background.set(0, 0, GameModel.viewport.getScreenWidth(), 244);

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

