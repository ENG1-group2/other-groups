package io.github.universityTycoon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    public FitViewport viewport;
    float timeSeconds;
    String time;
    PlayerInputHandler playerInputHandler = new PlayerInputHandler();
    GameModel gameModel = new GameModel(playerInputHandler);
    UILogic uiLogic = new UILogic();

    @Override
    public void create() {
        setScreen(new FirstScreen());
        batch = new SpriteBatch();
        // use libGDX's default font
        font = new BitmapFont();
        viewport = new FitViewport(16, 9);

        //font has 15pt, but we need to scale it to our viewport by ratio of viewport height to screen height
        font.setUseIntegerPositions(false);
        font.getData().setScale(viewport.getWorldHeight() / Gdx.graphics.getHeight());

    }



    @Override
    public void render() {
        timeSeconds = 300;
        timeSeconds -= Gdx.graphics.getDeltaTime();
        time = String.valueOf(timeSeconds / 60) + ":" + (String.valueOf(timeSeconds % 60));
        //All rendering happens between batch.begin() and batch.end()
        batch.begin();
        font.draw(batch, time, 1, 1.5f);
        batch.end();
    }
}
