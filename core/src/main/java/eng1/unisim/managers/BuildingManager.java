package eng1.unisim.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import eng1.unisim.Building;
import java.util.HashMap;

public class BuildingManager {
    private final HashMap<Vector2, Building> placedBuildings;
    private final HashMap<Vector2, Texture> buildingTextures;
    private final HashMap<String, Texture> buildingTypeTextures;

    public BuildingManager() {
        placedBuildings = new HashMap<>();
        buildingTextures = new HashMap<>();
        buildingTypeTextures = new HashMap<>();
        loadTextures();
    }

    private void loadTextures() {
        buildingTypeTextures.put("Accommodation",
            new Texture(Gdx.files.internal("buildings/accommodation.png")));
    }

    public boolean placeBuilding(Building building, float worldX, float worldY) {
        Vector2 position = new Vector2(worldX, worldY);

        // check land not occupied (needs testing)
        if (placedBuildings.containsKey(position)) {
            return false;
        }

        // TODO prevent buildings being placed on water

        placedBuildings.put(position, building);
        buildingTextures.put(position, buildingTypeTextures.get(building.getName()));
        return true;
    }

    public void render(SpriteBatch batch) {
        for (HashMap.Entry<Vector2, Texture> entry : buildingTextures.entrySet()) {
            Vector2 pos = entry.getKey();
            Texture texture = entry.getValue();
            batch.draw(texture, pos.x - texture.getWidth()/2, pos.y - texture.getHeight()/2);
        }
    }

    public void dispose() {
        for (Texture texture : buildingTypeTextures.values()) {
            texture.dispose();
        }
    }
}
