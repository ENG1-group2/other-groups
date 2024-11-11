package github.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class BuildingPlaceButton extends Entity {
  private String buildingName;
  private String buildingDescription;
  private int index;
  private Runnable onClick;

  public BuildingPlaceButton(String buildingName, String buildingDescription, int index, Runnable onClick) {
    this.buildingName = buildingName;
    this.buildingDescription = buildingDescription;
    this.index = index;
    this.onClick = onClick;
  }

  @Override
  public void update(Renderer renderer, InputHandler inputHandler) {
    Vector2 pos = new Vector2(20, 500 - (index * 80));
    Vector2 size = new Vector2(520, 60);
    renderer.drawRect(pos, size, Color.LIGHT_GRAY);
    renderer.drawText("Place: " + buildingName, new Vector2(pos.x + 10, pos.y + size.y - 10), Color.BLACK, 1.5f);
    renderer.drawText(buildingDescription, new Vector2(pos.x + 10, pos.y + size.y- 35), Color.BLACK, 1f);

    if (inputHandler.getMouseClicked() && inputHandler.getMouseInBounds(pos, size)) {
      onClick.run();
    }
  }
}
