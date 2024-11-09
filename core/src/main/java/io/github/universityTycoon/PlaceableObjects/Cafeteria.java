package io.github.universityTycoon.PlaceableObjects;

import java.time.Duration;
import java.time.LocalDateTime;

public class Cafeteria extends Building {

    int foodQuality;
    int hygieneRating;

    public Cafeteria(LocalDateTime constructionStartedAt) {
        super(constructionStartedAt, "images/rec_building.png");
        size = 2;
        satisfactionBonus = 2;
        constructionGameTime = Duration.ofDays(10);
        finishDate = constructionStartedAt.plus(constructionGameTime);
    }

    @Override
    public float calculateSatisfaction() {
        return 0f;
    }
}
