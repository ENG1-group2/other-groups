package eng1.unisim;

import java.util.ArrayList;
import java.util.List;

public class University {
    private List<Building> buildings;

    public University() {
        buildings = new ArrayList<>();
    }

    public void addBuilding(Building building) {
        buildings.add(building);
    }
}
