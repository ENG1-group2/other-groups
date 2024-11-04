package com.badlogic.UniSim2;

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
