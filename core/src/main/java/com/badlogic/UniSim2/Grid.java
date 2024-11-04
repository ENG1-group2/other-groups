package com.badlogic.UniSim2;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * This class is used to simulate the grid structure of the game.
 * Currently the only method that should be called is {@link #draw(StretchViewport)}.
 */
public class Grid {

    private final int rows; 
    private final int cols; 
    private final Color gridColor; 
    private final float cellSize;

    // Each grid space will have a status (Not used yet)
    public enum Status{
        Building,
        Path, 
        Unoccupied
    }
    private Status[][] grid; // 2D array to get status at any point (Not used yet)

    private ShapeRenderer shapeRenderer; 

    public Grid() {
        rows = Consts.GRID_ROWS;
        cols = Consts.GRID_COLS;
        grid = new Status[rows][cols];
        cellSize = Consts.CELL_SIZE;
        gridColor = Consts.GRID_COLOR;
        shapeRenderer = new ShapeRenderer(); 
    }

    // Called when a building is placed to update the status of the corresponding grid spaces (Not used yet)
    public void updateGrid(Building building){

        int startCol = building.getCol();
        int endCol = startCol + building.getBuildingWidth();
        int endRow = building.getRow();
        int startRow = endRow - building.getBuildingHeight();

        for (int row = startRow; row <= endRow; row++) {
            for(int col = startCol; col <= endCol; col++){
                grid[row][col] = Status.Building;
            }
        }
    }

    /**
     * Draws the lines of the grid.
     * @param viewport
     */
    public void draw(StretchViewport viewport) {
        setupRenderer(viewport);
        drawHorizontalLines(); 
        drawVerticalLines();
        shapeRenderer.end(); 
    }

    // Set up the ShapeRenderer with the viewport
    private void setupRenderer(StretchViewport viewport) {
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined); 
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line); 
        shapeRenderer.setColor(gridColor); 
    }

    // Draw horizontal grid lines
    private void drawHorizontalLines() {
        for (float i = 0; i < rows; i++) {
            float y = i * cellSize;
            shapeRenderer.line(0, y, cols * cellSize, y); // Line from left to right
        }
    }

    // Draw vertical grid lines
    private void drawVerticalLines() {
        for (float i = 0; i < cols; i++) {
            float x = i * cellSize;
            shapeRenderer.line(x, 0, x, rows * cellSize); // Line from top to bottom
        }
    }

    public void dispose() {
        shapeRenderer.dispose(); 
    }
}
