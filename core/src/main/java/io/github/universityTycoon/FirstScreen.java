package io.github.universityTycoon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;


public class FirstScreen implements Screen {
    SpriteBatch batch;
    PlayerInputHandler input;
    FitViewport viewport;

    Rectangle startButton;
    Vector2 mousePos;
    boolean mouseDown;
    PlayerInputHandler playerInputHandler;

    Vector2 touchPos;
    Array<Sprite> dropSprites;

    Music music = Gdx.audio.newMusic(Gdx.files.internal("music/title.mp3"));

    final ScreenManager game;
    public FirstScreen(ScreenManager main) {
        this.game = main;
    }


    @Override
    public void show() {
        batch = new SpriteBatch();
        viewport = new FitViewport(16, 9);
        input = new PlayerInputHandler();

        music.setVolume(0.3f);
        music.setLooping(true);
        music.play();

        startButton = new Rectangle();
        mousePos = new Vector2(0,0);
        playerInputHandler = new PlayerInputHandler();
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
        if (input.getKeyJustPressed(Input.Keys.SPACE)) {
            music.stop();
            game.switchToMainScreen();  // Switch to MainScreen
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

        if (mouseDown && startButton.contains(touch.x, touch.y)) {
            music.stop();
            game.switchToMainScreen();
        }
    }




    private void draw() {
        batch.begin();

        ScreenUtils.clear(Color.BLACK);
        Texture background = new Texture(Gdx.files.internal("images/title_page.png"));
        Texture logo = new Texture(Gdx.files.internal("images/logo.png"));
        Texture start = new Texture(Gdx.files.internal("images/start.png"));

        startButton.set(6.05f, 2.5f, 4f, 0.55f);


        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);




        batch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
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

