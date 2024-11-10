package io.github.universityTycoon.PlaceableObjects;

import com.badlogic.gdx.math.MathUtils;
import io.github.universityTycoon.BuildingTypes;

import java.time.Duration;
import java.time.LocalDateTime;

public class Building extends MapObject {

    public Duration constructionGameTime;
    public LocalDateTime finishDate;
    public boolean isUnderConstruction = true;

    // All of these building statistics (such as buildingCapacity), and those for its subclasses are currently static,
    // as we are not currently making subclasses of accommodation buildings for example, when that is the case,
    // those classes will have their own values, and at that point, these statistics should be made no longer static.
    public static float satisfactionBonus;

    // This is multipurpose, it's how many people can live in an accommodation building, how many can eat in the cafeteria
    // at any given time, how many can leisure? in a leisure building, and how many can be taught at once in a teaching building.
    // These should affect the satisfaction later on, meaning if 2000 students attend the university, the other buildings
    // should have a capacity that is high enough to accommodate for that, or the satisfaction score reduces.
    public static float buildingCapacity;

    LocalDateTime constructionStartedAt; // IN-GAME TIME

    public Building(LocalDateTime constructionStartedAt, String texturePath) {
        this.constructionStartedAt = constructionStartedAt;
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

    public static float getSatisfactionBonus() {
        return satisfactionBonus;
    }

    public static float getBuildingCapacity() {
        return buildingCapacity;
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

    public float getConstructionPercent(LocalDateTime currentGameTime) {
        Duration timePassed = Duration.between(constructionStartedAt, currentGameTime);
        float percent = MathUtils.clamp((float)timePassed.getSeconds() / (float)constructionGameTime.getSeconds(), 0, 1);
        return percent * 100;
    }

    public int getSize() {
        return size;
    }



    public static <T extends Building> T getObjectFromEnum(BuildingTypes type, LocalDateTime time) {
        return switch (type) {
            case Accommodation -> (T) new AccommodationBuilding(time);
            case Leisure -> (T) new LeisureBuilding(time);
            case Cafeteria -> (T) new Cafeteria(time);
            case Teaching -> (T) new TeachingBuilding(time);
            default -> null;
        };
    }
}
