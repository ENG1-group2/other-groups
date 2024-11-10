package eng1.unisim.managers;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

import eng1.unisim.Building;

public class BuildingManager {
    private Array<PolygonMapObject> buildingAreaPolygons;
    private Array<PolylineMapObject> buildingAreaPolylines;
    private final HashMap<Vector2, Building> placedBuildings;
    private final HashMap<Vector2, Texture> buildingTextures;
    private final HashMap<String, Texture> buildingTypeTextures;
    private Building selectedBuilding;
    private Texture selectedTexture;
    private static final float PLACED_SCALE = 0.3f; // scale down placed buildings
    private static final float PREVIEW_SCALE = 0.4f; // marginally larger scale for draggable preview
    private final UIManager uiManager;
    private HashMap<String, Integer> buildingCounts = new HashMap<>();

    public BuildingManager(UIManager uiManager) {
        this.uiManager = uiManager;
        placedBuildings = new HashMap<>();
        buildingTextures = new HashMap<>();
        buildingTypeTextures = new HashMap<>();
        buildingCounts = new HashMap<>();
        loadTextures();
        loadBuildingAreaObjects();
    }

    private void loadTextures() {
        buildingTypeTextures.put("Accommodation",
                new Texture(Gdx.files.internal("buildings/accommodation.png")));
        buildingTypeTextures.put("Recreation",
                new Texture(Gdx.files.internal("buildings/recreationPlaceholder.png")));
        buildingTypeTextures.put("Dining",
                new Texture(Gdx.files.internal("buildings/dining.png")));
        buildingTypeTextures.put("Learning",
                new Texture(Gdx.files.internal("buildings/learning.png")));
    }

    private void loadBuildingAreaObjects() {
        TiledMap tileMap = new TmxMapLoader().load("emptyGroundMap.tmx");
        if (tileMap == null) {
            System.out.println("Failed to load the map.");
            return;
        }

        if (tileMap.getLayers().get("BuildingAreas") == null) {
            System.out.println("BuildingAreas layer not found.");
            return;
        }
        buildingAreaPolygons = tileMap.getLayers().get("BuildingAreas").getObjects().getByType(PolygonMapObject.class);
        buildingAreaPolylines = tileMap.getLayers().get("BuildingAreas").getObjects()
                .getByType(PolylineMapObject.class);
        System.out.println("Loaded " + buildingAreaPolygons.size + " polygon areas.");
        System.out.println("Loaded " + buildingAreaPolylines.size + " polyline areas.");
    }

    public boolean placeBuilding(Building building, float worldX, float worldY) {
        Vector2 position = new Vector2(worldX, worldY);

        boolean isWithinBuildingArea = false;

        // Check polygons
        for (PolygonMapObject polygonObject : buildingAreaPolygons) {
            Polygon polygon = polygonObject.getPolygon();
            if (polygon.contains(position.x, position.y)) {
                isWithinBuildingArea = true;
                break;
            }
        }

        // Check polylines (optional, if you want to allow placement near polylines)
        if (!isWithinBuildingArea) {
            for (PolylineMapObject polylineObject : buildingAreaPolylines) {
                Polyline polyline = polylineObject.getPolyline();
                float[] vertices = polyline.getTransformedVertices();
                for (int i = 0; i < vertices.length - 2; i += 2) {
                    float x1 = vertices[i];
                    float y1 = vertices[i + 1];
                    float x2 = vertices[i + 2];
                    float y2 = vertices[i + 3];
                    if (isPointNearLine(position.x, position.y, x1, y1, x2, y2)) {
                        isWithinBuildingArea = true;
                        break;
                    }
                }
                if (isWithinBuildingArea)
                    break;
            }
        }

        // Check if the position is within a building area and not already occupied
        if (!isWithinBuildingArea || placedBuildings.containsKey(position)) {
            System.out.println("Cannot place building at (" + worldX + ", " + worldY + ").");
            return false;
        }

        placedBuildings.put(position, building);
        buildingTextures.put(position, buildingTypeTextures.get(building.getName()));

        // Update building counters
        int count = buildingCounts.getOrDefault(building.getName(), 0) + 1;
        buildingCounts.put(building.getName(), count);
        uiManager.updateBuildingCount(building.getName(), count);

        selectedBuilding = null;
        selectedTexture = null;
        return true;
    }

    private boolean isPointNearLine(float px, float py, float x1, float y1, float x2, float y2) {
        float dx = x2 - x1;
        float dy = y2 - y1;
        float lengthSquared = dx * dx + dy * dy;
        float t = ((px - x1) * dx + (py - y1) * dy) / lengthSquared;
        t = MathUtils.clamp(t, 0, 1);
        float nearestX = x1 + t * dx;
        float nearestY = y1 + t * dy;
        float distanceSquared = (px - nearestX) * (px - nearestX) + (py - nearestY) * (py - nearestY);
        return distanceSquared < 25; // Adjust the threshold as needed
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
                    pos.x - width / 2,
                    pos.y - height / 2,
                    width,
                    height);
        }

        // pre-place preview at cursor
        if (selectedBuilding != null && selectedTexture != null) {
            float width = selectedTexture.getWidth() * PREVIEW_SCALE;
            float height = selectedTexture.getHeight() * PREVIEW_SCALE;
            batch.setColor(1, 1, 1, 0.7f); // Semi-transparent preview
            batch.draw(selectedTexture,
                    cursorPosition.x - width / 2,
                    cursorPosition.y - height / 2,
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
