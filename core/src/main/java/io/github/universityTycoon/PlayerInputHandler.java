package io.github.universityTycoon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class PlayerInputHandler {
    final int PAUSE_KEY = Input.Keys.SPACE;
    boolean isMouseDown = false;
    boolean isPausePressed = false;
    Vector2 mousePos = new Vector2();

    // Call this in main's render() method..
    // Or don't. This, and all attributes, could be removed and instead calculated in the get() methods
    // Might be more efficient.
    public void handleInput() {
        // Update the relevant input attributes based on input
    }

    public Vector2 getMousePos() {
        return mousePos;
    }

    public boolean getIsMouseDown() {
        return isMouseDown;
    }

    public boolean getIsPausePressed() {
        return isPausePressed;
    }
}
