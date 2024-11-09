package io.github.universityTycoon.PlaceableObjects;

import com.badlogic.gdx.graphics.Texture;

import java.time.Duration;
import java.time.LocalDateTime;

public class AccommodationBuilding extends Building {
    int rentPricePPM;
    int commonRoomsCount;

    public AccommodationBuilding(LocalDateTime constructionStartedAt) {
        super(constructionStartedAt, "images/sleep_building.png");
        size = 2;
        constructionGameTime = Duration.ofDays(30);
        satisfactionBonus = 1;
        finishDate = constructionStartedAt.plus(constructionGameTime);
    }

    @Override
    public float calculateSatisfaction() {
        return 0f;
    }
}
