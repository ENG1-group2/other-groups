package com.badlogic.UniSim2.buildings;

import com.badlogic.UniSim2.Assets;
import com.badlogic.UniSim2.Consts;

/**
 * A building which represents a nature item such as some tree. This does inherit
 * from {@link Building} which might be an issue.
 * @see Building
 */
public class Nature extends Building{

    public Nature(){
        super(
            Assets.naturePlacedTexture,
            Assets.natureCollisionTexture,
            Assets.natureDraggingTexture,
            Consts.NATURE_WIDTH,
            Consts.NATURE_HEIGHT,
            BuildingTypes.Nature
        );
    } 
}
