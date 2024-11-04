package com.badlogic.UniSim2;

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
