package io.github.universityTycoon.PlaceableObjects;

import com.badlogic.gdx.graphics.Texture;

import java.time.LocalDateTime;

public class AccommodationBuilding extends Building {
    int rentPricePPM;
    int commonRoomsCount;

    public AccommodationBuilding(LocalDateTime constructionStartedAt) {
        super(constructionStartedAt, "images/sleep_building.png");
    }

    @Override
    public float calculateSatisfaction() {
        return 0f;
    }
}
