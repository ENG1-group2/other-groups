package com.badlogic.UniSim2;

/**
 * A building which represents student accommodation.
 * @see Building
 */
public class Accomodation extends Building{

    public Accomodation(){
        super(
            Assets.accomodationPlacedTexture,
            Assets.accomodationCollisionTexture,
            Assets.accomodationDraggingTexture,
            Consts.ACCOMODATION_WIDTH,
            Consts.ACCOMODATION_HEIGHT,
            BuildingTypes.Accomodation
        ); 
    }
}
