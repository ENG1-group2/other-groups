package github.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class GameMap extends Entity {
  private static final int CELLS_PER_ROW = 30;
  private Cell[] cells = new Cell[CELLS_PER_ROW * CELLS_PER_ROW];
  private ArrayList<Building> placedBuildings;
  private Obstacle[] obstacles;

  public GameMap(Game game) {
    this.placedBuildings = new ArrayList<Building>();
    this.obstacles = new Obstacle[] {
      new Obstacle(
        new Vector2(5, 5), 
        new Vector2[] {
          new Vector2(1, 0),
          new Vector2(2, 0),
          new Vector2(3, 0),

          new Vector2(0, 1),
          new Vector2(1, 1),
          new Vector2(2, 1),
          new Vector2(3, 1),
          new Vector2(4, 1),
          new Vector2(5, 1),

          new Vector2(3, 2),
          new Vector2(4, 2),
          new Vector2(5, 2)
        }
        , Color.BLUE
      ),
      new Obstacle(
        new Vector2(20, 25), 
        new Vector2[] {
          new Vector2(0, 0),
          new Vector2(1, 0),
          new Vector2(0, -1),
          new Vector2(1, -1),
          new Vector2(0, -2),
          new Vector2(1, -2),
          new Vector2(1, -3),
          new Vector2(2, -3),
          new Vector2(2, -4),
          new Vector2(3, -4),
          new Vector2(2, -5),
          new Vector2(3, -5),
          new Vector2(2, -6),
          new Vector2(3, -6),
          new Vector2(3, -7),
        }
        , Color.BLUE
      )
    };

    for (int i = 0; i < CELLS_PER_ROW; i++) {
      for (int j = 0; j < CELLS_PER_ROW; j++) {
        Vector2 cellPos = new Vector2(i, j);
        cells[i * CELLS_PER_ROW + j] = new Cell(
          cellPos, 
          () -> game.handleCellClick(cellPos)
        );
      }
    }
  };

  @Override
  public void update(Renderer renderer, InputHandler inputHandler) {

    // draw the map
    Vector2 pos = new Vector2(1280 - 720, 0);
    Vector2 size = new Vector2(720, 720);
    Color color = Color.valueOf("c2c2c2");
    renderer.drawRect(pos, size, color);

    for (Cell cell : cells) {
      cell.update(renderer, inputHandler);
    }

    for (Building building : placedBuildings) {
      building.update(renderer, inputHandler);
    }

    for (Obstacle obstacle : obstacles) {
      obstacle.update(renderer, inputHandler);
    }
  }

  public void placeBuilding(Building building) {
    placedBuildings.add(building);
  }

  public boolean getCanPlace(Building building) {
    Vector2 mapPos = building.getMapPos();
    Vector2[] relCellsUsed = building.getRelCellsUsed();

    for (Vector2 relCell : relCellsUsed) {
      Vector2 cellPos = new Vector2(mapPos.x + relCell.x, mapPos.y + relCell.y);
      if (!getCellIsFree(cellPos)) {
        return false;
      }

      if (cellPos.x < 0 || cellPos.x >= CELLS_PER_ROW || cellPos.y < 0 || cellPos.y >= CELLS_PER_ROW) {
        return false;
      }
    }

    return true;
  }

  public boolean getCellIsFree(Vector2 cellPos) {
    for (Building building : placedBuildings) {
      Vector2[] relCellsUsed = building.getRelCellsUsed();
      Vector2 mapPos = building.getMapPos();

      for (Vector2 relCell : relCellsUsed) {
        Vector2 usedCellPos = new Vector2(mapPos.x + relCell.x, mapPos.y + relCell.y);

        if (usedCellPos.equals(cellPos)) {
          return false;
        }
      }
    }

    for (Obstacle obstacle : obstacles) {
      Vector2[] relCellsUsed = obstacle.getRelCellsUsed();
      Vector2 mapPos = obstacle.getMapPos();

      for (Vector2 relCell : relCellsUsed) {
        Vector2 usedCellPos = new Vector2(mapPos.x + relCell.x, mapPos.y + relCell.y);

        if (usedCellPos.equals(cellPos)) {
          return false;
        }
      }
    }

    return true;
  }

  public int getBuildingCount() {
    return placedBuildings.size();
  }
}
