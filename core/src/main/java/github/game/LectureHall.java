package github.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class LectureHall extends Building {

  public LectureHall(Vector2 mapPos) {
    super(
      mapPos, 
       new Vector2[] {
        new Vector2(-2, 0),
        new Vector2(-1, 0),
        new Vector2(0, 0),
        new Vector2(1, 0),
        new Vector2(2, 0),

        new Vector2(-2, 1),
        new Vector2(-1, 1),
        new Vector2(0, 1),
        new Vector2(1, 1),
        new Vector2(2, 1),

        new Vector2(-1, 2),
        new Vector2(0, 2),
        new Vector2(1, 2),
      }
    );
  }

  @Override
  public void update(Renderer renderer, InputHandler inputHandler) {
    Vector2 pos = new Vector2((1280 - 720) + (this.mapPos.x * CELL_WIDTH), this.mapPos.y * CELL_WIDTH);

    for (Vector2 offset : this.relCellsUsed) {
      Vector2 cellPos = new Vector2(pos.x + (offset.x * CELL_WIDTH), pos.y + (offset.y * CELL_WIDTH));
      renderer.drawRect(cellPos, new Vector2(CELL_WIDTH, CELL_WIDTH), Color.BLUE);
    }
    
    renderer.drawText(
      "Lecture Hall", 
      new Vector2(pos.x, pos.y + CELL_WIDTH), 
      Color.WHITE, 
      1.25f
    );
  }
}
