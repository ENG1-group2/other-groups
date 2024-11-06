package io.github.universityTycoon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Main extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    public FitViewport viewport;

    public MainScreen gameScreen;
    public FirstScreen menuScreen;

    public void create() {

        //Create instances of the screens, this allows access to non-static variables
        gameScreen = new MainScreen(this);
        menuScreen = new FirstScreen(this);

        batch = new SpriteBatch();

        // use libGDX's default font
        font = new BitmapFont(Gdx.files.internal("ui/font.fnt"),
            Gdx.files.internal("ui/font.png"), false);

        viewport = new FitViewport(16, 9);

        //font has 15pt, but we need to scale it to our viewport by ratio of viewport height to screen height
        font.setUseIntegerPositions(false);
        // Changing the 1 in the line below can change the font size, though only in
        // integer increments, so probably better to use a non-default font.
        font.getData().setScale(1 *(viewport.getWorldHeight() / Gdx.graphics.getHeight()));


        // use these lines to choose which screen is displayed.
        setScreen(menuScreen);
        setScreen(gameScreen);
    }

    public void render() {
        font.getData().setScale(0.003f, 0.003f);
        super.render();
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
