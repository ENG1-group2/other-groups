package io.github.universityTycoon.PlaceableObjects;

import com.badlogic.gdx.math.MathUtils;
import io.github.universityTycoon.BuildingTypes;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Building extends the abstract class MapObject, and is the superclass of all other building types.
 *
 * @param constructionGameTime The duration it will take for the building to be built.
 * @param finishDate The in game date the building will finish being built.
 * @param isUnderConstruction A property that states whether the building is under construction.
 */
public class Building extends MapObject {

    public static Duration constructionGameTime;
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

    /**
     * Constructor with the following parameters.
     *
     * @param constructionStartedAt The date the construction starts
     * @param texturePath The file path for the texture.
     */
    public Building(LocalDateTime constructionStartedAt, String texturePath) {
        this.constructionStartedAt = constructionStartedAt;
        this.texturePath = texturePath;
    }

    /**
     * Constructor with the following parameters.
     *
     * @param constructionStartedAt The date the construction starts
     * @param texturePath The file path for the texture.
     * @param constructionGameTime The time it will take to construct the building.
     */
    public Building(LocalDateTime constructionStartedAt, String texturePath, Duration constructionGameTime) {
        this.constructionStartedAt = constructionStartedAt;
        this.constructionGameTime = constructionGameTime;
        this.texturePath = texturePath;
        finishDate = constructionStartedAt.plus(constructionGameTime);
    }

    /**
     * Retrieves the building name.
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the file path of the texture associated with this object.
     * @return File path as a string.
     */
    public String getTexturePath() {
        return texturePath;
    }

    /**
     * Retrieves the satisfaction bonus provided by this building.
     *
     * @return The bonus.
     */
    public static float getSatisfactionBonus() {
        return satisfactionBonus;
    }

    /**
     * Retrieves the capacity of the building.
     *
     * @return The capacity.
     */
    public static float getBuildingCapacity() {
        return buildingCapacity;
    }

    /**
     * Retrieves the time taken to construct the building.
     *
     * @return The time taken to construct the building.
     */
    public static Duration getConstructionGameTime() {
        return constructionGameTime;
    }


    /**
     * Returns if where the object is stackable.
     *
     * @return true if the object is stackable, false otherwise.
     */
    public boolean getIsStackable() {
        return isStackable;
    }

    // As a percentage (not implemented)
    public float calculateSatisfaction() {
        return 1f;
    }

    /**
     * Updates isUnderConstruction when the building is finished being built.
     * @param currentGameTime the in game time
     */
    public void update(LocalDateTime currentGameTime) {
        if (currentGameTime.isAfter(finishDate)) {
            isUnderConstruction = false;
        }
    }

    /**
     * Returns the current construction progress as a percentage.
     * @param currentGameTime The current in game time.
     * @return The construction progress as a percentage.
     */
    public float getConstructionPercent(LocalDateTime currentGameTime) {
        Duration timePassed = Duration.between(constructionStartedAt, currentGameTime);
        float percent = MathUtils.clamp((float)timePassed.getSeconds() / (float)constructionGameTime.getSeconds(), 0, 1);
        return percent * 100;
    }

    /**
     * Gets the size of the building.
     * @return The size of the building
     */
    public int getSize() {
        return size;
    }


    /**
     * Creates and returns a new instance of a specific building type based on the provided enum type.
     * The type of building returned corresponds to the `BuildingTypes` enum value.
     *
     * @param type The type of building to create, specified as a `BuildingTypes` enum value.
     * @param time The time the building will be constructed at.
     * @param <T> A generic type parameter that extends the `Building` class, representing the type of building to create.
     *
     * @return A new instance of the specified building type, constructed at the specified time.
     */
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
