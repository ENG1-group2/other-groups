package io.github.universityTycoon.PlaceableObjects;

import com.badlogic.gdx.physics.box2d.PolygonShape;

public interface MapObject {
    String name = "";
    String spritePath = "";  // I chose to use a path so that this object can easily be serialised
                             // if we deem that necessary to store building types
    PolygonShape shape = new PolygonShape(); // Possibly not the type we need
    int capacity = 0;
    boolean isStackable = false;

    public float calculateSatisfaction(); // As a percentage

    public String getName();
    public String getSpritePath();
    public PolygonShape getShape();
    public int getCapacity();
    public boolean getIsStackable();
}
