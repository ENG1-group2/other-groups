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
        this.mapObjects = new MapObject[tilesWide][tilesHigh];
    }

    public void addBuilding(Building building, int xPos, int yPos) {
        // the following if statement, if checkSurroundingTiles worked should be as follows
        /*
         if ((mapObjects[xPos][yPos] == null) && checkSurroundingTiles(xPos, yPos) &&
         (xPos < 61) && (yPos > 5) && (yPos < 23)) {
         mapObjects[xPos][yPos] = building;
         }
         */

        // However I've constrained placement of tiles on the left to prevent crashing

        if ((mapObjects[xPos][yPos] == null) &&
            (xPos < 61) && (xPos > 2) && (yPos > 5) && (yPos < 23)) {
            if  (checkSurroundingTiles(xPos, yPos)) {
                mapObjects[xPos][yPos] = building;
            }
        }

        }

    public boolean checkSurroundingTiles(int xPos, int yPos) {
            return (
                // checks the tiles 1  above and 1 below
                (mapObjects[xPos][yPos+1] == null) && (mapObjects[xPos][yPos-1] == null) &&
                // checks the tiles 2  above and 2 below
                (mapObjects[xPos][yPos+2] == null) && (mapObjects[xPos][yPos-2] == null) &&

                // checks the tiles 1 right, 1 above and 1 below
                (mapObjects[xPos+1][yPos+1] == null) && (mapObjects[xPos+1][yPos-1] == null) &&
                // checks the tiles 1 right, 2 above and 2 below
                (mapObjects[xPos+1][yPos+2] == null) && (mapObjects[xPos+1][yPos-2] == null) &&

                // checks the tiles 1 left, 1 above and 1 below
                (mapObjects[xPos-1][yPos+1] == null) && (mapObjects[xPos-1][yPos-1] == null) &&
                // checks the tiles 1 left, 2 above and 2 below
                (mapObjects[xPos-1][yPos+2] == null) && (mapObjects[xPos-1][yPos-2] == null) &&

                // checks the tiles 2 right, 1 above and 1 below
                (mapObjects[xPos+2][yPos+1] == null) && (mapObjects[xPos+2][yPos-1] == null) &&
                // checks the tiles 2 right, 2 above and 2 below
                (mapObjects[xPos+2][yPos+2] == null) && (mapObjects[xPos+2][yPos-2] == null) &&

                // checks the tiles 2 left, 1 above and 1 below
                (mapObjects[xPos-2][yPos+1] == null) && (mapObjects[xPos-2][yPos-1] == null) &&
                // checks the tiles 2 left, 2 above and 2 below
                (mapObjects[xPos-2][yPos+2] == null) && (mapObjects[xPos-2][yPos-2] == null) &&

                // checks the tiles 3 right, 1 above and 1 below
                (mapObjects[xPos + 3][yPos + 1] == null) && (mapObjects[xPos + 3][yPos - 1] == null) &&
                // checks the tiles 3 right, 2 above and 2 below
                (mapObjects[xPos + 3][yPos + 2] == null) && (mapObjects[xPos + 3][yPos - 2] == null) &&

                // checks the tiles 3 left, 1 above and 1 below
                (mapObjects[xPos - 3][yPos + 1] == null) && (mapObjects[xPos - 3][yPos - 1] == null) &&
                // checks the tiles 3, 2 above and 2 below
                (mapObjects[xPos - 3][yPos + 2] == null) && (mapObjects[xPos - 3][yPos - 2] == null) &&

                // checks the tiles 1 and 2 right
                (mapObjects[xPos + 1][yPos] == null) && (mapObjects[xPos + 2][yPos] == null) &&
                // checks the tiles 1 and 2 left
                (mapObjects[xPos - 1][yPos] == null) && (mapObjects[xPos - 2][yPos] == null) &&
                // checks the tiles 3 right and 3 left
                (mapObjects[xPos + 3][yPos] == null) && (mapObjects[xPos - 3][yPos] == null)


        );


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
