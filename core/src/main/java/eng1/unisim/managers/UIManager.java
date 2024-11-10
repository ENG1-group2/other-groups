package eng1.unisim.managers;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import eng1.unisim.Building;
import eng1.unisim.Player;

public class UIManager {
    private Stage stage;
    private Stage endGameStage;
    private Label timeLabel;
    private Label satisfactionLabel;
    private Label fundsLabel;
    private Window endGameWindow;
    private BitmapFont font;
    private final Player player;
    private Table backgroundTable;
    private Runnable onRestartGame;
    private BuildingSelectionCallback buildingSelectionCallback;
    private Runnable onPauseToggle;
    private HashMap<String, Label> buildingCounters = new HashMap<>();
    private HashMap<String, Integer> buildingCounts = new HashMap<>();
    private static final String[] BUILDING_TYPES = { "Accommodation", "Learning", "Food", "Recreation" };
    private Label notificationLabel;
    private float notificationTimer = 0;
    private static final float NOTIFICATION_DURATION = 2f; // show messages for 2 seconds

    public interface BuildingSelectionCallback {
        void onBuildingSelected(Building building);
    }

    public UIManager(Player player, Runnable onRestartGame, BuildingSelectionCallback buildingCallback,
            Runnable onPauseToggle) {
        this.player = player;
        this.onRestartGame = onRestartGame;
        this.buildingSelectionCallback = buildingCallback;
        this.onPauseToggle = onPauseToggle;
        setupUI();
    }

    private void setupUI() {
        stage = new Stage(new ScreenViewport());
        endGameStage = new Stage(new ScreenViewport());

        font = new BitmapFont();
        font.getData().setScale(1.5f);

        createHUD();
        createBuildingInventory();
        createEndGameWindow();
        createNotificationSystem();
        createPauseButton();
    }

    private void createHUD() {
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
        timeLabel = new Label("Time: 0", labelStyle);
        satisfactionLabel = new Label("Satisfaction: " + player.getSatisfaction(), labelStyle);
        fundsLabel = new Label("Funds: $" + player.getFunds(), labelStyle);

        Table hudTable = new Table();
        hudTable.top().right();
        hudTable.setFillParent(true);
        hudTable.pad(10);
        hudTable.add(timeLabel).padBottom(5).row();
        hudTable.add(satisfactionLabel).padBottom(5).row();
        hudTable.add(fundsLabel);

        stage.addActor(hudTable);
    }

