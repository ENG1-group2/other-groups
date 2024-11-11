package github.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Game {
    private static final float REALTIME_LENGTH = 300f;
    private static final float GAMETIME_LENGTH = 10f;
    private static final String GAMETIME_UNIT = "year";

    private float timeElapsed = 0f;
    private boolean paused = false;
    private Class<? extends Building> buildingToPlace = null;
    private GameMap map;
    private UI ui;

    public Game() {
        map = new GameMap(this);
        ui = new UI(this);
    }

    public void update(float delta, Renderer renderer, InputHandler inputHandler) {
        if (!paused) {
            timeElapsed += delta;
        }

        boolean gameComplete = timeElapsed >= REALTIME_LENGTH;
        if (gameComplete) {
            renderGameOverScreen(renderer);
            return;
        }

        map.update(renderer, inputHandler);
        ui.update(renderer, inputHandler);
    }

    public String getTimeLeftString() {
        float progress = timeElapsed / REALTIME_LENGTH;
        float gameTimeElapsed = progress * GAMETIME_LENGTH;
        float gameTimeLeft = GAMETIME_LENGTH - gameTimeElapsed;
        return String.format("%.2f %ss left", gameTimeLeft, GAMETIME_UNIT);
    }

    public void togglePause() {
        paused = !paused;
    }

    public void setBuildingToPlace(Class<? extends Building> buildingType) {
        if (buildingType == buildingToPlace) {
            buildingToPlace = null;
        } else {
            buildingToPlace = buildingType;
        }
    }

    public Class<? extends Building> getBuildingToPlace() {
        return buildingToPlace;
    }

    public void handleCellClick(Vector2 cellPos) {
        Building building;
        if (buildingToPlace == Pub.class) {
            building = new Pub(cellPos);
        }
        else if (buildingToPlace == HallsAccommadation.class) {
            building = new HallsAccommadation(cellPos);
        }
        else if (buildingToPlace == Restaurant.class) {
            building = new Restaurant(cellPos);
        }
        else if (buildingToPlace == LectureHall.class) {
            building = new LectureHall(cellPos);
        }
        else if (buildingToPlace == Gym.class) {
            building = new Gym(cellPos);
        }
        else {
            return;
        }

        if (!map.getCanPlace(building)) {
            return;
        }

        map.placeBuilding(building);
    }

    public int getBuildingCount() {
        return map.getBuildingCount();
    }

    private void renderGameOverScreen(Renderer renderer) {
        Vector2 pos = new Vector2(500, 700);
        renderer.drawText("Game Over!",pos, Color.RED, 4f);
    }
}
