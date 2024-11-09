package uk.ac.york.vfc510.unisim;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

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
    private final float TIME_STEP = 1f;

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

    // UI elements
    private Stage stage;
    private Stage endGameStage;
    private Label timeLabel;
    private Label satisfactionLabel;
    private Label fundsLabel;
    private Window endGameWindow;
    private boolean gameEnded = false;
    private Skin skin;
    private BitmapFont font;

    @Override
    public void create() {
        batch = new SpriteBatch();
        map = new TmxMapLoader().load("emptyMap2.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);

        // Initialize game objects
        buildingManager = new BuildingManager();
        player = new Player();
        university = new University();
        timeManager = new TimeManager(1, 0);

        // Set initial camera position
        targetPosition.set(camera.position.x, camera.position.y);

        // Load building textures
        loadBuildingTextures();

        // UI init
        setupUI();

        // Setup input handling
        setupInputProcessors();
    }

    private void setupUI() {
        stage = new Stage(new ScreenViewport());
        endGameStage = new Stage(new ScreenViewport());

        // Create font and style for labels
        font = new BitmapFont();
        font.getData().setScale(1.5f);
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);

        // Create UI labels
        timeLabel = new Label("Time: 0", labelStyle);
        satisfactionLabel = new Label("Satisfaction: " + player.getSatisfaction(), labelStyle);
        fundsLabel = new Label("Funds: $" + player.getFunds(), labelStyle);

        // Create and position the HUD table
        Table hudTable = new Table();
        hudTable.top().right();
        hudTable.setFillParent(true);
        hudTable.pad(10);
        hudTable.add(timeLabel).padBottom(5).row();
        hudTable.add(satisfactionLabel).padBottom(5).row();
        hudTable.add(fundsLabel);

        // Add HUD to stage
        stage.addActor(hudTable);

        // Create end game window
        createEndGameWindow();
    }

    private void createEndGameWindow() {
        Window.WindowStyle windowStyle = new Window.WindowStyle();
        windowStyle.titleFont = font;
        windowStyle.titleFontColor = Color.WHITE;

        // window styles
        endGameWindow = new Window("Game Over!", new Window.WindowStyle(font, Color.WHITE, null));
        endGameWindow.setMovable(false);
        endGameWindow.setModal(false);  // prevent automatic darkening

        // cover screen with background table
        Table backgroundTable = new Table();
        backgroundTable.setFillParent(true);
        backgroundTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Pixmap.createFromFrameBuffer(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight())))));
        backgroundTable.setColor(0, 0, 0, 0.7f);  // Adjust alpha here for transparency
        endGameStage.addActor(backgroundTable);
        backgroundTable.setVisible(false);

        // game visible in background with semi-opacity
        endGameWindow.setColor(0, 0, 0, 0.8f);

        // end game info table
        Table content = new Table();
        content.pad(20);

        // stat labels and styles
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
        Label finalScoreLabel = new Label("Satisfaction Score: " + player.getSatisfaction(), labelStyle);
        content.add(finalScoreLabel).pad(10).row();

        // game end action buttons (restart/exit)
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        buttonStyle.fontColor = Color.WHITE;
        buttonStyle.downFontColor = Color.LIGHT_GRAY;

        TextButton restartButton = new TextButton("Restart", buttonStyle);
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                restartGame();
                backgroundTable.setVisible(false);  // Hide background on restart
            }
        });

        TextButton exitButton = new TextButton("Exit", buttonStyle);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        // add buttons
        Table buttonTable = new Table();
        buttonTable.add(restartButton).pad(10).width(100);
        buttonTable.add(exitButton).pad(10).width(100);
        content.add(buttonTable);

        // add content
        endGameWindow.add(content);
        endGameWindow.pack();

        // enlarge window for a better look
        endGameWindow.setSize(endGameWindow.getWidth() + 40, endGameWindow.getHeight() + 40);

        // center content
        endGameWindow.setPosition(
            (Gdx.graphics.getWidth() - endGameWindow.getWidth()) / 2,
            (Gdx.graphics.getHeight() - endGameWindow.getHeight()) / 2
        );

        // hide until needed
        endGameStage.addActor(endGameWindow);
        endGameWindow.setVisible(false);
    }

    private void setupInputProcessors() {
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(endGameStage);
        multiplexer.addProcessor(new InputAdapter() {
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
        Gdx.input.setInputProcessor(multiplexer);
    }

    private void loadBuildingTextures() {
        // Still TODO
    }

    private void updateUI() {
        int timeLeft = timeManager.getTimeLimit() - TimeManager.getCurrentTime();
        timeLabel.setText("Time Left: " + timeLeft);
        satisfactionLabel.setText("Satisfaction: " + player.getSatisfaction());
        fundsLabel.setText("Funds: $" + player.getFunds());
    }

    private void showEndGameScreen() {
        gameEnded = false;  // Changed to false so the game continues to render
        endGameWindow.setVisible(true);
        // show table for stats
        for (Actor actor : endGameStage.getActors()) {
            if (actor instanceof Table && actor != endGameWindow) {
                actor.setVisible(true);
            }
        }

        // final score update
        Label finalScoreLabel = endGameWindow.findActor("finalScoreLabel");
        if (finalScoreLabel != null) {
            finalScoreLabel.setText("Final Satisfaction Score: " + player.getSatisfaction());
        }
    }

    private void restartGame() {
        // Reset game state
        timeManager = new TimeManager(1, 0);
        player = new Player();
        university = new University();
        gameEnded = false;
        endGameWindow.setVisible(false);
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
        float minX = camera.viewportWidth * camera.zoom / 2;
        float maxX = MAP_WIDTH - minX;
        float minY = camera.viewportHeight * camera.zoom / 2;
        float maxY = MAP_HEIGHT - minY;

        targetPosition.x = MathUtils.clamp(targetPosition.x, minX, maxX);
        targetPosition.y = MathUtils.clamp(targetPosition.y, minY, maxY);

        camera.position.x = MathUtils.lerp(camera.position.x, targetPosition.x, LERP_ALPHA);
        camera.position.y = MathUtils.lerp(camera.position.y, targetPosition.y, LERP_ALPHA);
    }

    private void updateTime() {
        accumulator += Gdx.graphics.getDeltaTime();

        while (accumulator >= TIME_STEP) {
            timeManager.incrementTime();
            updateGameState();
            accumulator -= TIME_STEP;

            if (timeManager.isEndOfGame() && !gameEnded) {
                showEndGameScreen();
            }
        }
    }

    private void updateGameState() {
        player.setFunds(player.getFunds() + player.getIncome());
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
        // Always render the game
        updateTime();
        handleInput();
        updateCamera();
        updateUI();

        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();

        mapRenderer.setView(camera);
        mapRenderer.render();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        buildingManager.render(batch);
        batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        // If the game is ended, render the end game stage on top
        if (endGameWindow.isVisible()) {
            endGameStage.act(Gdx.graphics.getDeltaTime());
            endGameStage.draw();
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        endGameStage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
        map.dispose();
        mapRenderer.dispose();
        stage.dispose();
        endGameStage.dispose();
        font.dispose();
    }
}
