package com.badlogic.UniSim2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class Map {

    private final int width;
    private final int height;
    private final Grid grid;

    private Buildings buildings; // Used to control all the buildings in the game
    private Paths paths; // Contains all the paths used in the game 
    public static Array<Sprite> collidableSprites; // Contains both buildings and paths 
    
    private final StretchViewport viewport;
    private final SpriteBatch spriteBatch;

    private GameMenu menu; // Used to make and display the game menu


    public Map(Game game) {

        width = Consts.WORLD_WIDTH;
        height = Consts.WORLD_HEIGHT;
        grid = new Grid();

        buildings = new Buildings();
        paths = new Paths();
        collidableSprites = new Array<Sprite>();
        paths.createPaths();

        viewport = new StretchViewport(width, height);
        spriteBatch = new SpriteBatch();
        menu = new GameMenu(viewport, buildings, game);
    }

    public void render() {
        input();
        draw();
    }

    // Handles when a user clicks or applies any input
    private void input() {
        menu.input();

        Vector2 mousePos = new Vector2(Gdx.input.getX(), Gdx.input.getY()); // Gets the position of the mouse
        viewport.unproject(mousePos);
        boolean clicked = Gdx.input.justTouched(); // True when the mouse is clicked
        buildings.input(mousePos, clicked); // Handles input for all buildings in the game
    }

    private void draw() {
        drawSetup();
        drawBackground();
        grid.draw(viewport);
        drawPath();
        buildings.draw(spriteBatch); 
        menu.draw();
    }

    // Required to start drawing 
    private void drawSetup(){
        ScreenUtils.clear(Consts.BACKGROUND_COLOR);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.enableBlending();
    }


    private void drawBackground() {
        spriteBatch.begin();
        spriteBatch.draw(Assets.backgroundTexture, 0, 0, width, height);
        spriteBatch.end();
    }

    public void drawPath(){
        spriteBatch.begin();
        spriteBatch.draw(Assets.pathTexture, 0,0, width, height);
        spriteBatch.end();
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    public void pause(){
        this.pause();
    }

    public void dispose(){
        grid.dispose();
        buildings.dispose();
        menu.dispose();
    }
}
