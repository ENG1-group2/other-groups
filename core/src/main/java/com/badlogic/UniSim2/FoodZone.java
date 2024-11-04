package com.badlogic.UniSim2;

public class FoodZone extends Building{

    public FoodZone(){
        super(
            Assets.foodZonePlacedTexture,
            Assets.foodZoneCollisionTexture,
            Assets.foodZoneDraggingTexture,
            Consts.FOODZONE_WIDTH,
            Consts.FOODZONE_HEIGHT,
            BuildingTypes.FoodZone
        );
    }
}
