package io.github.universityTycoon;

import io.github.universityTycoon.PlaceableObjects.Building;
import io.github.universityTycoon.PlaceableObjects.MapObject;
import io.github.universityTycoon.PlaceableObjects.MapObjectPointer;

import java.time.LocalDateTime;

/**
 * Controls the map.
 *
 * @param tilesWide The number of tiles wide the map is.
 * @param tilesHigh The number of tiles high the map is.
 * @param mapObjects A 2D array of the placeable objects on the map.
 * @param MIN_DISTANCE_TO_TOP The min distance an object has to be from the top to be placed.
 */
public class MapController {
    private int tilesWide;
    private int tilesHigh;
    MapObject[][] mapObjects;
    private final int MIN_DISTANCE_TO_TOP = 2;


    /**
     * Constructor taking the following parameters.
     *
     * @param tilesWide The number of tiles wide the map is.
     * @param tilesHigh The number of tiles high the map is.
     */
    public MapController(int tilesWide, int tilesHigh) {
        this.tilesWide = tilesWide;
        this.tilesHigh = tilesHigh;
        this.mapObjects = new MapObject[tilesWide][tilesHigh];
    }

    /**
     * Adds a building to the map, if the attempted location is acceptable. Meaning it doesn't overlap with something else.
     *
     * @param building The building being added.
     * @param xPos The x grid coordinate it's being placed in.
     * @param yPos The y grid coordinate it's being placed in.
     * @return true if the building was successfully added, false otherwise.
     */
    public boolean addBuilding(Building building, int xPos, int yPos) {
        boolean buildingFits = true;
        // Note that the top left square is 0,0, so y/j is negative
        for (int i = 0; i < building.getSize() ; i++) {
            for (int j = 0; j < building.getSize() ; j++) {
                if ((xPos + i >= tilesWide || yPos - j < MIN_DISTANCE_TO_TOP || yPos - j >= tilesHigh) || mapObjects[xPos + i][yPos - j] != null) {
                    buildingFits = false;
                    break;
                }
            }
        }
        if (buildingFits) {
            // Place the bottom left square
            mapObjects[xPos][yPos] = building;

            // Then place pointers to the original
            for (int i = 0; i < building.getSize() ; i++) {
                for (int j = 0; j < building.getSize() ; j++) {
                    if (i != 0 || j != 0) {
                        mapObjects[xPos + i][yPos - j] = new MapObjectPointer(building);
                    }
                }
            }
        }
        return buildingFits;
    }

    // Call this to ensure buildings progress from under construction to complete

    /**
     * This function checks all the buildings against the current game time, and updates them when they've finished
     * being constructed.
     * @param gameTime The current in game time.
     */
    public void updateBuildings(LocalDateTime gameTime) {
        for (int x = 0; x < tilesWide; x++) {
            for (int y = 0; y < tilesHigh; y++) {
                if (mapObjects[x][y] instanceof Building) {
                    ((Building) mapObjects[x][y]).update(gameTime);
                }
            }
        }
    }
}
