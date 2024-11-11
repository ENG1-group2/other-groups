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

public class PauseMenuView {
    private final Stage stage;
    private final Window pauseMenuWindow;
    private final Table background;
    private final TextButton inGamePauseButton;
    private boolean isGameStarted = false;
    private final Runnable onPauseToggle;

    public PauseMenuView(BitmapFont font, Runnable onPauseToggle) {
        this.onPauseToggle = onPauseToggle;
        this.stage = new Stage(new ScreenViewport());

        background = createBackground();
        pauseMenuWindow = createPauseWindow(font);
        inGamePauseButton = createPauseButton(font);

        stage.addActor(background);
        stage.addActor(pauseMenuWindow);
        stage.addActor(createPauseButtonContainer());

        hide();
    }

    private Table createBackground() {
        Table bg = new Table();
        bg.setFillParent(true);
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 0.7f);
        pixmap.fill();
        bg.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pixmap))));
        pixmap.dispose();
        return bg;
    }

    private Window createPauseWindow(BitmapFont font) {
        Window window = new Window("", new Window.WindowStyle(font, Color.WHITE, null));
        window.setMovable(false);

        TextButton resumeButton = new TextButton("Start Game", new TextButton.TextButtonStyle(null, null, null, font));
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isGameStarted) {
                    isGameStarted = true;
                    resumeButton.setText("Resume");
                }
                hide();
                onPauseToggle.run();
            }
        });

        window.add(resumeButton).pad(10).width(200).height(50).row();
        window.pack();
        centerWindow(window);

        return window;
    }

    private TextButton createPauseButton(BitmapFont font) {
        TextButton button = new TextButton("Pause", new TextButton.TextButtonStyle(null, null, null, font));
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                show();
                onPauseToggle.run();
            }
        });
        return button;
    }

    private Table createPauseButtonContainer() {
        Table container = new Table();
        container.bottom().left();
        container.setFillParent(true);
        container.pad(10);
        container.add(inGamePauseButton).width(100).height(40);
        return container;
    }

    private void centerWindow(Window window) {
        window.setPosition(
            (Gdx.graphics.getWidth() - window.getWidth()) / 2,
            (Gdx.graphics.getHeight() - window.getHeight()) / 2
        );
    }

    public void show() {
        background.setVisible(true);
        pauseMenuWindow.setVisible(true);
    }

    public void hide() {
        background.setVisible(false);
        pauseMenuWindow.setVisible(false);
        if (isGameStarted) {
            inGamePauseButton.setVisible(true);
        }
    }

    public void render(float delta) {
        if (pauseMenuWindow.isVisible()) {
            stage.act(delta);
            stage.draw();
        }
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        centerWindow(pauseMenuWindow);
    }

    public void dispose() {
        stage.dispose();
    }

    public Stage getStage() {
        return stage;
    }
}
