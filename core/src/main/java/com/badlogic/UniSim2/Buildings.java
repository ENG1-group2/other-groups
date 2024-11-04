package com.badlogic.UniSim2;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Buildings {

    private Array<Building> buildings; // Array of all the buildings on the map in order of when placed

    private Building currentBuilding; // References the building currently selected

    private boolean currentlySelecting; // True when a building is selected and being being dragged 

    public Buildings() {
        buildings = new Array<>();
        currentBuilding = null;
        currentlySelecting = false;
    }

    private void addBuilding(Building building){
        buildings.add(building);
        Map.collidableSprites.add(building);
    }

    // Used to determine what to do when the mouse is clicked on a menu button
    public void input(Vector2 mousePos, boolean clicked) {

        // If a click has happened
        if(clicked){
            // If we are currently selecting a building
            if(currentlySelecting){
                handlePlacing(); // Place the building in the location of the click
            }
        }
        // Otherwise continue dragging the building
        if(currentlySelecting){
            handleDragging(mousePos);
        }
    }

    private void handlePlacing(){

        // If the current building is not colliding 
        if(!isColliding(currentBuilding)){
            currentBuilding.placeBuilding(); // Place building
            currentlySelecting = false; // No longer selecting a building
        }
    }

    // Called when a building button has been pressed, deals with placing a new building 
    // corresponding to the button pressed determined with type
    public void handleSelection(Building.BuildingTypes type){

        handleType(type); // Sets current building to the type of building selected
        currentlySelecting = true; // Sets currently selecting to true as we have selected a building to drag and place
        currentBuilding.selectBuilding(); // Selects building
        addBuilding(currentBuilding); // Adds currentBuilding to the buildings array and to collidableSprites array
    }

    // Creates a new building based on type
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

    // Updates the position of the building to mousePos and changes its texture depending on colliding
    private void handleDragging(Vector2 mousPos){
        boolean colliding = isColliding(currentBuilding);
        currentBuilding.handleDragging(mousPos, colliding);
    }


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

    // Draws all buildings in the game and clamps them, ensuring any selected buildings are on top
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

    public boolean getCurrentlySelecting(){
        return currentlySelecting;
    }

    public void dispose(){
        for(Building building : buildings){
            building.dispose();
        }
    }
}
