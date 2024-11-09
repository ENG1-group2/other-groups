package io.github.universityTycoon.PlaceableObjects;

import com.badlogic.gdx.math.MathUtils;

import java.time.Duration;
import java.time.LocalDateTime;

public class Building extends MapObject {

    public Duration constructionGameTime = Duration.ofDays(90);
    public LocalDateTime finishDate;
    public boolean isUnderConstruction = true;

    LocalDateTime constructionStartedAt; // IN-GAME TIME

    public Building(LocalDateTime constructionStartedAt, String texturePath) {
        this.constructionStartedAt = constructionStartedAt;
        finishDate = constructionStartedAt.plus(constructionGameTime);
        this.texturePath = texturePath;
    }

    public Building(LocalDateTime constructionStartedAt, String texturePath, Duration constructionGameTime) {
        this.constructionStartedAt = constructionStartedAt;
        this.constructionGameTime = constructionGameTime;
        finishDate = constructionStartedAt.plus(constructionGameTime);
    }

    public String getName() {
        return name;
    }
    public String getTexturePath() {
        return texturePath;
    }
    //public PolygonShape getShape() {
    //    return shape;
    //}
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

    public void update(LocalDateTime currentGameTime) {
        if (currentGameTime.isAfter(finishDate)) {
            isUnderConstruction = false;
        }
    }

    public float getConstructionPercent(LocalDateTime currentGameTime) {
        Duration timePassed = Duration.between(constructionStartedAt, currentGameTime);
        float percent = MathUtils.clamp((float)timePassed.getSeconds() / (float)constructionGameTime.getSeconds(), 0, 1);
        return percent * 100;
    }

    public int getSize() {
        return size;
    }
}
