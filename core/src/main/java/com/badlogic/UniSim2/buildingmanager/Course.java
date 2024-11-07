package com.badlogic.UniSim2.buildingmanager;

import com.badlogic.UniSim2.resources.*;

/**
 * A building which represents a course specific building.
 * @see Building
 */
public class Course extends Building{

    public Course(){
        super(
            Assets.coursePlacedTexture,
            Assets.courseCollisionTexture,
            Assets.courseDraggingTexture,
            Consts.COURSE_WIDTH,
            Consts.COURSE_HEIGHT,
            BuildingTypes.Course
        );
    } 
}
