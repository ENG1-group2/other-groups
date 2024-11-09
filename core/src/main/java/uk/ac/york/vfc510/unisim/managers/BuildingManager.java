package uk.ac.york.vfc510.unisim.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import uk.ac.york.vfc510.unisim.Building;
import java.util.HashMap;
import java.util.ArrayList;

public class BuildingManager {
    private HashMap<Vector2, Building> placedBuildings;
    private HashMap<String, TextureRegion> buildingTextures;

    public BuildingManager() {
        placedBuildings = new HashMap<>();
        buildingTextures = new HashMap<>();
    }

    public void addBuildingTexture(String buildingName, TextureRegion texture) {
        buildingTextures.put(buildingName, texture);
    }

    public boolean placeBuilding(Building building, float worldX, float worldY) {
        Vector2 position = new Vector2(worldX, worldY);
        // Check if position is already occupied
        if (placedBuildings.containsKey(position)) {
            return false;
        }
        placedBuildings.put(position, building);
        return true;
    }

    public void render(SpriteBatch batch) {
        for (HashMap.Entry<Vector2, Building> entry : placedBuildings.entrySet()) {
            Vector2 pos = entry.getKey();
            Building building = entry.getValue();
            TextureRegion texture = buildingTextures.get(building.getName());
            if (texture != null) {
                batch.draw(texture, pos.x, pos.y);
            }
        }
    }
}
