package com.badlogic.UniSim2;

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
