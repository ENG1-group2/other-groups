package com.badlogic.UniSim2.buildingmanager;

import com.badlogic.UniSim2.resources.*;

/**
 * A building which represents a library.
 * @see Building
 */
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
