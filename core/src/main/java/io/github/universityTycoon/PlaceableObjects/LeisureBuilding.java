package io.github.universityTycoon.PlaceableObjects;

import java.time.Duration;
import java.time.LocalDateTime;

public class LeisureBuilding extends Building {

    public LeisureBuilding(LocalDateTime constructionStartedAt) {
        super(constructionStartedAt, "images/fun_building.png");
        size = 2;
        satisfactionBonus = 3;
        constructionGameTime = Duration.ofDays(45);
        finishDate = constructionStartedAt.plus(constructionGameTime);
        buildingCapacity = 150;
    }



    @Override
    public float calculateSatisfaction() {
        return 0f;
    }
}
