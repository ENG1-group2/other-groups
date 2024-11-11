package eng1.unisim.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class University {
    private final List<Building> buildings;

    public University() {
        buildings = new ArrayList<>();
    }

    public void addBuilding(Building building) {
        buildings.add(building);
    }

    public List<Building> getBuildings() {
        return Collections.unmodifiableList(buildings);
    }
}
