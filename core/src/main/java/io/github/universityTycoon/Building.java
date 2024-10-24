package io.github.universityTycoon;

import com.badlogic.gdx.physics.box2d.PolygonShape;

import java.time.Duration;
import java.time.LocalDateTime;

public class Building implements MapObject {

    public Duration constructionGameTime = Duration.ofDays(365);
    public LocalDateTime finishDate;

    LocalDateTime constructionStartedAt; // IN-GAME TIME

    public Building(LocalDateTime constructionStartedAt) {
        this.constructionStartedAt = constructionStartedAt;
        finishDate = constructionStartedAt.plus(constructionGameTime);
    }

    public String getName() {
        return name;
    }
    public String getSpritePath() {
        return spritePath;
    }
    public PolygonShape getShape() {
        return shape;
    }
    public int getCapacity() {
        return capacity;
    }
    public boolean getIsStackable() {
        return isStackable;
    }

    // As a percentage
    public float calculateSatisfaction() {
        return 1f;
    }

    public boolean isUnderConstruction(LocalDateTime currentGameTime) {
        return currentGameTime.isBefore(finishDate);
    }
}
