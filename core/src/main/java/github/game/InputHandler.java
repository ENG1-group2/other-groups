package github.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class InputHandler {

    final private OrthographicCamera camera;

    public InputHandler(OrthographicCamera camera) {
        this.camera = camera;
    }

    public boolean getMouseClicked() {
        boolean clicked = Gdx.input.isButtonJustPressed(Input.Buttons.LEFT);
        return clicked;
    }

    public Vector2 getMousePos() {
        float x = Gdx.input.getX();
        float y = Gdx.input.getY();
        Vector3 screenCoords = new Vector3(x, y, 0);
        Vector3 worldCoords = camera.unproject(screenCoords);
        return new Vector2(worldCoords.x, worldCoords.y);
    }

    public boolean getMouseInBounds(Vector2 pos, Vector2 size) {
        Vector2 mousePos = getMousePos();
        return (
            mousePos.x > pos.x 
            && mousePos.x < pos.x + size.x 
            && mousePos.y > pos.y 
            && mousePos.y < pos.y + size.y
        );
    }
}
