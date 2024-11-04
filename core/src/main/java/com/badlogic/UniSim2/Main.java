package com.badlogic.UniSim2;

import com.badlogic.gdx.Game;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    @Override
    public void create() {
        Assets.loadTextures();
        setScreen(new StartScreen(this));
    }
}