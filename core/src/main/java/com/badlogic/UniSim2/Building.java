package com.badlogic.UniSim2;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Building extends Sprite {

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

    // Used when a building is being dragged
    public void handleDragging(Vector2 mousePos, boolean colliding){
        updatePosition(mousePos); // Sets the position of the building to the mouse position
        setDraggingTexture(colliding); // Sets texture to colliding if true, dragging if false
    }

    // When a building is selected with a button
    public void selectBuilding() {
        isSelected = true;
        setDraggingTexture(false); // Sets the texture to dragging
    }

    // Used when a building is placed, changes isSelected and isPlaced and sets the placed texture
    public void placeBuilding() {
        Assets.click.play();
        isSelected = false;
        isPlaced = true;
        setRegion(placedTexture);
    }

    // Sets the texture to collision or dragging depending on if its colliding
    private void setDraggingTexture(boolean collision) {
        setRegion(collision ? collisionTexture : draggingTexture);
    }

    // Ensures that the building stays within the boundaries set by MAP_MIN_X_BOUNDARY, MAP_MAX_X_BOUNDARY 
    // and MAP_MIN_Y_BOUNDARY, MAP_MAX_Y_BOUNDARY
    public void clampPosition() {
        setX(MathUtils.clamp(getX(), Consts.MAP_MIN_X_BOUNDARY, Consts.MAP_MAX_X_BOUNDARY - width));
        setY(MathUtils.clamp(getY(), Consts.MAP_MIN_Y_BOUNDARY, Consts.MAP_MAX_Y_BOUNDARY - height));
    }

    // Gets the path distance from this building to another 
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

    // Returns the colomn of the bottom left square of the building
    public int getCol(){
        return (int)(getX() / Consts.CELL_SIZE);
    }

    // Returns the row of the bottom left square of the building
    public int getRow(){
        return (int)(Consts.GRID_ROWS - getY() / Consts.CELL_SIZE);
    }

    public Texture getPlacedTexture(){
        return placedTexture;
    }

    public Texture getCollisionTexture(){
        return collisionTexture;
    }

    public Texture getDraggingTexture(){
        return draggingTexture;
    }

    public void setSelected(boolean setter){
        isSelected = setter;
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

    public void dispose(){
        dispose();
    }
}
