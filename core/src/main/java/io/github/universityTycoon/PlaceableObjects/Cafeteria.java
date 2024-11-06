package io.github.universityTycoon.PlaceableObjects;

import java.time.LocalDateTime;

public class Cafeteria extends Building {

    int foodQuality;
    int hygieneRating;

    public Cafeteria(LocalDateTime constructionStartedAt) {
        super(constructionStartedAt);
    }

    @Override
    public float calculateSatisfaction() {
        return 0f;
    }
}
