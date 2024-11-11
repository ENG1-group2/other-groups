package eng1.unisim.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import eng1.unisim.models.Player;

public class EndGameView {
    private final Stage stage;
    private final Window endGameWindow;
    private final Table backgroundTable;
    private final Player player;
    private final Runnable onRestartGame;

    public EndGameView(BitmapFont font, Player player, Runnable onRestartGame) {
        this.stage = new Stage(new ScreenViewport());
        this.player = player;
        this.onRestartGame = onRestartGame;

        // dark background
        backgroundTable = createBackground();
        stage.addActor(backgroundTable);

        // window to say game over and display stats
        endGameWindow = createEndGameWindow(font);
        stage.addActor(endGameWindow);

        hide();
    }

    private Table createBackground() {
        Table background = new Table();
        background.setFillParent(true);

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 0.7f);
        pixmap.fill();

        background.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pixmap))));
        pixmap.dispose();

        return background;
    }

    private Window createEndGameWindow(BitmapFont font) {
        Window.WindowStyle windowStyle = new Window.WindowStyle(font, Color.WHITE, null);
        Window window = new Window("Game Over!", windowStyle);
        window.setMovable(false);
        window.setModal(false);
        window.setColor(0, 0, 0, 0.8f);

        Table content = createEndGameContent(font);
        window.add(content);
        window.pack();
        window.setSize(window.getWidth() + 40, window.getHeight() + 40);
        centerWindow(window);

        return window;
    }

    private Table createEndGameContent(BitmapFont font) {
        Table content = new Table();
        content.pad(20);

        // score label
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
        Label finalScoreLabel = new Label("Satisfaction Score: " + player.getSatisfaction(), labelStyle);
        finalScoreLabel.setName("finalScoreLabel");
        content.add(finalScoreLabel).pad(10).row();

        // game end buttons (restart/exit)
        Table buttonTable = createEndGameButtons(font);
        content.add(buttonTable);

        return content;
    }

    private Table createEndGameButtons(BitmapFont font) {
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = font;
        buttonStyle.fontColor = Color.WHITE;
        buttonStyle.downFontColor = Color.LIGHT_GRAY;

        TextButton restartButton = new TextButton("Restart", buttonStyle);
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                onRestartGame.run();
                hide();
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

    private void centerWindow(Window window) {
        window.setPosition(
            (Gdx.graphics.getWidth() - window.getWidth()) / 2,
            (Gdx.graphics.getHeight() - window.getHeight()) / 2
        );
    }

    public void show() {
        backgroundTable.setVisible(true);
        endGameWindow.setVisible(true);
        updateFinalScore();
    }

    public void hide() {
        backgroundTable.setVisible(false);
        endGameWindow.setVisible(false);
    }

    private void updateFinalScore() {
        Label finalScoreLabel = endGameWindow.findActor("finalScoreLabel");
        if (finalScoreLabel != null) {
            finalScoreLabel.setText("Final Satisfaction Score: " + player.getSatisfaction());
        }
    }

    public void render(float delta) {
        if (endGameWindow.isVisible()) {
            stage.act(delta);
            stage.draw();
        }
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        centerWindow(endGameWindow);
    }

    public void dispose() {
        stage.dispose();
    }

    public Stage getStage() {
        return stage;
    }
}
