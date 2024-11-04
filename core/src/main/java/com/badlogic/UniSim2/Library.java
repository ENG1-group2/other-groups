package com.badlogic.UniSim2;

public class Library extends Building{

    public Library(){
        super(
            Assets.libraryPlacedTexture,
            Assets.libraryCollisionTexture,
            Assets.libraryDraggingTexture,
            Consts.LIBRARY_WIDTH,
            Consts.LIBRARY_HEIGHT,
            BuildingTypes.Library
        );
    }
}
