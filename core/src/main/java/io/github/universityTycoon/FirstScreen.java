package io.github.universityTycoon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;


public class FirstScreen implements Screen {
    SpriteBatch batch;

    FitViewport viewport;

    Vector2 touchPos;
    Array<Sprite> dropSprites;

    BitmapFont font;


    // Everything that goes in create for an application listener, goes in here
    // Meaning all asset/variable assignments
    final Main game;
    public FirstScreen(Main main) {
        this.game = main;

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

    }


    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        Texture background = new Texture(Gdx.files.internal("images/title_page.png"));
        Texture logo = new Texture(Gdx.files.internal("images/logo.png"));
        Texture start = new Texture(Gdx.files.internal("images/start.png"));

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);


        batch.begin();

        batch.draw(background, 0, 0, worldWidth, worldHeight);
        batch.draw(logo, 6, 4.5f, 4, 4);
        batch.draw(start, 7.1f, 2.5f, 2, 0.75f);

        batch.end();
    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

