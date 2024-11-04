package com.badlogic.UniSim2;

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
    }

    private void update() {
        
    }

    private void draw() {
        menu.draw();
        map.draw();
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