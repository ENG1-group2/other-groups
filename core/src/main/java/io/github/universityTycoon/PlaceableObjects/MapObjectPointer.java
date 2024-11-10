package io.github.universityTycoon.PlaceableObjects;

/**
 * An object that can be placed on the map to declare a grid space "in use" by another MapObject.
 * These should NEVER be rendered. Currently, objects larger than a square are placed in the bottom left square of the
 * space they take up, with the remaining space occupied by instances of this class that point to it.
 */
public class MapObjectPointer extends MapObject {
    MapObject original; // The map object to point to

    /**
     * Constructor with the following parameters.
     * @param original The object that this pointer should point to
     */
    public MapObjectPointer(MapObject original) {
        this.original = original;
    }

    @Override
    public float calculateSatisfaction() {
        return original.calculateSatisfaction();
    }

    @Override
    public String getName() {
        return original.getName();
    }

    @Override
    public String getTexturePath() {
        return original.getTexturePath();
    }

    @Override
    public boolean getIsStackable() {
        return original.getIsStackable();
    }

    @Override
    public int getSize() {
        return original.getSize();
    }
}
