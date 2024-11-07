package com.badlogic.UniSim2.buildingmanager;

import com.badlogic.UniSim2.mapmanager.Map;
import com.badlogic.UniSim2.resources.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * A class which represents a building that can be placed on the map.
 * Each building can be stored in the {@link BuildingManager}.
 */
public abstract class Building extends Sprite {

    // Textures for when a building is placed, colliding, dragged
    private final Texture placedTexture;
    private final Texture collisionTexture;
    private final Texture draggingTexture;

    private boolean isSelected; // True when the corresponding building button is clicked
    private boolean isPlaced; // True when a building is placed on the grid

    private final int width; 
    private final int height;

    public enum BuildingTypes{
        Accomodation,
        LectureHall,
        Library,
        Course,
        FoodZone,
        Recreational,
        Nature
    }
    private BuildingTypes type;
    
    public Building(Texture placedTexture, Texture collisionTexture, Texture draggingTexture, int width, int height, BuildingTypes type) {

        this.placedTexture = placedTexture;
        this.collisionTexture = collisionTexture;
        this.draggingTexture = draggingTexture;
        this.width = width;
        this.height = height;
        this.type = type;
        isSelected = true;
        isPlaced = false;

        setSize(width, height);
        setRegion(placedTexture); // By default a buildings texture is its placed texture
    }

    /**
     * Updates the position of the building to the specified mousePos. Will
     * ensure that the building is snapped to the grid.
     * @param mousePos The position of the mouse in world coords.
     */
    private void updatePosition(Vector2 mousePos) {
        // Puts the centre of the building where the mouse is
        float x = mousePos.x - getWidth() / 2f;
        float y = mousePos.y - getHeight() / 2f;

        // Ensures that a building moves within the grid by snapping it to the grid
        int cellSize = Consts.CELL_SIZE;
        float snapX = MathUtils.round(x / cellSize) * cellSize;
        float snapY = MathUtils.round(y / cellSize) * cellSize;
        setPosition(snapX, snapY);
    }

    /**
     * Should be called when is building is being dragged. Will update the 
     * position to the mousePos and will set use the colliding texture
     * if colliding is set to true.
     * @param mousePos The mouse position in world coords.
     * @param colliding true if the building is colliding with something in
     * {@link Map#collidableSprites} and false otherwise.
     */
    public void handleDragging(Vector2 mousePos, boolean colliding){
        updatePosition(mousePos); // Sets the position of the building to the mouse position
        setDraggingTexture(colliding); // Sets texture to colliding if true, dragging if false
    }

    /**
     * Should be called when this building is selected. Will set
     * the texture to dragging.
     */
    public void selectBuilding() {
        isSelected = true;
        setDraggingTexture(false); // Sets the texture to dragging
    }

    /**
     * Should be called when the building is placed. Will play the build
     * placing sound, deselect the building and change the texture to the
     * placed texture.
     */
    public void placeBuilding() {
        isSelected = false;
        isPlaced = true;
        setRegion(placedTexture);
    }

    /**
     * Sets the texture to collision or dragging dependings on if it is colliding
     * with another collidable in {@link Map#collidableSprites}.
     * @param collision true when the building is colliding and false otherwise.
     */
    private void setDraggingTexture(boolean collision) {
        setRegion(collision ? collisionTexture : draggingTexture);
    }

    /**
     * Ensures that the building stays within the boundaries set by {@link Consts#MAP_MIN_X_BOUNDARY},
     * {@link Consts#MAP_MAX_X_BOUNDARY}, {@link Consts#MAP_MIN_Y_BOUNDARY} and 
     * {@link Consts#MAP_MAX_Y_BOUNDARY}.
     */
    public void clampPosition() {
        setX(MathUtils.clamp(getX(), Consts.MAP_MIN_X_BOUNDARY, Consts.MAP_MAX_X_BOUNDARY - width));
        setY(MathUtils.clamp(getY(), Consts.MAP_MIN_Y_BOUNDARY, Consts.MAP_MAX_Y_BOUNDARY - height));
    }

    /**
     * Gets the distance from this building to another building. The distance
     * is defined by the x_distance + y_distance between buildings.
     * @param building The other building to find the distance between.
     * @return The distance. Can be rounded both down and up.
     */
    public int getDistanceFrom(Building building){
    float xDistance = Math.abs(getX() - building.getX());
    float yDistance = Math.abs(getY() - building.getY());
    int distance = Math.round(xDistance + yDistance);
    return distance;
    }

    // Gets the vector of the centre of the building
    public Vector2 getCentre(){
        return new Vector2(getX() + width / 2, getY() + height / 2);
    }

    /**
     * Returns the column of the bottom left square of the building.
     * @return
     */
    public int getCol(){
        return (int)(getX() / Consts.CELL_SIZE);
    }

    /**
     * Returns the row of the bottom left square of the building.
     * @return
     */
    public int getRow(){
        return (int)(Consts.GRID_ROWS - getY() / Consts.CELL_SIZE);
    }

    public boolean getIsSelected(){
        return isSelected;
    }

    public boolean getIsPlaced(){
        return isPlaced;
    }

    public int getBuildingWidth(){
        return width;
    }

    public int getBuildingHeight(){
        return height;
    }

    public BuildingTypes getType(){
        return type;
    }

    public void dispose() {
    }
}
