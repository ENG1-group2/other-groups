package io.github.universityTycoon.PlaceableObjects;

public class MapObjectPointer extends MapObject {
    MapObject original;

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
    public int getCapacity() {
        return original.getCapacity();
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
