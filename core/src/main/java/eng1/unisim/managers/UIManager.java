package eng1.unisim.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
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

    public UIManager(Player player, Runnable onRestartGame) {
        this.player = player;
        this.onRestartGame = onRestartGame;
        setupUI();
    }

    private void setupUI() {
        stage = new Stage(new ScreenViewport());
        endGameStage = new Stage(new ScreenViewport());

        font = new BitmapFont();
        font.getData().setScale(1.5f);

        createHUD();
        createEndGameWindow();
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

    private void createEndGameWindow() {
        Window.WindowStyle windowStyle = new Window.WindowStyle();
        windowStyle.titleFont = font;
        windowStyle.titleFontColor = Color.WHITE;

        endGameWindow = new Window("Game Over!", new Window.WindowStyle(font, Color.WHITE, null));
        endGameWindow.setMovable(false);
        endGameWindow.setModal(false);

        backgroundTable = new Table();
        backgroundTable.setFillParent(true);
        backgroundTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(Pixmap.createFromFrameBuffer(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight())))));
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
            (Gdx.graphics.getHeight() - endGameWindow.getHeight()) / 2
        );
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
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        if (endGameWindow.isVisible()) {
            endGameStage.act(Gdx.graphics.getDeltaTime());
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
