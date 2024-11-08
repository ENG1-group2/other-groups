package io.github.universityTycoon.PlaceableObjects;

public class DecorationObject extends MapObject {

    public String getName() {
        return name;
    }
    public String getTexturePath() {
        return texturePath;
    }
    //public PolygonShape getShape() {
    //    return shape;
    //}
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
