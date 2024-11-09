package io.github.universityTycoon.PlaceableObjects;

import java.time.LocalDateTime;

public class Cafeteria extends Building {

    int foodQuality;
    int hygieneRating;

    public Cafeteria(LocalDateTime constructionStartedAt) {
        super(constructionStartedAt, "images/rec_building.png");
        size = 2;
    }

    @Override
    public float calculateSatisfaction() {
        return 0f;
    }
}
