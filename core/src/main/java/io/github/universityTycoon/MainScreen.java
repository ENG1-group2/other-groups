package io.github.universityTycoon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static java.lang.Math.floorDiv;

public class MainScreen implements Screen {
    SpriteBatch batch;
    ShapeRenderer shapeRenderer;
    FitViewport viewport;

    Texture backgroundTexture;

    Vector2 mousePos;
    boolean mouseDown;

    BitmapFont font;

    float timeSeconds;
    String time;
    boolean isPaused;

    public float getTimeSeconds() {
        return timeSeconds;
    }


    // Everything that goes in create for an application listener, goes in here
    // Meaning all asset/variable assignments
    final Main game;
    public MainScreen(Main main) {
        this.game = main;
        timeSeconds = 300;
        isPaused = false;
    }



    @Override
    public void show() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        viewport = new FitViewport(1920, 1080);

        mousePos = new Vector2(0,0);

        backgroundTexture = new Texture("images/map.png");

        // start the playback of the background music
        // when the screen is shown
        // music.play();
    }

    @Override
    public void render(float v) {
        input();
        logic();
        draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }


    private void input() {
        float delta = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isTouched()) {
            mousePos.set(Gdx.input.getX(), Gdx.input.getY());
            mouseDown = true;
        }
        else {mouseDown = false;}
    }

    private void logic() {
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        float delta = Gdx.graphics.getDeltaTime();
        if (!isPaused) {
            timeSeconds -= delta;
        }
        // This is an example of how the game can be paused
        // To do so in a different file, use gameScreen.timeSeconds
        // and gameScreen.getTimeSeconds
        /*
        if (timeSeconds < 290) {
            pause();
            System.out.println(getTimeSeconds());
        }
        */
        time = String.valueOf(floorDiv((int) timeSeconds, 60)) + ":" + (String.valueOf((int) timeSeconds % 60));

    }


    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();

        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        batch.draw(backgroundTexture, 0, worldHeight - 840, worldWidth, 840);
        game.font.draw(batch, time, 960, 100);
        batch.end();

        if (mouseDown) {
            Rectangle rect = new Rectangle();
            rect.set(mousePos.x, 1080 - mousePos.y, 10, 10);

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
            shapeRenderer.end();
        }
    }


    @Override
    public void pause() {
        isPaused = true;
    }

    @Override
    public void resume() {
        isPaused = false;
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

