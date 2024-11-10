package com.badlogic.UniSim2.buildingmanager;

import com.badlogic.UniSim2.mapmanager.Map;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * This class is used to manage all of the placed {@link Building buildings} 
 * on the map as well as a single selectedBuilding.
 */
public class BuildingManager {

    private Array<Building> buildings; // Array of all the buildings on the map in order of when placed

    private Building currentBuilding; // References the building currently selected

    private boolean currentlySelecting; // True when a building is selected and being being dragged 

    public BuildingManager() {
        buildings = new Array<>();
        currentBuilding = null;
        currentlySelecting = false;
    }

    /**
     * Adds currentBuilding to the buildings array and to collidableSprites array.
     * @param building The building to add.
     */
    private void addBuilding(Building building){
        buildings.add(building);
        Map.collidableSprites.add(building);
    }

    /**
     * Used to determine what to do when the mouse moves or clicks.
     * @param mousePos The position of the mouse in world coordinates.
     * @param clicked true if a click has happened and false if not.
     */
    public void input(Vector2 mousePos, boolean clicked, boolean backspacePressed) {

        // If we're currently selecting a building
        if(currentlySelecting){
            if(clicked){
                handlePlacing(); // Place the building in the location of the click
            }
            else if(backspacePressed){
                removeBuilding();
            }
            
            else{
                handleDragging(mousePos); // Otherwise continue dragging the building
            }
        }
    }

    private void removeBuilding(){
        buildings.removeValue(currentBuilding, true);
        Map.collidableSprites.removeValue(currentBuilding, true);
        currentBuilding = null;
        currentlySelecting = false;
    }

    /**
     * Used to place a building in a location. The {@link #currentBuilding} holds
     * the location where it should be placed.
     */
    private void handlePlacing(){
        // If the current building is not colliding 
        if(!isColliding(currentBuilding)){
            currentBuilding.placeBuilding(); // Place building
            currentBuilding = null;
            currentlySelecting = false; // No longer selecting a building
        }
    }

    /**
     * Called when a building button has been pressed. Deals with placing a new building 
     * corresponding to the button pressed determined with type
     * @param type The type of the building which the building button relates to.
     */
    public void handleSelection(Building.BuildingTypes type){

        handleType(type); // Sets current building to the type of building selected
        currentlySelecting = true; // Sets currently selecting to true as we have selected a building to drag and place
        currentBuilding.selectBuilding(); // Selects building
        addBuilding(currentBuilding); // Adds currentBuilding to the buildings array and to collidableSprites array
    }

    /**
     * Creates a new building based on the type.
     * @param type The type of the building to create.
     */
    private void handleType(Building.BuildingTypes type){
        switch(type){
            case Accomodation:
                currentBuilding = new Accomodation();
                break;
            case LectureHall:
                currentBuilding = new LectureHall();
                break;
            case Library:
                currentBuilding = new Library();
                break;
            case Course:
                currentBuilding = new Course();
                break;
            case FoodZone:
                currentBuilding = new FoodZone();
                break;
            case Recreational:
                currentBuilding = new Recreational();
                break;
            case Nature:
                currentBuilding = new Nature();
                break;
            default:
                break;
        }
    }

    /**
     * Updates the position of the currently selected building to the mouse pos and changes
     * the texture of the building depending on whether it is colliding with
     * another building.
     * @param mousPos The position of the mouse if world coords.
     */
    private void handleDragging(Vector2 mousPos){
        boolean colliding = isColliding(currentBuilding);
        currentBuilding.handleDragging(mousPos, colliding);
    }

    /**
     * Checks whether a building is colliding with anything in
     * {@link Map#collidableSprites}.
     * @param building The building to check.
     * @return true if the building is colliding with something and false otherwise.
     */
    private boolean isColliding(Building building){
        
        // For all sprites that are collidable
        for(Sprite collidableSprite : Map.collidableSprites){
            // Check if the building is overlapping with any
            boolean overlaps = building.getBoundingRectangle().overlaps(collidableSprite.getBoundingRectangle());
            // If so then return true
            if(!building.equals(collidableSprite) && overlaps){
                return true;
            }
        }
        // If not colliding return false
        return false;
    }

    /**
     * Draws all the buildings and clamps them to ensure they cannot go outside
     * of the map boundaries. Will also draw the {@link #currentBuilding}
     * on top of any placed buildings.
     * @param spriteBatch
     */
    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        for (Building building : buildings) {
            building.clampPosition(); // Ensures the buildings cannot be outside the map boundaries
            if(!building.equals(currentBuilding)){
            building.draw(spriteBatch);
            }
        }
        // Draws currentBuilding on top of every other building
        if(currentBuilding != null){
            currentBuilding.draw(spriteBatch);
        }
        spriteBatch.end();
    }

    /**
     * @return true if a building is currently being selected and false otherwise.
     */
    public boolean getCurrentlySelecting(){
        return currentlySelecting;
    }

    /**
     * Calls {@link Building#dispose()} on each building this stores.
     */
    public void dispose(){
        for(Building building : buildings){
            building.dispose();
        }
    }
}
