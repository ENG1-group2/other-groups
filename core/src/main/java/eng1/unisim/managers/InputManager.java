package eng1.unisim.managers;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.MathUtils;

// handles all user input for camera control and building placement
public class InputManager extends InputAdapter {
    private final OrthographicCamera camera;
    private boolean isDragging;
    private float lastX, lastY;
    private boolean isPlacingBuilding;
    private final BuildingPlacementCallback buildingCallback;
    private final CancelPlacementCallback cancelCallback;
    private final Vector2 targetPosition;
    // controls how fast the camera zooms in/out
    private static final float CAMERA_ZOOM_SPEED = 0.1f;
    // prevents camera from zooming in too close
    private static final float MIN_ZOOM = 0.5f;
    private final float maxZoom;

    // functional interface for building placement callback
    public interface BuildingPlacementCallback {
        void onPlaceBuilding(float worldX, float worldY);
    }

    // to cancel building placement on ESC
    public interface CancelPlacementCallback {
        void onCancelPlacement();
    }

    // initializes input manager with camera and building placement settings
    public InputManager(OrthographicCamera camera, BuildingPlacementCallback buildingCallback,
                        Vector2 targetPosition, float maxZoom, CancelPlacementCallback cancelCallback) {
        this.camera = camera;
        this.buildingCallback = buildingCallback;
        this.targetPosition = targetPosition;
        this.maxZoom = maxZoom;
        this.cancelCallback = cancelCallback;
        this.isDragging = false;
        this.isPlacingBuilding = false;
    }

    // ESC key cancels building placement
    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE && isPlacingBuilding) {
            cancelCallback.onCancelPlacement();
            return true;
        }
        return false;
    }

    // handles mouse button press events
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            if (isPlacingBuilding) {
                // convert screen coordinates to world coordinates for building placement
                Vector3 worldCoords = camera.unproject(new Vector3(screenX, screenY, 0));
                buildingCallback.onPlaceBuilding(worldCoords.x, worldCoords.y);
                return true;
            } else {
                // start camera drag
                isDragging = true;
                lastX = screenX;
                lastY = screenY;
            }
        }
        return true;
    }

    // handles mouse button release events
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            isDragging = false;
        }
        return true;
    }

    // handles camera panning when dragging the mouse
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (isDragging && !isPlacingBuilding) {
            // calculate camera movement based on mouse drag distance and zoom level
            float deltaX = (lastX - screenX) * camera.zoom;
            float deltaY = (screenY - lastY) * camera.zoom;

            // update camera position
            targetPosition.x = camera.position.x + deltaX;
            targetPosition.y = camera.position.y + deltaY;

            camera.position.x = targetPosition.x;
            camera.position.y = targetPosition.y;

            // store current position for next frame
            lastX = screenX;
            lastY = screenY;
            return true;
        }
        return false;
    }

    // handles mouse wheel scrolling for camera zoom
    @Override
    public boolean scrolled(float amountX, float amountY) {
        // adjust zoom level within defined bounds
        float newZoom = camera.zoom + amountY * CAMERA_ZOOM_SPEED;
        camera.zoom = MathUtils.clamp(newZoom, MIN_ZOOM, maxZoom);
        return true;
    }

    // toggles building placement mode
    public void setPlacingBuilding(boolean isPlacing) {
        this.isPlacingBuilding = isPlacing;
    }

    // returns current building placement mode state
    public boolean isPlacingBuilding() {
        return isPlacingBuilding;
    }
}