    // TODO improve look of pause button
    private void createPauseButton() {
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        buttonStyle.fontColor = Color.WHITE;
        buttonStyle.downFontColor = Color.LIGHT_GRAY;

        TextButton pauseButton = new TextButton("Resume", buttonStyle);
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                onPauseToggle.run();
                pauseButton.setText(pauseButton.getText().toString().equals("Pause") ? "Resume" : "Pause");
            }
        });

        Table pauseTable = new Table();
        pauseTable.top().left();
        pauseTable.setFillParent(true);
        pauseTable.pad(10);
        pauseTable.add(pauseButton).padBottom(5).row();

        stage.addActor(pauseTable);
    }

    private void createBuildingInventory() {
        // start building counts from 0
        for (String type : BUILDING_TYPES) {
            buildingCounts.put(type, 0);
        }

        // vertical button display for buildings
        Table buildingTable = new Table();
        buildingTable.top().left();
        buildingTable.setFillParent(true);
        buildingTable.pad(10);

        // accommodation building button
        Texture accommodationTexture = new Texture(Gdx.files.internal("buildings/accommodation.png"));
        ImageButton.ImageButtonStyle accommodationStyle = new ImageButton.ImageButtonStyle();
        accommodationStyle.imageUp = new TextureRegionDrawable(new TextureRegion(accommodationTexture));
        accommodationStyle.imageChecked = new TextureRegionDrawable(new TextureRegion(accommodationTexture));
        accommodationStyle.imageChecked.setMinWidth(90); // Slightly smaller when selected
        accommodationStyle.imageChecked.setMinHeight(90);

        ImageButton accommodationButton = new ImageButton(accommodationStyle);
        accommodationButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Building accommodation = new Building("Accommodation", 50000, 10, 5000);
                buildingSelectionCallback.onBuildingSelected(accommodation);
                accommodationButton.setChecked(true);
            }
        });

        // dining building button
        Texture diningTexture = new Texture(Gdx.files.internal("buildings/dining.png"));
        ImageButton.ImageButtonStyle diningStyle = new ImageButton.ImageButtonStyle();
        diningStyle.imageUp = new TextureRegionDrawable(new TextureRegion(diningTexture));
        diningStyle.imageChecked = new TextureRegionDrawable(new TextureRegion(diningTexture));
        diningStyle.imageChecked.setMinWidth(90); // Slightly smaller when selected
        diningStyle.imageChecked.setMinHeight(90);

        ImageButton diningButton = new ImageButton(diningStyle);

        diningButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Building dining = new Building("Dining", 50000, 10, 5000);
                buildingSelectionCallback.onBuildingSelected(dining);
                diningButton.setChecked(true);
            }
        });

        // learning building button
        Texture learningTexture = new Texture(Gdx.files.internal("buildings/learning.png"));
        ImageButton.ImageButtonStyle learningStyle = new ImageButton.ImageButtonStyle();
        learningStyle.imageUp = new TextureRegionDrawable(new TextureRegion(learningTexture));
        learningStyle.imageChecked = new TextureRegionDrawable(new TextureRegion(learningTexture));
        learningStyle.imageChecked.setMinWidth(90); // Slightly smaller when selected
        learningStyle.imageChecked.setMinHeight(90);

        ImageButton learningButton = new ImageButton(learningStyle);

        learningButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Building learning = new Building("Learning", 50000, 10, 5000);
                buildingSelectionCallback.onBuildingSelected(learning);
                learningButton.setChecked(true);
            }
        });

        // recreation building button
        Texture recreationTexture = new Texture(Gdx.files.internal("buildings/recreationPlaceholder.png"));
        ImageButton.ImageButtonStyle recreationStyle = new ImageButton.ImageButtonStyle();
        recreationStyle.imageUp = new TextureRegionDrawable(new TextureRegion(recreationTexture));
        recreationStyle.imageChecked = new TextureRegionDrawable(new TextureRegion(recreationTexture));
        recreationStyle.imageChecked.setMinWidth(90); // Slightly smaller when selected
        recreationStyle.imageChecked.setMinHeight(90);

        ImageButton recreationButton = new ImageButton(recreationStyle);

        recreationButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Building recreation = new Building("Recreation", 50000, 10, 5000);
                buildingSelectionCallback.onBuildingSelected(recreation);
                recreationButton.setChecked(true);
            }
        });

        // Add placement instruction label (not shown)
        // TODO: change cursor pointer on buttons, and to crosshair on move building
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
        Label placementLabel = new Label("Click to place building", labelStyle);
        placementLabel.setVisible(false);

        // counters
        // counters
        Label.LabelStyle counterStyle = new Label.LabelStyle(font, Color.WHITE);
        Label accommodationCounter = new Label("0", counterStyle);
        Label diningCounter = new Label("0", counterStyle);
        Label learningCounter = new Label("0", counterStyle);
        Label recreationCounter = new Label("0", counterStyle);

        buildingCounters.put("Accommodation", accommodationCounter);
        buildingCounters.put("Dining", diningCounter);
        buildingCounters.put("Learning", learningCounter);
        buildingCounters.put("Recreation", recreationCounter);

        // Add buttons and counters to the table
        buildingTable.add(accommodationButton).size(100, 100).pad(5);
        buildingTable.add(accommodationCounter).pad(5).row();
        buildingTable.add(diningButton).size(100, 100).pad(5);
        buildingTable.add(diningCounter).pad(5).row();
        buildingTable.add(learningButton).size(100, 100).pad(5);
        buildingTable.add(learningCounter).pad(5).row();
        buildingTable.add(recreationButton).size(100, 100).pad(5);
        buildingTable.add(recreationCounter).pad(5).row();

        stage.addActor(buildingTable);
    }


    public void updateBuildingCount(String buildingType, int count) {
        buildingCounts.put(buildingType, count);
        Label counter = buildingCounters.get(buildingType);
        if (counter != null) {
            counter.setText(String.valueOf(count));
        }
    }

    public void incrementBuildingCount(String buildingType) {
        int currentCount = buildingCounts.getOrDefault(buildingType, 0);
        updateBuildingCount(buildingType, currentCount + 1);
    }

    private void createNotificationSystem() {
        Label.LabelStyle style = new Label.LabelStyle(font, Color.RED);
        notificationLabel = new Label("", style);
        notificationLabel.setVisible(false);

        // notification positioned at top of screen
        Table notificationTable = new Table();
        notificationTable.setFillParent(true);
        notificationTable.top().padTop(50);
        notificationTable.add(notificationLabel);

        stage.addActor(notificationTable);
    }

    public void showNotification(String message) {
        notificationLabel.setText(message);
        notificationLabel.setVisible(true);
        notificationTimer = NOTIFICATION_DURATION;
    }

    private void updateNotifications(float delta) {
        if (notificationTimer > 0) {
            notificationTimer -= delta;
            if (notificationTimer <= 0) {
                notificationLabel.setVisible(false);
            }
        }
    }

    private void createEndGameWindow() {
        Window.WindowStyle windowStyle = new Window.WindowStyle();
        windowStyle.titleFont = font;
        windowStyle.titleFontColor = Color.WHITE;

        endGameWindow = new Window("Game Over!", new Window.WindowStyle(font, Color.WHITE, null));
        endGameWindow.setMovable(false);
        endGameWindow.setModal(false);

        backgroundTable = new Table();
        backgroundTable.setFillParent(true);
        backgroundTable.setBackground(new TextureRegionDrawable(new TextureRegion(
                new Texture(Pixmap.createFromFrameBuffer(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight())))));
        backgroundTable.setColor(0, 0, 0, 0.7f);
        endGameStage.addActor(backgroundTable);
        backgroundTable.setVisible(false);

        endGameWindow.setColor(0, 0, 0, 0.8f);

        Table content = createEndGameContent();
        endGameWindow.add(content);
        endGameWindow.pack();
        endGameWindow.setSize(endGameWindow.getWidth() + 40, endGameWindow.getHeight() + 40);
        centerWindow();

        endGameStage.addActor(endGameWindow);
        endGameWindow.setVisible(false);

        // TODO: block user input on game when time is up
    }

    private Table createEndGameContent() {
        Table content = new Table();
        content.pad(20);

        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
        Label finalScoreLabel = new Label("Satisfaction Score: " + player.getSatisfaction(), labelStyle);
        content.add(finalScoreLabel).pad(10).row();

        Table buttonTable = createEndGameButtons();
        content.add(buttonTable);

        return content;
    }

    private Table createEndGameButtons() {
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        buttonStyle.fontColor = Color.WHITE;
        buttonStyle.downFontColor = Color.LIGHT_GRAY;

        TextButton restartButton = new TextButton("Restart", buttonStyle);
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                onRestartGame.run();
                hideEndGameScreen();
            }
        });

        TextButton exitButton = new TextButton("Exit", buttonStyle);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        Table buttonTable = new Table();
        buttonTable.add(restartButton).pad(10).width(100);
        buttonTable.add(exitButton).pad(10).width(100);
        return buttonTable;
    }

    private void centerWindow() {
        endGameWindow.setPosition(
                (Gdx.graphics.getWidth() - endGameWindow.getWidth()) / 2,
                (Gdx.graphics.getHeight() - endGameWindow.getHeight()) / 2);
    }

    public void showEndGameScreen() {
        endGameWindow.setVisible(true);
        for (Actor actor : endGameStage.getActors()) {
            if (actor instanceof Table && actor != endGameWindow) {
                actor.setVisible(true);
            }
        }
        updateFinalScore();
    }

    public void hideEndGameScreen() {
        endGameWindow.setVisible(false);
        backgroundTable.setVisible(false);
    }

    private void updateFinalScore() {
        Label finalScoreLabel = endGameWindow.findActor("finalScoreLabel");
        if (finalScoreLabel != null) {
            finalScoreLabel.setText("Final Satisfaction Score: " + player.getSatisfaction());
        }
    }

    public void updateHUD(int timeLeft) {
        timeLabel.setText("Time Left: " + timeLeft);
        satisfactionLabel.setText("Satisfaction: " + player.getSatisfaction());
        fundsLabel.setText("Funds: $" + player.getFunds());
    }

    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        updateNotifications(delta);

        stage.act(delta);
        stage.draw();

        if (endGameWindow.isVisible()) {
            endGameStage.act(delta);
            endGameStage.draw();
        }
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        endGameStage.getViewport().update(width, height, true);
    }

    public void dispose() {
        stage.dispose();
        endGameStage.dispose();
        font.dispose();
    }

    public Stage getStage() {
        return stage;
    }

    public Stage getEndGameStage() {
        return endGameStage;
    }
}
