package com.badlogic.UniSim2.buildings;

import com.badlogic.UniSim2.Assets;
import com.badlogic.UniSim2.Consts;

/**
 * A building which represents a place where lectures can take place.
 * @see Building
 */
public class LectureHall extends Building{
    
    public LectureHall(){
        super(
            Assets.lectureHallPlacedTexture,
            Assets.lectureHallCollisionTexture,
            Assets.lectureHallDraggingTexture,
            Consts.LECTUREHALL_WIDTH,
            Consts.LECTUREHALL_HEIGHT,
            BuildingTypes.LectureHall
        );       
    }

}
