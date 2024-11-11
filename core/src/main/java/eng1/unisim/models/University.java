package eng1.unisim.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

// represents a university in the game world that contains multiple buildings
public class University {
    // stores all buildings that belong to this university
    private final List<Building> buildings;

    // creates a new empty university
    public University() {
        buildings = new ArrayList<>();
    }

    // adds a new building to the university
    public void addBuilding(Building building) {
        buildings.add(building);
    }

    // returns an unmodifiable view of all buildings in the university
    // prevents external code from modifying the buildings list directly
    public List<Building> getBuildings() {
        return Collections.unmodifiableList(buildings);
    }
}
