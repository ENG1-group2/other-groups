package io.github.universityTycoon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
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
    final ScreenManager game;
    public FirstScreen(ScreenManager main) {
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
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            game.switchToMainScreen();  // Switch to MainScreen
        }
        logic();
        draw();
    }

    @Override
    public void resize(int width, int height) {

        viewport.update(width, height, true);
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
        batch.draw(start, 6.05f, 2.5f, 4, 0.55f);

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

