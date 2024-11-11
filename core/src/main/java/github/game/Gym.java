package github.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Gym extends Building {

  public Gym(Vector2 mapPos) {
    super(
      mapPos, 
       new Vector2[] {
        new Vector2(0, 0),
        new Vector2(1, 0),
        new Vector2(2, 0),
        
        new Vector2(0, 1),
        new Vector2(1, 1),
        new Vector2(2, 1),

        new Vector2(0, 2),
        new Vector2(1, 2),
        new Vector2(2, 2),
      }
    );
  }

  @Override
  public void update(Renderer renderer, InputHandler inputHandler) {
    Vector2 pos = new Vector2((1280 - 720) + (this.mapPos.x * CELL_WIDTH), this.mapPos.y * CELL_WIDTH);
    Vector2 size = new Vector2(CELL_WIDTH * 3, CELL_WIDTH * 3);
    renderer.drawRect(pos,size,Color.PURPLE);
    
    renderer.drawText(
      "Gym", 
      new Vector2(pos.x + 3, pos.y + size.y - 3),
      Color.WHITE, 
      1.25f
    );
  }
}
