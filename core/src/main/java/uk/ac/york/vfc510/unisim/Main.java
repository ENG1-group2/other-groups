package uk.ac.york.vfc510.unisim;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import uk.ac.york.vfc510.unisim.managers.TimeManager;
import uk.ac.york.vfc510.unisim.managers.BuildingManager;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private TimeManager timeManager;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;
    private BuildingManager buildingManager;
    private Player player;
    private University university;

    // Time management
    private float accumulator = 0f;
    private final float TIME_STEP = 1f; // One second real time per game time step

    // Camera movement variables
    private final float CAMERA_SPEED = 500f;
    private final float CAMERA_ZOOM_SPEED = 0.1f;
    private final float MIN_ZOOM = 0.5f;
    private final float MAX_ZOOM = 3f;
    private boolean isDragging = false;
    private float lastX, lastY;

    // Camera boundaries
    private final float MAP_WIDTH = 2000f;
    private final float MAP_HEIGHT = 2000f;
    private final Vector2 targetPosition = new Vector2();
    private final float LERP_ALPHA = 0.1f;

    // Building placement
    private Building selectedBuilding = null;
    private boolean isPlacingBuilding = false;

    @Override
    public void create() {
        batch = new SpriteBatch();
        map = new TmxMapLoader().load("emptyMap.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);

        // Initialize game objects
        buildingManager = new BuildingManager();
        player = new Player();
        university = new University();
        timeManager = new TimeManager(1, 0); // Initialize with timeStep=1 and currentTime=0

        // Set initial camera position
        targetPosition.set(camera.position.x, camera.position.y);

        // Load building textures
        loadBuildingTextures();

        setupInputProcessor();
    }

    private void loadBuildingTextures() {
        // TODO: load in building textures to be used
    }

    private void setupInputProcessor() {
        Gdx.input.setInputProcessor(new com.badlogic.gdx.InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (button == Input.Buttons.LEFT) {
                    if (isPlacingBuilding) {
                        Vector3 worldCoords = camera.unproject(new Vector3(screenX, screenY, 0));
                        placeSelectedBuilding(worldCoords.x, worldCoords.y);
                        return true;
                    } else {
                        isDragging = true;
                        lastX = screenX;
                        lastY = screenY;
                    }
                }
                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if (button == Input.Buttons.LEFT) {
                    isDragging = false;
                }
                return true;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                if (isDragging && !isPlacingBuilding) {
                    float deltaX = (screenX - lastX) * camera.zoom;
                    float deltaY = (lastY - screenY) * camera.zoom;

                    targetPosition.x = camera.position.x - deltaX * (camera.viewportWidth / Gdx.graphics.getWidth());
                    targetPosition.y = camera.position.y - deltaY * (camera.viewportHeight / Gdx.graphics.getHeight());

                    lastX = screenX;
                    lastY = screenY;
                }
                return true;
            }

            @Override
            public boolean scrolled(float amountX, float amountY) {
                float newZoom = camera.zoom + amountY * CAMERA_ZOOM_SPEED;
                camera.zoom = MathUtils.clamp(newZoom, MIN_ZOOM, MAX_ZOOM);
                return true;
            }
        });
    }

    private void updateTime() {
        // Update game time based on real time
        accumulator += Gdx.graphics.getDeltaTime();

        // Update game time when accumulator reaches TIME_STEP
        while (accumulator >= TIME_STEP) {
            timeManager.incrementTime();
            updateGameState();
            accumulator -= TIME_STEP;

            // Check for game end condition
            if (timeManager.isEndOfGame()) {
                // Handle game end
                // TODO: Show end game splash and stop user input
                System.out.println("Game Over! Time limit reached.");
            }
        }
    }

    private void updateGameState() {
        // Update player's funds with income
        player.setFunds(player.getFunds() + player.getIncome());

        // TODO: add stuff below
        // - Check for random events
        // - Update building effects
        // - Update satisfaction levels
    }

    private void handleInput() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        float effectiveSpeed = CAMERA_SPEED * camera.zoom * deltaTime;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) targetPosition.y += effectiveSpeed;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) targetPosition.y -= effectiveSpeed;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) targetPosition.x -= effectiveSpeed;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) targetPosition.x += effectiveSpeed;
    }

    private void updateCamera() {
        // Clamp target position to map boundaries
        float minX = camera.viewportWidth * camera.zoom / 2;
        float maxX = MAP_WIDTH - minX;
        float minY = camera.viewportHeight * camera.zoom / 2;
        float maxY = MAP_HEIGHT - minY;

        targetPosition.x = MathUtils.clamp(targetPosition.x, minX, maxX);
        targetPosition.y = MathUtils.clamp(targetPosition.y, minY, maxY);

        // Smooth camera movement
        camera.position.x = MathUtils.lerp(camera.position.x, targetPosition.x, LERP_ALPHA);
        camera.position.y = MathUtils.lerp(camera.position.y, targetPosition.y, LERP_ALPHA);
    }

    public void setSelectedBuilding(Building building) {
        this.selectedBuilding = building;
        this.isPlacingBuilding = true;
    }

    private void placeSelectedBuilding(float worldX, float worldY) {
        if (selectedBuilding != null && player.getFunds() >= selectedBuilding.getCost()) {
            if (buildingManager.placeBuilding(selectedBuilding, worldX, worldY)) {
                player.placeBuilding(selectedBuilding);
                university.addBuilding(selectedBuilding);
                isPlacingBuilding = false;
                selectedBuilding = null;
            }
        }
    }

    @Override
    public void render() {
        // Update time and game state
        updateTime();

        // Handle input and camera
        handleInput();
        updateCamera();

        // Render the game
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();

        mapRenderer.setView(camera);
        mapRenderer.render();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        buildingManager.render(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        map.dispose();
        mapRenderer.dispose();
    }
}
