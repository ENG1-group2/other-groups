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

// handles the pause menu ui and its interactions
public class PauseMenuView {
    // ui components
    private final Stage stage;
    private final Window pauseMenuWindow;
    private final Table background;
    private final TextButton inGamePauseButton;
    private boolean isGameStarted = false;
    // callback for when pause state changes
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

        background.setVisible(false);
        pauseMenuWindow.setVisible(false);
        inGamePauseButton.setVisible(false);
    }

    // creates a semi-transparent black overlay
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

    // creates the main pause window with the resume/start button
    private Window createPauseWindow(BitmapFont font) {
        Window window = new Window("", new Window.WindowStyle(font, Color.WHITE, null));
        window.setMovable(false);

        TextButton resumeButton = new TextButton("Start Game", createButtonStyle(font));
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!isGameStarted) {
                    isGameStarted = true;
                    resumeButton.setText("Resume");
                    // Show the pause button when game starts
                    inGamePauseButton.setVisible(true);
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

    // creates the pause button that appears in-game
    private TextButton createPauseButton(BitmapFont font) {
        TextButton button = new TextButton("Pause", createButtonStyle(font));
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                show();
                onPauseToggle.run();
            }
        });
        return button;
    }

    // creates a consistent style for all buttons
    private TextButton.TextButtonStyle createButtonStyle(BitmapFont font) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font;
        style.fontColor = Color.WHITE;
        style.downFontColor = Color.LIGHT_GRAY;
        return style;
    }

    // positions the pause button in the bottom left corner
    private Table createPauseButtonContainer() {
        Table container = new Table();
        container.bottom().left();
        container.setFillParent(true);
        container.pad(10);
        container.add(inGamePauseButton).width(100).height(40);
        return container;
    }

    // helper method to center a window on the screen
    private void centerWindow(Window window) {
        window.setPosition(
            (Gdx.graphics.getWidth() - window.getWidth()) / 2,
            (Gdx.graphics.getHeight() - window.getHeight()) / 2
        );
    }

    // displays the pause menu and hides the pause button
    public void show() {
        background.setVisible(true);
        pauseMenuWindow.setVisible(true);
        // while paused menu shown, hide pause button
        inGamePauseButton.setVisible(false);
    }

    // hides the pause menu and shows the pause button if game is started
    public void hide() {
        background.setVisible(false);
        pauseMenuWindow.setVisible(false);
        // show pause button once game starts
        if (isGameStarted) {
            inGamePauseButton.setVisible(true);
        }
    }

    // updates and draws the pause menu
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    // handles window resizing and repositioning
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        centerWindow(pauseMenuWindow);
    }

    // cleans up resources
    public void dispose() {
        stage.dispose();
    }

    // getter for the stage (used for input handling)
    public Stage getStage() {
        return stage;
    }
}
