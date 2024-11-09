package eng1.unisim;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.InputMultiplexer;

import eng1.unisim.managers.BuildingManager;
import eng1.unisim.managers.InputManager;
import eng1.unisim.managers.TimeManager;
import eng1.unisim.managers.UIManager;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private TimeManager timeManager;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
    private BuildingManager buildingManager;
    private UIManager uiManager;
    private InputManager inputManager;
    private Player player;
    private University university;

    // Constants
    private static final float MAP_WIDTH = 2000f;
    private static final float MAP_HEIGHT = 2000f;
    private static final float TIME_STEP = 1f;
    private static final float CAMERA_SPEED = 500f;
    private static final float CAMERA_ZOOM_SPEED = 0.1f;
    private static final float MIN_ZOOM = 0.5f;
    private float maxZoom; // calc based on map size
    private static final float LERP_ALPHA = 0.1f;

    // State
    private float accumulator = 0f;
    private Building selectedBuilding = null;
    private boolean isPlacingBuilding = false;
    private final Vector2 targetPosition = new Vector2();

    private Vector3 cursorPosition;

    @Override
    public void create() {
        initializeGame();
        setupCamera();
        setupManagers();
        setupInput();
        cursorPosition = new Vector3();
    }

    private void initializeGame() {
        batch = new SpriteBatch();
        map = new TmxMapLoader().load("emptyGroundMap.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        player = new Player();
        university = new University();
        timeManager = new TimeManager(1, 0);
    }

    private void setupCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);
        targetPosition.set(camera.position.x, camera.position.y);

        // calc maximum zoom level based on map size and screen size
        float mapAspect = MAP_WIDTH / MAP_HEIGHT;
        float screenAspect = Gdx.graphics.getWidth() / (float)Gdx.graphics.getHeight();

        if (mapAspect > screenAspect) {
            // try and fit to width (might not work)
            maxZoom = MAP_WIDTH / camera.viewportWidth;
        } else {
            // try and fit to height (might not work)
            maxZoom = MAP_HEIGHT / camera.viewportHeight;
        }

        // initial map position
        camera.position.set(MAP_WIDTH / 2f, MAP_HEIGHT / 2f, 0);
        targetPosition.set(camera.position.x, camera.position.y);
    }

    private void setupManagers() {
        uiManager = new UIManager(player, this::restartGame, this::setSelectedBuilding);
        buildingManager = new BuildingManager(uiManager);
        inputManager = new InputManager(camera, this::placeSelectedBuilding, targetPosition, maxZoom);
    }

    private void setupInput() {
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(uiManager.getStage());
        multiplexer.addProcessor(uiManager.getEndGameStage());
        multiplexer.addProcessor(inputManager);
        Gdx.input.setInputProcessor(multiplexer);
    }

    private void updateGameState() {
        player.setFunds(player.getFunds() + player.getIncome());
    }

    private void updateCamera() {
        handleCameraInput();
        applyCameraBounds();
        smoothCameraMovement();
    }

    private void handleCameraInput() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        float effectiveSpeed = CAMERA_SPEED * camera.zoom * deltaTime;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) targetPosition.y += effectiveSpeed;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) targetPosition.y -= effectiveSpeed;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) targetPosition.x -= effectiveSpeed;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) targetPosition.x += effectiveSpeed;
    }

    private void applyCameraBounds() {
        // visible map area
        float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
        float effectiveViewportHeight = camera.viewportHeight * camera.zoom;

        // keep camera within map
        float minX = effectiveViewportWidth / 2f;
        float maxX = MAP_WIDTH - minX;
        float minY = effectiveViewportHeight / 2f;
        float maxY = MAP_HEIGHT - minY;

        // if zoomed out far, change bounds to prevent flickering
        if (minX > maxX) {
            // center on x axis
            targetPosition.x = MAP_WIDTH / 2f;
        } else {
            targetPosition.x = MathUtils.clamp(targetPosition.x, minX, maxX);
        }

        if (minY > maxY) {
            // center on y axis
            targetPosition.y = MAP_HEIGHT / 2f;
        } else {
            targetPosition.y = MathUtils.clamp(targetPosition.y, minY, maxY);
        }
    }

    private void smoothCameraMovement() {
        camera.position.x = MathUtils.lerp(camera.position.x, targetPosition.x, LERP_ALPHA);
        camera.position.y = MathUtils.lerp(camera.position.y, targetPosition.y, LERP_ALPHA);
    }

    private void updateTime() {
        accumulator += Gdx.graphics.getDeltaTime();

        while (accumulator >= TIME_STEP) {
            timeManager.incrementTime();
            updateGameState();
            accumulator -= TIME_STEP;

            if (timeManager.isEndOfGame()) {
                uiManager.showEndGameScreen();
            }
        }
    }

    private void restartGame() {
        timeManager = new TimeManager(1, 0);
        player = new Player();
        university = new University();
        setupManagers();
        setupInput();
    }

    private void placeSelectedBuilding(float worldX, float worldY) {
        if (selectedBuilding != null) {
            if (player.getFunds() >= selectedBuilding.getCost()) {
                if (buildingManager.placeBuilding(selectedBuilding, worldX, worldY)) {
                    player.placeBuilding(selectedBuilding);
                    university.addBuilding(selectedBuilding);
                    isPlacingBuilding = false;
                    inputManager.setPlacingBuilding(false);
                    selectedBuilding = null;
                }
            } else {
                // show insufficient funds message for 2 seconds
                String message = "Insufficient funds! Need $" + selectedBuilding.getCost() +
                    " (Current: $" + player.getFunds() + ")";
                uiManager.showNotification(message);
            }
        }
    }

    public void setSelectedBuilding(Building building) {
        this.selectedBuilding = building;
        this.isPlacingBuilding = true;
        inputManager.setPlacingBuilding(true);
        buildingManager.setSelectedBuilding(building);
    }

    private void updateCursorPosition() {
        if (isPlacingBuilding) {
            float screenX = Gdx.input.getX();
            float screenY = Gdx.input.getY();
            cursorPosition = camera.unproject(new Vector3(screenX, screenY, 0));
        }
    }

    @Override
    public void render() {
        updateTime();
        updateCamera();
        updateCursorPosition();

        // update time left and render
        int timeLeft = timeManager.getTimeLimit() - TimeManager.getCurrentTime();
        uiManager.updateHUD(timeLeft);

        // render the game
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();

        mapRenderer.setView(camera);
        mapRenderer.render();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        buildingManager.render(batch, cursorPosition);
        batch.end();

        uiManager.render();
    }

    @Override
    public void resize(int width, int height) {
        uiManager.resize(width, height);
    }

    @Override
    public void dispose() {
        batch.dispose();
        map.dispose();
        mapRenderer.dispose();
        uiManager.dispose();
        buildingManager.dispose();
    }
}
