package uk.ac.york.vfc510.unisim.managers;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.MathUtils;

public class InputManager extends InputAdapter {
    private final OrthographicCamera camera;
    private boolean isDragging;
    private float lastX, lastY;
    private boolean isPlacingBuilding;
    private final BuildingPlacementCallback buildingCallback;
    private final Vector2 targetPosition;
    private static final float CAMERA_ZOOM_SPEED = 0.1f;
    private static final float MIN_ZOOM = 0.5f;
    private final float maxZoom;
    private static final float DRAG_SENSITIVITY = 2.5f; // Increased drag sensitivity

    public interface BuildingPlacementCallback {
        void onPlaceBuilding(float worldX, float worldY);
    }

    public InputManager(OrthographicCamera camera, BuildingPlacementCallback buildingCallback,
                        Vector2 targetPosition, float maxZoom) {
        this.camera = camera;
        this.buildingCallback = buildingCallback;
        this.targetPosition = targetPosition;
        this.maxZoom = maxZoom;
        this.isDragging = false;
        this.isPlacingBuilding = false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            if (isPlacingBuilding) {
                Vector3 worldCoords = camera.unproject(new Vector3(screenX, screenY, 0));
                buildingCallback.onPlaceBuilding(worldCoords.x, worldCoords.y);
                return true;
            } else {
                isDragging = true;
                lastX = screenX;
                lastY = screenY;
            }
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            isDragging = false;
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (isDragging && !isPlacingBuilding) {
            float deltaX = (screenX - lastX) * camera.zoom;
            float deltaY = (lastY - screenY) * camera.zoom;

            // drag movement is based off zoom level
            float movementScale = Math.min(1.0f, camera.zoom) * DRAG_SENSITIVITY;
            targetPosition.x = camera.position.x - deltaX * movementScale;
            targetPosition.y = camera.position.y - deltaY * movementScale;

            lastX = screenX;
            lastY = screenY;
            return true;
        }
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        float newZoom = camera.zoom + amountY * CAMERA_ZOOM_SPEED;
        camera.zoom = MathUtils.clamp(newZoom, MIN_ZOOM, maxZoom);
        return true;
    }

    public void setPlacingBuilding(boolean isPlacing) {
        this.isPlacingBuilding = isPlacing;
    }
}
