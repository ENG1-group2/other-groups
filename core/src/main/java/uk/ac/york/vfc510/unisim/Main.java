package uk.ac.york.vfc510.unisim;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import uk.ac.york.vfc510.unisim.managers.TimeManager;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
// TODO: Create instances for buildings and events
// TODO: Create UI
// TODO: make inputs effect the game
// TODO: create game logic e.g. update methods
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private TimeManager timeManager;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;

    @Override
    public void create() {
        batch = new SpriteBatch();
        map = new TmxMapLoader().load("C:\\ENG1-UniSim\\assets\\emptyMap.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720); // Set the camera to your desired viewport size
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        mapRenderer.setView(camera);
        mapRenderer.render();
        batch.begin();
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        map.dispose();
        mapRenderer.dispose();
    }
}
