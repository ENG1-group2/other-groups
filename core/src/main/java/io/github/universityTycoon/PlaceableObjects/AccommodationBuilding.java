package io.github.universityTycoon.PlaceableObjects;

import com.badlogic.gdx.graphics.Texture;

import java.time.LocalDateTime;

public class AccommodationBuilding extends Building {
    int rentPricePPM;
    int commonRoomsCount;
    Texture texture = new Texture("images/new_uni_style_assets.png");

    public AccommodationBuilding(LocalDateTime constructionStartedAt) {
        super(constructionStartedAt);
    }

    @Override
    public float calculateSatisfaction() {
        return 0f;
    }
}
