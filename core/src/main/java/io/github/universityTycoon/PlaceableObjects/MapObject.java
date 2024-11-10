package io.github.universityTycoon.PlaceableObjects;

/**
 * An abstract class representing anything that can be placed on a map
 */
public abstract class MapObject {
    String name = "";
    String texturePath = ""; // Relative path to the texture, e.g. "images/rec_building.png"
    boolean isStackable = false; // Whether MapObjects can be place on top of this MapObject
    int size = 1; // How many square it takes up. size=2 makes a 2x2 building

    public abstract float calculateSatisfaction(); // As a percentage

    public abstract String getName();
    public abstract String getTexturePath();
    public abstract boolean getIsStackable();
    public abstract int getSize();
}
