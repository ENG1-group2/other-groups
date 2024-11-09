package io.github.universityTycoon;

import com.badlogic.gdx.math.Vector2;
import io.github.universityTycoon.PlaceableObjects.Building;
import io.github.universityTycoon.PlaceableObjects.MapObject;
import io.github.universityTycoon.PlaceableObjects.MapObjectPointer;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class MapController {
    private int tilesWide;
    private int tilesHigh;
    MapObject[][] mapObjects;



    public MapController(int tilesWide, int tilesHigh) {
        this.tilesWide = tilesWide;
        this.tilesHigh = tilesHigh;
        this.mapObjects = new MapObject[tilesWide][tilesHigh];
    }

    public void addBuilding(Building building, int xPos, int yPos) {
        boolean buildingFits = true;
        // Note that the top left square is 0,0, so y/j is negative
        for (int i = 0; i < building.getSize() ; i++) {
            for (int j = 0; j < building.getSize() ; j++) {
                if ((xPos + i >= tilesWide || yPos - j < 0 || yPos - j >= tilesHigh) || mapObjects[xPos + i][yPos - j] != null) {
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
    }

    // Call this to ensure buildings progress from under construction to complete
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
