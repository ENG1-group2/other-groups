package com.badlogic.UniSim2.GUImanager;

import com.badlogic.UniSim2.Main;
import com.badlogic.UniSim2.mapmanager.Map;
import com.badlogic.UniSim2.resources.Consts;
import com.badlogic.UniSim2.resources.SoundManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * This screen is used when the game is being played. 
 */
public class GameScreen implements Screen {
    private Main game;
    private StretchViewport viewport;

    private Timer timer;

    private GameMenu menu; // Used to make and display the game menu

    boolean isPaused = false;

    // This variable is needed to stop a crash from occuring when the game ends.
    boolean hasEnded = false;

    private Map map;
    public GameScreen(Main game){
        this.game = game;
        viewport = game.getViewport();
        timer = new Timer();
        map = new Map(game);
        menu = new GameMenu(game, timer, map.getBuildingManager());
        SoundManager.playMusic();
        
    }

    @Override
    public void show() {
        menu.activate();
    }

    @Override
    public void render(float delta) {
        input();
        update();
        if (hasEnded == true) return;
        draw();
    }

    /**
     * Processes input. Will pause/resume the game if the space is pressed.
     */
    private void input() {
        menu.input();
        map.input();

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            if (isPaused) {
                isPaused = false;
                menu.resume();
            }
            else {
                isPaused = true;
                menu.pause();
            }
        }

    }

    /**
     * Will update the timer or not (depending on whether the game is paused)
     * and will end the game if the timer has reached its max time.
     */
    private void update() {
        if (isPaused == false) {
            timer.update();
            if (timer.hasReachedMaxTime()) {
                game.endGame();
                hasEnded = true;
            }
        }
    }

    /**
     * Draws the game. This means drawing the game menu, building menu and game
     * map.
     */
    private void draw() {
        viewport.apply();
        ScreenUtils.clear(Consts.BACKGROUND_COLOR);
        map.draw();
        menu.draw();
    }


    @Override
    public void resize(int width, int height) {
        map.resize(width, height);
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        map.dispose();
        menu.dispose();
    }
}