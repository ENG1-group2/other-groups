package com.badlogic.UniSim2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

/**
 * This screen is used when the game is being played. 
 */
public class GameScreen implements Screen {
    private Map map;
    public GameScreen(Game game){
        map = new Map(game);
    }
    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        map.render();
    }

    @Override
    public void resize(int width, int height) {
        map.resize(width, height);
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
    }
}