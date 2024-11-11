package github.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class HallsAccommadation extends Building {

  public HallsAccommadation(Vector2 mapPos) {
    super(
      mapPos, 
       new Vector2[] {
        new Vector2(0, 0),
        new Vector2(1, 0),
        new Vector2(2, 0),
        new Vector2(3, 0),
        new Vector2(0, 1),
        new Vector2(1, 1),
        new Vector2(2, 1),
        new Vector2(3, 1),
      }
    );
  }

  @Override
  public void update(Renderer renderer, InputHandler inputHandler) {
    Vector2 pos = new Vector2((1280 - 720) + (this.mapPos.x * CELL_WIDTH), this.mapPos.y * CELL_WIDTH);
    Vector2 size = new Vector2(CELL_WIDTH * 4, CELL_WIDTH * 2);
    renderer.drawRect(pos,size,Color.BLUE);
    
    renderer.drawText(
      "Halls", 
      new Vector2(pos.x + 3, pos.y + size.y - 3),
      Color.WHITE, 
      1.25f
    );
  }
}
