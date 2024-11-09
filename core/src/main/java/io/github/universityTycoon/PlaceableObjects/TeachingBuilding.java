package io.github.universityTycoon.PlaceableObjects;

import java.time.Duration;
import java.time.LocalDateTime;

public class TeachingBuilding extends Building {
    int lectureHallCount;
    int labCount;
    int classroomCount;

    public TeachingBuilding(LocalDateTime constructionStartedAt) {
        super(constructionStartedAt, "images/library_building.png");
        size = 2;
        satisfactionBonus = 0.1f;
        constructionGameTime = Duration.ofDays(60);
        finishDate = constructionStartedAt.plus(constructionGameTime);
    }

    @Override
    public float calculateSatisfaction() {
        return 0f;
    }
}
