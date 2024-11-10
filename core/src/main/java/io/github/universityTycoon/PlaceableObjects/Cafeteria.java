package io.github.universityTycoon.PlaceableObjects;

import java.time.Duration;
import java.time.LocalDateTime;

public class Cafeteria extends Building {
    // displayed out of 10
    static int foodQuality;
    // displayed out of 5
    static int hygieneRating;

    /**
     * Constructor with the following parameters.
     *
     * @param constructionStartedAt The date the construction starts at.
     */
    public Cafeteria(LocalDateTime constructionStartedAt) {
        super(constructionStartedAt, "images/rec_building.png");
        size = 1;
        satisfactionBonus = 2;
        constructionGameTime = Duration.ofDays(10);
        finishDate = constructionStartedAt.plus(constructionGameTime);
        buildingCapacity = 100;
        foodQuality = 7;
        hygieneRating = 1;
    }

    /**
     * Retrieves the hygiene rating.
     * @return The number of hygiene rating.
     */
    public static int getHygieneRating() {
        return hygieneRating;
    }

    /**
     * Retrieves the food quality.
     * @return The number of food quality.
     */
    public static int getFoodQuality() {
        return foodQuality;
    }

    /**
     * Will calculate the satisfaction impact based off other aspects in the game.
     * @return the satisfaction impact.
     */
    @Override
    public float calculateSatisfaction() {
        return 0f;
    }
}
