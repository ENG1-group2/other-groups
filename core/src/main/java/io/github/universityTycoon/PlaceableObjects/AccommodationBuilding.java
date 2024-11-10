package io.github.universityTycoon.PlaceableObjects;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * AccommodationBuilding extends the Building class, and will be the superclass to different types of accommodation
 * building.
 *
 * @param rentPricePPM The rent per month
 * @param commonRoomsCount The number of common rooms
 *
 */
public class AccommodationBuilding extends Building {
    static int rentPricePPM;
    static int commonRoomsCount;

    /**
     * Constructor with the following parameters.
     *
     * @param constructionStartedAt The date the construction starts at.
     */
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

    /**
     * Retrieves the rent price per month.
     * @return The rent per month.
     */
    public static int getRentPricePPM() {
        return rentPricePPM;
    }

    /**
     * Retrieves the number of common rooms.
     * @return The number of common rooms.
     */
    public static int getCommonRoomsCount() {
        return commonRoomsCount;
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
