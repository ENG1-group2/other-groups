package eng1.unisim.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import eng1.unisim.Building;
import java.util.HashMap;

public class BuildingManager {
    private final HashMap<Vector2, Building> placedBuildings;
    private final HashMap<Vector2, Texture> buildingTextures;
    private final HashMap<String, Texture> buildingTypeTextures;
    private Building selectedBuilding;
    private Texture selectedTexture;
    private static final float PLACED_SCALE = 0.5f; // scale down placed buildings
    private static final float PREVIEW_SCALE = 0.6f; // marginally larger scale for draggable preview
    private final UIManager uiManager;
    private HashMap<String, Integer> buildingCounts = new HashMap<>();

    public BuildingManager(UIManager uiManager) {  // now takes UIManager
        this.uiManager = uiManager;
        placedBuildings = new HashMap<>();
        buildingTextures = new HashMap<>();
        buildingTypeTextures = new HashMap<>();
        buildingCounts = new HashMap<>();
        loadTextures();
    }

    private void loadTextures() {
        buildingTypeTextures.put("Accommodation",
            new Texture(Gdx.files.internal("buildings/accommodation.png")));
    }

    public boolean placeBuilding(Building building, float worldX, float worldY) {
        Vector2 position = new Vector2(worldX, worldY);

        // check land not occupied (doesn't work..)
        if (placedBuildings.containsKey(position)) {
            return false;
        }

        // TODO prevent buildings being placed on water

        placedBuildings.put(position, building);
        buildingTextures.put(position, buildingTypeTextures.get(building.getName()));

        // building counters
        int count = buildingCounts.getOrDefault(building.getName(), 0) + 1;
        buildingCounts.put(building.getName(), count);
        uiManager.updateBuildingCount(building.getName(), count);

        selectedBuilding = null;
        selectedTexture = null;
        return true;
    }

    public void setSelectedBuilding(Building building) {
        this.selectedBuilding = building;
        this.selectedTexture = buildingTypeTextures.get(building.getName());
    }

    public void render(SpriteBatch batch, Vector3 cursorPosition) {
        // render placed buildings
        for (HashMap.Entry<Vector2, Texture> entry : buildingTextures.entrySet()) {
            Vector2 pos = entry.getKey();
            Texture texture = entry.getValue();
            float width = texture.getWidth() * PLACED_SCALE;
            float height = texture.getHeight() * PLACED_SCALE;
            batch.draw(texture,
                pos.x - width/2,
                pos.y - height/2,
                width,
                height);
        }

        // pre-place preview at cursor
        if (selectedBuilding != null && selectedTexture != null) {
            float width = selectedTexture.getWidth() * PREVIEW_SCALE;
            float height = selectedTexture.getHeight() * PREVIEW_SCALE;
            batch.setColor(1, 1, 1, 0.7f); // Semi-transparent preview
            batch.draw(selectedTexture,
                cursorPosition.x - width/2,
                cursorPosition.y - height/2,
                width,
                height);
            batch.setColor(1, 1, 1, 1); // Reset color
        }
    }

    public void dispose() {
        for (Texture texture : buildingTypeTextures.values()) {
            texture.dispose();
        }
    }

    public boolean hasSelectedBuilding() {
        return selectedBuilding != null;
    }
}
