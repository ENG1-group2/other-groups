package io.github.universityTycoon.PlaceableObjects;

public abstract class MapObject {
    String name = "";
    String texturePath = "";
    //PolygonShape shape = new PolygonShape(); // Possibly not the type we need
    int capacity = 0;
    boolean isStackable = false;
    int size = 1; // How many square it takes up. size=2 makes a 2x2 building

    public abstract float calculateSatisfaction(); // As a percentage

    public abstract String getName();
    public abstract String getTexturePath();
    //public PolygonShape getShape();
    public abstract int getCapacity();
    public abstract boolean getIsStackable();
    public abstract int getSize();
}
