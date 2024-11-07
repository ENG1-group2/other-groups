package com.badlogic.UniSim2.buildingmanager;

import com.badlogic.UniSim2.Assets;
import com.badlogic.UniSim2.Consts;

/**
 * A building which represents a recreational building where students can have
 * fun.
 * @see Building
 */
public class Recreational extends Building{
    
    public Recreational(){
        super(
            Assets.recreationalPlacedTexture,
            Assets.recreationalCollisionTexture,
            Assets.recreationalDraggingTexture,
            Consts.RECREATIONAL_WIDTH,
            Consts.RECREATIONAL_HEIGHT,
            BuildingTypes.Recreational
        );
    }
}
