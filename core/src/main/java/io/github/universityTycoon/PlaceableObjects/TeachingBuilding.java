package io.github.universityTycoon.PlaceableObjects;

import java.time.Duration;
import java.time.LocalDateTime;

public class TeachingBuilding extends Building {
    static int lectureHallCount;
    static int labCount;
    static int classroomCount;

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

    public static int getLabCount() {
        return labCount;
    }

    public static int getLectureHallCount() {
        return lectureHallCount;
    }

    public static int getClassroomCount() {
        return classroomCount;
    }

    @Override
    public float calculateSatisfaction() {
        return 0f;
    }
}
