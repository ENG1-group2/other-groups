package github.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Pub extends Building {

  public Pub(Vector2 mapPos) {
    super(
      mapPos, 
       new Vector2[] {
        new Vector2(0, 0),
        new Vector2(1, 0),
        new Vector2(0, 1),
        new Vector2(1, 1)
      }
    );
  }

  @Override
  public void update(Renderer renderer, InputHandler inputHandler) {
    Vector2 pos = new Vector2((1280 - 720) + (this.mapPos.x * CELL_WIDTH), this.mapPos.y * CELL_WIDTH);
    Vector2 size = new Vector2(CELL_WIDTH * 2, CELL_WIDTH * 2);
    renderer.drawRect(pos,size,Color.PINK);
    
    renderer.drawText(
      "Pub", 
      new Vector2(pos.x + 3, pos.y + size.y - 3),
      Color.WHITE, 
      1.25f
    );
  }
}
