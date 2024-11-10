package io.github.universityTycoon.PlaceableObjects;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * LeisureBuilding extends the Building class, and will be the superclass to different types of leisure building.
 *
 */
public class LeisureBuilding extends Building {

    /**
     * Constructor with the following parameters.
     *
     * @param constructionStartedAt The date the construction starts at.
     */
    public LeisureBuilding(LocalDateTime constructionStartedAt) {
        super(constructionStartedAt, "images/fun_building.png");
        size = 2;
        satisfactionBonus = 3;
        constructionGameTime = Duration.ofDays(45);
        finishDate = constructionStartedAt.plus(constructionGameTime);
        buildingCapacity = 150;
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
