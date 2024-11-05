package io.github.universityTycoon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static java.lang.Math.floorDiv;

public class MainScreen implements Screen {
    SpriteBatch batch;

    FitViewport viewport;

    Vector2 touchPos;
    Array<Sprite> dropSprites;

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
        viewport = new FitViewport(16, 9);

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
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPos);
            // ABuilding.setCenterX(touchPos.x); use this to place a building with the mouse
        }

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

    game.font.draw(batch, time, 1f, 1.5f);

    batch.end();
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

