package io.github.universityTycoon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

/**
 * PlayerInputHandler handles the user inputs.
 *
 * @param PAUSE_KEY Assigns a specific key to the pause button.
 */
public class PlayerInputHandler {
    final int PAUSE_KEY = Input.Keys.P;

    /**
     * Retrieves the current mouse position on the screen.
     * @return A Vector2 object with the coordinates of the mouse.
     */
    public Vector2 getMousePos() {
        return new Vector2(Gdx.input.getX(), Gdx.input.getY());
    }

    /**
     * Checks if the left mouse button is currently being pressed.
     * @return true if the left mouse button is pressed, false otherwise.
     */
    public boolean getIsMouseDown() {
        return Gdx.input.isButtonPressed(Input.Buttons.LEFT);
    }

    /**
     * Checks if the left mouse button was just clicked (pressed and released).
     * @return true if the left mouse button was just clicked, false otherwise.
     */
    public boolean mouseJustClicked() {
        return Gdx.input.isButtonJustPressed(Input.Buttons.LEFT);
    }

    /**
     * Checks if the pause key was just pressed.
     * @return true if the pause key was just pressed, false otherwise.
     */
    public boolean getIsPauseJustPressed() {
        return Gdx.input.isKeyJustPressed(PAUSE_KEY);
    }

    /**
     * Checks if a specified key was just pressed.
     * @param key The integer code representing the key to check.
     * @return true if the specified key was just pressed, false otherwise.
     */
    public boolean getKeyJustPressed(int key) {
        return Gdx.input.isKeyJustPressed(key);
    }

}
