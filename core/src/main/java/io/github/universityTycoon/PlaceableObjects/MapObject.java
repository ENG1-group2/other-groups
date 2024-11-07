package io.github.universityTycoon.PlaceableObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public interface MapObject {
    String name = "";
    Texture texture;
    PolygonShape shape = new PolygonShape(); // Possibly not the type we need
    int capacity = 0;
    boolean isStackable = false;

    public float calculateSatisfaction(); // As a percentage

    public String getName();
    public Texture getTexture();
    public PolygonShape getShape();
    public int getCapacity();
    public boolean getIsStackable();
}
