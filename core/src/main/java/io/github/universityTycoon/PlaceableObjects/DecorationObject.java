package io.github.universityTycoon.PlaceableObjects;

import com.badlogic.gdx.physics.box2d.PolygonShape;

public class DecorationObject implements MapObject {

    public String getName() {
        return name;
    }
    public String getSpritePath() {
        return spritePath;
    }
    public PolygonShape getShape() {
        return shape;
    }
    public int getCapacity() {
        return capacity;
    }
    public boolean getIsStackable() {
        return isStackable;
    }

    public float calculateSatisfaction() { // As a percentage
        return 0f;
    }
}
