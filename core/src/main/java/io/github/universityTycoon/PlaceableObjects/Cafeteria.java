package io.github.universityTycoon.PlaceableObjects;

import java.time.Duration;
import java.time.LocalDateTime;

public class Cafeteria extends Building {
    // displayed out of 10
    static int foodQuality;
    // displayed out of 5
    static int hygieneRating;

    public Cafeteria(LocalDateTime constructionStartedAt) {
        super(constructionStartedAt, "images/rec_building.png");
        size = 2;
        satisfactionBonus = 2;
        constructionGameTime = Duration.ofDays(10);
        finishDate = constructionStartedAt.plus(constructionGameTime);
        buildingCapacity = 100;
        foodQuality = 7;
        hygieneRating = 1;
    }

    public static int getHygieneRating() {
        return hygieneRating;
    }

    public static int getFoodQuality() {
        return foodQuality;
    }

    @Override
    public float calculateSatisfaction() {
        return 0f;
    }
}
