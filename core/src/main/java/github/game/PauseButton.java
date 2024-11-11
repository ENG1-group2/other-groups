package github.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class PauseButton extends Entity {
  private Runnable togglePause;

  public PauseButton(Runnable togglePause) {
    this.togglePause = togglePause;
  }

  @Override
  public void update(Renderer renderer, InputHandler inputHandler) {
    Vector2 pos = new Vector2(180, 625);
    Vector2 size = new Vector2(30, 30);
    renderer.drawRect(pos, size, Color.LIGHT_GRAY);
    renderer.drawRect(
      new Vector2(pos.x + 5, pos.y + 5),
      new Vector2(7, 20),
      Color.WHITE
    );
    renderer.drawRect(
      new Vector2(pos.x + 17, pos.y + 5),
      new Vector2(7, 20),
      Color.WHITE
    );

    if (inputHandler.getMouseClicked() && inputHandler.getMouseInBounds(pos, size)) {
      togglePause.run();
    }
  }
}
