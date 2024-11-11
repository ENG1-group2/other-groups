package github.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class UI extends Entity {
  private Game game;
  private PauseButton pauseButton;
  private BuildingPlaceButton[] buildingPlaceButtons;

  public UI(Game game) {
    this.game = game;
    pauseButton = new PauseButton(game::togglePause);
    buildingPlaceButtons = new BuildingPlaceButton[] {
      new BuildingPlaceButton(
        "Pub", 
        "A place to drink.", 
        0, 
        () -> game.setBuildingToPlace(Pub.class)
      ),
      new BuildingPlaceButton(
        "Halls Accommodation", 
        "A place to sleep.", 
        1,
        () -> game.setBuildingToPlace(HallsAccommadation.class)
      ),
      new BuildingPlaceButton(
        "Restaurant", 
        "A place to eat.", 
        2,
        () -> game.setBuildingToPlace(Restaurant.class)
      ),
      new BuildingPlaceButton(
        "Lecture Hall", 
        "A place to learn.", 
        3,
        () -> game.setBuildingToPlace(LectureHall.class)
      ),
      new BuildingPlaceButton(
        "Gym", 
        "A place to get bolo.", 
        3,
        () -> game.setBuildingToPlace(Gym.class)
      )
    };
  }

  @Override
  public void update(Renderer renderer, InputHandler inputHandler) {

    // draw the title
    Vector2 titlePos = new Vector2(20, 700);
    renderer.drawText("University Simulator", titlePos, Color.BLACK, 2f);

    // draw the time display
    String timeLeftString = game.getTimeLeftString();
    Vector2 timePos = new Vector2(20, 650);
    renderer.drawText(timeLeftString, timePos, Color.BLACK, 1.5f);

    // update pause button
    pauseButton.update(renderer, inputHandler);

    // draw the building count
    renderer.drawText(
      game.getBuildingCount() + " Buildings Placed", 
      new Vector2(20, 600),
      Color.BLACK,
      1.5f
    );

    // draw the selected building
    renderer.drawText(
      "Selected: " + (game.getBuildingToPlace() == null ? "None" : game.getBuildingToPlace().getSimpleName()), 
      new Vector2(250, 600), 
      Color.BLACK, 
      1.5f
    );

    // update the building place buttons
    for (BuildingPlaceButton buildingPlaceButton : buildingPlaceButtons) {
      buildingPlaceButton.update(renderer, inputHandler);
    }
  }
}
