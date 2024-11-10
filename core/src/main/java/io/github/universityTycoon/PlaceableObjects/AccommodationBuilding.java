package io.github.universityTycoon.PlaceableObjects;

import com.badlogic.gdx.graphics.Texture;

import java.time.Duration;
import java.time.LocalDateTime;

public class AccommodationBuilding extends Building {
    static int rentPricePPM;
    static int commonRoomsCount;

    public AccommodationBuilding(LocalDateTime constructionStartedAt) {
        super(constructionStartedAt, "images/sleep_building.png");
        size = 2;
        constructionGameTime = Duration.ofDays(30);
        satisfactionBonus = 1;
        finishDate = constructionStartedAt.plus(constructionGameTime);
        buildingCapacity = 300;
        rentPricePPM = 700;
        commonRoomsCount = 4;
    }

    public static int getRentPricePPM() {
        return rentPricePPM;
    }

    public static int getCommonRoomsCount() {
        return commonRoomsCount;
    }

    @Override
    public float calculateSatisfaction() {
        return 0f;
    }
}
