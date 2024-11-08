package io.github.universityTycoon.PlaceableObjects;

import java.time.LocalDateTime;

public class TeachingBuilding extends Building {
    int lectureHallCount;
    int labCount;
    int classroomCount;

    public TeachingBuilding(LocalDateTime constructionStartedAt) {
        super(constructionStartedAt, "images/library_building.png");
    }

    @Override
    public float calculateSatisfaction() {
        return 0f;
    }
}
