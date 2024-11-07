package com.badlogic.UniSim2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    private StretchViewport viewport = new StretchViewport(Consts.WORLD_WIDTH, Consts.WORLD_HEIGHT);

    private StartScreen startScreen;
    private GameScreen gameScreen;
    private EndScreen endScreen;

    @Override
    public void create() {
        Assets.loadTextures();

        startScreen = new StartScreen(this);

        setScreen(startScreen);
    }

    public StretchViewport getViewport() {
        return viewport;
    }

    @Override
    public void render() {
        super.render();
    }

    /**
     * Starts the game by setting the screen to the {@link #gameScreen}. Should be
     * called by the {@link StartScreen} when the start button is clicked.
     */
    public void startGame() {
        gameScreen = new GameScreen(this);
        setScreen(gameScreen);
        startScreen.dispose();
    }

    /**
     * Ends the game by settings the screen to {@link #endScreen}. Should be called by
     * the {@link GameScreen} when the timer ends.
     */
    public void endGame() {
        endScreen = new EndScreen(this, 0);
        setScreen(endScreen);
        gameScreen.dispose();
    }
}