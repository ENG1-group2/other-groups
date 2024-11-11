package eng1.unisim.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import eng1.unisim.managers.UIManager.BuildingSelectionCallback;
import eng1.unisim.models.Building;

import java.util.HashMap;
import java.util.Map;

public class BuildingInventoryView extends Table implements Disposable {
    private static final String[] BUILDING_TYPES = { "Accommodation", "Learning", "Dining", "Recreation" };
    private final Map<String, Label> buildingCounters = new HashMap<>();
    private final Map<String, Integer> buildingCounts = new HashMap<>();
    private final Map<String, Texture> buildingTextures = new HashMap<>();

    public BuildingInventoryView(BitmapFont font, BuildingSelectionCallback buildingCallback) {
        this.top().left();
        this.setFillParent(true);
        this.pad(10);

        initializeBuildingCounts();
        loadTextures();
        createBuildingButtons(font, buildingCallback);
    }

    private void initializeBuildingCounts() {
        for (String type : BUILDING_TYPES) {
            buildingCounts.put(type, 0);
        }
    }

    private void loadTextures() {
        for (String type : BUILDING_TYPES) {
            buildingTextures.put(type, new Texture(Gdx.files.internal("buildings/" + type.toLowerCase() + ".png")));
        }
    }

    private void createBuildingButtons(BitmapFont font, BuildingSelectionCallback buildingCallback) {
        Label.LabelStyle counterStyle = new Label.LabelStyle(font, Color.WHITE);

        for (String type : BUILDING_TYPES) {
            ImageButton.ImageButtonStyle style = createButtonStyle(type);
            ImageButton button = createBuildingButton(type, style, buildingCallback);
            Label counter = new Label("0", counterStyle);
            buildingCounters.put(type, counter);

            this.add(button).size(100, 100).pad(5);
            this.add(counter).pad(5).row();
        }
    }

    private ImageButton.ImageButtonStyle createButtonStyle(String type) {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        Texture texture = buildingTextures.get(type);
        style.imageUp = new TextureRegionDrawable(new TextureRegion(texture));
        style.imageChecked = new TextureRegionDrawable(new TextureRegion(texture));
        style.imageChecked.setMinWidth(90);
        style.imageChecked.setMinHeight(90);
        return style;
    }

    private ImageButton createBuildingButton(String type, ImageButton.ImageButtonStyle style,
                                             BuildingSelectionCallback buildingCallback) {
        ImageButton button = new ImageButton(style);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Building building = new Building(type, 50000, 10, 5000);
                buildingCallback.onBuildingSelected(building);
                button.setChecked(true);
            }
        });
        return button;
    }

    public void updateBuildingCount(String buildingType, int count) {
        buildingCounts.put(buildingType, count);
        Label counter = buildingCounters.get(buildingType);
        if (counter != null) {
            counter.setText(String.valueOf(count));
        }
    }

    @Override
    public void dispose() {
        for (Texture texture : buildingTextures.values()) {
            texture.dispose();
        }
    }
}
