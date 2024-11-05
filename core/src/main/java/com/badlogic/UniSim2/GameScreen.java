package com.badlogic.UniSim2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
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

    private Map map;
    public GameScreen(Main game){
        this.game = game;
        viewport = game.getViewport();
        timer = new Timer();
        map = new Map(game);
        menu = new GameMenu(game, timer, map.getBuildingManager());
    }

    @Override
    public void show() {
        menu.activate();
    }

    @Override
    public void render(float delta) {
        input();
        update();
        draw();
    }

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

    private void update() {
        if (isPaused == false) {
            timer.update();
            if (timer.hasReachedMaxTime()) {
                game.endGame();
            }
        }
    }

    private void draw() {
        viewport.apply();
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