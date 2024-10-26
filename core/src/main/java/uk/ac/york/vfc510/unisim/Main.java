package uk.ac.york.vfc510.unisim;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import uk.ac.york.vfc510.unisim.managers.TimeManager;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
// TODO: Create instances for buildings and events
// TODO: Create UI and map
// TODO: make inputs effect the game
// TODO: create game logic e.g. update methods

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;
    private TimeManager timeManager;

    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(image, 140, 210);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
