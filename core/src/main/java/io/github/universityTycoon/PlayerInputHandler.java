package io.github.universityTycoon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class PlayerInputHandler {
    final int PAUSE_KEY = Input.Keys.P;

    public Vector2 getMousePos() {
        return new Vector2(Gdx.input.getX(), Gdx.input.getY());
    }

    public boolean getIsMouseDown() {
        return Gdx.input.isButtonPressed(Input.Buttons.LEFT);
    }

    public boolean getIsPauseJustPressed() {
        return Gdx.input.isKeyJustPressed(PAUSE_KEY);
    }

    public boolean getKeyJustPressed(int key) {
        return Gdx.input.isKeyJustPressed(key);
    }
}
