package io.github.universityTycoon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class ScreenManager extends Game {

    public SpriteBatch batch;
    public FitViewport viewport;

    public MainScreen gameScreen;
    public FirstScreen menuScreen;
    public FirstScreen titleScreen;

    public Boolean fullScreen;

    public void create() {

        //Create instances of the screens, this allows access to non-static variables
        gameScreen = new MainScreen(this);
        menuScreen = new FirstScreen(this);
        titleScreen = new FirstScreen(this);

        batch = new SpriteBatch();

        fullScreen = false;







        // use these lines to choose which screen is displayed.
        setScreen(titleScreen);
    }

    public void render() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.F11)){
            fullScreen = Gdx.graphics.isFullscreen();
            Graphics.DisplayMode currentMode = Gdx.graphics.getDisplayMode();
            if (fullScreen)
                Gdx.graphics.setWindowedMode(currentMode.width, currentMode.height);
            else
                Gdx.graphics.setFullscreenMode(currentMode);
        }

        super.render();
    }

    public void dispose() {
        batch.dispose();
        GameModel.font.dispose();
        GameModel.smaller_font.dispose();

    }

    public void switchToMainScreen() {
        setScreen(gameScreen);
    }
}
