package github.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Restaurant extends Building {

  public Restaurant(Vector2 mapPos) {
    super(
      mapPos, 
       new Vector2[] {
        new Vector2(0, 0),
        new Vector2(1, 0),
        new Vector2(2, 0),
        new Vector2(3, 0),
        new Vector2(0, 1),
        new Vector2(3, 1)
      }
    );
  }

  @Override
  public void update(Renderer renderer, InputHandler inputHandler) {
    Vector2 pos = new Vector2((1280 - 720) + (this.mapPos.x * CELL_WIDTH), this.mapPos.y * CELL_WIDTH);
    Vector2 size = new Vector2(CELL_WIDTH * 4, CELL_WIDTH);
    renderer.drawRect(pos,size,Color.RED);
    renderer.drawRect(new Vector2(pos.x, pos.y+CELL_WIDTH), new Vector2(CELL_WIDTH, CELL_WIDTH), Color.RED);
    renderer.drawRect(new Vector2(pos.x+(3*CELL_WIDTH), pos.y+CELL_WIDTH), new Vector2(CELL_WIDTH, CELL_WIDTH), Color.RED);
    
    renderer.drawText(
      "Restaurant", 
      new Vector2(pos.x + 3, pos.y + size.y - 3),
      Color.WHITE, 
      1.25f
    );
  }
}
