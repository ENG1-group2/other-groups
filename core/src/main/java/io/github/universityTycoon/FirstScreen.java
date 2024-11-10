package io.github.universityTycoon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * FirstScreen is an implementation of the screen interface.
 * It is used for the initial startup screen, and an instance of it is created within ScreenManager.
 *
 * @param batch The batch which draws textures.
 * @param input An instance of the PlayerInputHandler class, which handles inputs.
 * @param viewport The viewport things are displayed on.
 * @param startButton The rectangle used to check for mouse inputs on the start button.
 * @param mousePos The vector position of the mouse.
 * @param mouseDown The updated when the mouse is clicked.
 * @param music The music.
 *
 * @param game An instance of the ScreenManager class, used in the constructor so that stuff works.
 */
public class FirstScreen implements Screen {
    SpriteBatch batch;
    PlayerInputHandler input;
    FitViewport viewport;

    Rectangle startButton;
    Vector2 mousePos;
    boolean mouseDown;

    Music music = Gdx.audio.newMusic(Gdx.files.internal("music/title.mp3"));

    final ScreenManager game;
    public FirstScreen(ScreenManager main) {
        this.game = main;
    }


    /**
     * Show is responsible for setting all variables.
     * It is effectively the constructor.
     */
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
    }

    /**
     * Calls three functions which are used to split up the rendering method.
     *
     * @param v Not sure what this does, but it's part of the screen interface ¯\_(ツ)_/¯
     */
    @Override
    public void render(float v) {
        input();
        logic();
        draw();
    }

    /**
     * Resizes the viewport
     *
     * @param width The new width
     * @param height The new height
     */
    @Override
    public void resize(int width, int height) {

        viewport.update(width, height, true);
    }

    /**
     * Does things when inputs are received.
     */
    private void input() {
        if (input.getKeyJustPressed(Input.Keys.SPACE)) {
            music.stop();
            game.switchToMainScreen();  // Switch to MainScreen
        }

        if (input.getIsMouseDown()) {
            mousePos = input.getMousePos();
            mouseDown = true;

        }
        else {mouseDown = false;}
    }


    /**
     * Deals with the logic of the screen.
     */
    private void logic() {
        Vector3 touch = new Vector3(mousePos.x, mousePos.y, 0);
        viewport.getCamera().unproject(touch);

        if (mouseDown && startButton.contains(touch.x, touch.y)) {
            music.stop();
            game.switchToMainScreen();
        }
    }


    /**
     * Draws all textures on the screen.
     * Note: Batch.begin() and batch.end() must contain all draw statements, and cannot overlap with other begin/ends.
     */
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

