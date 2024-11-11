package eng1.unisim;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import eng1.unisim.managers.BuildingManager;
import eng1.unisim.managers.InputManager;
import eng1.unisim.managers.TimeManager;
import eng1.unisim.managers.UIManager;
import eng1.unisim.models.Building;
import eng1.unisim.models.Player;
import eng1.unisim.models.University;

// main game class that handles core game logic and rendering
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

    // game configuration constants
    private static final float MAP_WIDTH = 2000f;  // width of game world in pixels
    private static final float MAP_HEIGHT = 2000f; // height of game world in pixels
    private static final float TIME_STEP = 1f;     // how often the game state updates (in seconds)
    private static final float CAMERA_SPEED = 500f; // how fast the camera moves when using WASD
    private float maxZoom;                         // furthest zoom level, calculated based on map size
    private static final float LERP_ALPHA = 0.1f;  // smoothing factor for camera movement (0-1)

    // game state variables
    private float accumulator = 0f;                // tracks time between updates
    private Building selectedBuilding = null;       // building currently selected for placement
    private boolean isPlacingBuilding = false;     // whether player is currently placing a building
    private final Vector2 targetPosition = new Vector2(); // where the camera is trying to move to
    private boolean isPaused = true;               // tracks if game is paused

    private Vector3 cursorPosition;

    @Override
    public void create() {
        initializeGame();
        setupCamera();
        setupManagers();
        setupInput();
        cursorPosition = new Vector3();
        centerCameraOnMap();
        uiManager.showPauseMenu(); // Show pause menu on startup
    }

    private void centerCameraOnMap() {
        // map dimensions
        float mapWidth = map.getProperties().get("width", Integer.class) * map.getProperties().get("tilewidth", Integer.class);
        float mapHeight = map.getProperties().get("height", Integer.class) * map.getProperties().get("tileheight", Integer.class);

        // camera starts at center of map
        camera.position.set(mapWidth / 2, mapHeight / 2, 0);
        targetPosition.set(mapWidth / 2, mapHeight / 2); // Set targetPosition to the center as well
        camera.update();
    }

    private void initializeGame() {
        // create core game objects and load initial map
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

        // math to find center of map
        float mapWidth = MAP_WIDTH;
        float mapHeight = MAP_HEIGHT;
        float centerX = mapWidth / 2f;
        float centerY = mapHeight / 2f;

        // position camera center of map
        camera.position.set(centerX, centerY, 0);
        targetPosition.set(centerX, centerY);

        // maximum zoom based of map length & window size
        float mapAspect = mapWidth / mapHeight;
        float screenAspect = Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();

        if (mapAspect > screenAspect) {
            // adjust to width
            maxZoom = mapWidth / camera.viewportWidth;
        } else {
            // adjust to height
            maxZoom = mapHeight / camera.viewportHeight;
        }
        camera.update();
    }

    private void setupManagers() {
        uiManager = new UIManager(player, this::restartGame, this::setSelectedBuilding, this::togglePause);
        buildingManager = new BuildingManager(uiManager);
        inputManager = new InputManager(camera, this::placeSelectedBuilding, targetPosition, maxZoom);
    }

    private void setupInput() {
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(uiManager.getPauseMenuStage());
        multiplexer.addProcessor(uiManager.getStage());
        multiplexer.addProcessor(uiManager.getEndGameStage());
        multiplexer.addProcessor(inputManager);
        Gdx.input.setInputProcessor(multiplexer);
    }

    private void updateGameState() {
        // update player's funds based on income (called each time step)
        player.setFunds(player.getFunds() + player.getIncome());
    }

    private void updateCamera() {
        // handle camera movement, boundaries, and smooth transitions
        handleCameraInput();
        applyCameraBounds();
        smoothCameraMovement();
    }

    private void handleCameraInput() {
        // move camera based on WASD keys, accounting for zoom level
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
        if (!isPaused) {
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
    }

    private void restartGame() {
        timeManager = new TimeManager(1, 0);
        player = new Player();
        university = new University();
        setupManagers();
        setupInput();
        isPaused = true; // Pause the game when restarting
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
                // buildingManager now handles the invalid placement notification
            } else {
                // show error if player can't afford the building
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

    public void togglePause() {
        isPaused = !isPaused;
    }

    @Override
    public void render() {
        // main game loop - update game state and render everything
        updateTime();
        updateCamera();
        updateCursorPosition();

        // update ui with remaining time
        int timeLeft = timeManager.getTimeLimit() - TimeManager.getCurrentTime();
        uiManager.updateHUD(timeLeft);

        // clear screen and render map, buildings, and ui
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
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
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
