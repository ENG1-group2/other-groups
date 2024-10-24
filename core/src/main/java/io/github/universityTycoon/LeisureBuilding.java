package io.github.universityTycoon;

import java.time.LocalDateTime;

public class LeisureBuilding extends Building {

    public LeisureBuilding(LocalDateTime constructionStartedAt) {
        super(constructionStartedAt);
    }

    @Override
    public float calculateSatisfaction() {
        return 0f;
    }
}
