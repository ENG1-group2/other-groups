package io.github.universityTycoon.PlaceableObjects;

import java.time.Duration;
import java.time.LocalDateTime;

public class TeachingBuilding extends Building {
    static int lectureHallCount;
    static int labCount;
    static int classroomCount;

    /**
     * Constructor with the following parameters.
     *
     * @param constructionStartedAt The date the construction starts at.
     */
    public TeachingBuilding(LocalDateTime constructionStartedAt) {
        super(constructionStartedAt, "images/library_building.png");
        size = 2;
        satisfactionBonus = 0.1f;
        constructionGameTime = Duration.ofDays(60);
        finishDate = constructionStartedAt.plus(constructionGameTime);
        buildingCapacity = 400;
        lectureHallCount = 2;
        labCount = 10;
        classroomCount = 30;
    }

    /**
     * Retrieves the number of labs.
     * @return The number of labs.
     */
    public static int getLabCount() {
        return labCount;
    }

    /**
     * Retrieves the number of lecture halls.
     * @return The number of lecture halls.
     */
    public static int getLectureHallCount() {
        return lectureHallCount;
    }

    /**
     * Retrieves the number of classrooms.
     * @return The number of classrooms.
     */
    public static int getClassroomCount() {
        return classroomCount;
    }


    /**
     * Will calculate the satisfaction impact based off other aspects in the game.
     * @return the satisfaction impact.
     */
    @Override
    public float calculateSatisfaction() {
        return 0f;
    }
}
