package com.badlogic.UniSim2.mapmanager;

import com.badlogic.UniSim2.Assets;
import com.badlogic.UniSim2.Consts;
import com.badlogic.UniSim2.Main;
import com.badlogic.UniSim2.buildings.Building;
import com.badlogic.UniSim2.buildings.BuildingManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * This class represents the map that the game will take place on. It holds
 * all {@link Building buildings} and {@link Path paths} that can be placed on the map.
 */
public class Map {

    private final int width;
    private final int height;
    private final Grid grid;

    private BuildingManager buildings; // Used to control all the buildings in the game
    public static Array<Sprite> collidableSprites; // Contains both buildings and paths 
    
    private final SpriteBatch spriteBatch;
    private StretchViewport viewport;


    public Map(Main game) {
        width = Consts.WORLD_WIDTH;
        height = Consts.WORLD_HEIGHT;

        grid = new Grid();

        buildings = new BuildingManager();
        collidableSprites = new Array<Sprite>();
        Paths.createPaths();

        this.viewport = game.getViewport();

        spriteBatch = new SpriteBatch();
    }

    public BuildingManager getBuildingManager() {
        return buildings;
    }
    

    /**
     * Handles all input.
     */
    public void input() {
        Vector2 mousePos = new Vector2(Gdx.input.getX(), Gdx.input.getY()); // Gets the position of the mouse
        viewport.unproject(mousePos);
        boolean clicked = Gdx.input.justTouched(); // True when the mouse is clicked
        buildings.input(mousePos, clicked); // Handles input for all buildings in the game
    }

    /**
     * Draws the entire map including the background, the grid, the paths,
     * the buildings and then the menu.
     */
    public void draw() {
        drawSetup();
        drawBackground();
        grid.draw(viewport);
        drawPath();
        buildings.draw(spriteBatch); 
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

    private void drawPath(){
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
    }
}
