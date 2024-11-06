package io.github.universityTycoon.PlaceableObjects;

import java.time.LocalDateTime;

public class AccommodationBuilding extends Building {
    int rentPricePPM;
    int commonRoomsCount;

    public AccommodationBuilding(LocalDateTime constructionStartedAt) {
        super(constructionStartedAt);
    }

    @Override
    public float calculateSatisfaction() {
        return 0f;
    }
}
