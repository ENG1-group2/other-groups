package io.github.universityTycoon;

import com.badlogic.gdx.math.Vector2;
import io.github.universityTycoon.PlaceableObjects.Building;
import io.github.universityTycoon.PlaceableObjects.MapObject;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class MapController {
    private int tilesWide;
    private int tilesHigh;
    MapObject[][] mapObjects;

    public MapController(int tilesWide, int tilesHigh) {
        this.tilesWide = tilesWide;
        this.tilesHigh = tilesHigh;
        MapObject[][] mapObjects = new MapObject[tilesWide][tilesHigh];
    }

    public void addBuilding(Building building, int xPos, int yPos) {
        if (mapObjects[xPos][yPos] == null) {
            mapObjects[xPos][yPos] = building;
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
