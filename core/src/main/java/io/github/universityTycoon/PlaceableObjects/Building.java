package io.github.universityTycoon.PlaceableObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import java.time.Duration;
import java.time.LocalDateTime;

public class Building implements MapObject {

    public Duration constructionGameTime = Duration.ofDays(365);
    public LocalDateTime finishDate;
    public boolean isUnderConstruction = true;

    LocalDateTime constructionStartedAt; // IN-GAME TIME

    public Building(LocalDateTime constructionStartedAt) {
        this.constructionStartedAt = constructionStartedAt;
        finishDate = constructionStartedAt.plus(constructionGameTime);
    }

    public Building(LocalDateTime constructionStartedAt, Duration constructionGameTime) {
        this.constructionStartedAt = constructionStartedAt;
        this.constructionGameTime = constructionGameTime;
        finishDate = constructionStartedAt.plus(constructionGameTime);
    }

    public String getName() {
        return name;
    }
    public Texture getTexture() {
        return texture;
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

    public void update(LocalDateTime currentGameTime) {
        if (currentGameTime.isAfter(finishDate)) {
            isUnderConstruction = false;
        }
    }
}
