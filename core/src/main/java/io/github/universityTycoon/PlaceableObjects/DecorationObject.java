package io.github.universityTycoon.PlaceableObjects;

/**
 * A generic decorative object to be placed on the map. May affect satisfaction
 */
public class DecorationObject extends MapObject {

    public String getName() {
        return name;
    }
    public String getTexturePath() {
        return texturePath;
    }

    public boolean getIsStackable() {
        return isStackable;
    }

    public float calculateSatisfaction() { // As a percentage
        return 0f;
    }

    public int getSize() {
        return size;
    }
}
