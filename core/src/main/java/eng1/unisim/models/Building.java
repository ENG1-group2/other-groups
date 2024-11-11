package eng1.unisim.models;

public class Building {
    private final int cost;
    private final int effectOnSatisfaction;
    private final int effectOnIncome;
    private final String name;

    public Building(String name, int cost, int effectOnSatisfaction, int effectOnIncome) {
        this.name = name;
        this.cost = cost;
        this.effectOnSatisfaction = effectOnSatisfaction;
        this.effectOnIncome = effectOnIncome;
    }

    public int getCost() {
        return cost;
    }

    public int getSatisfaction() {
        return effectOnSatisfaction;
    }

    public int getIncome() {
        return effectOnIncome;
    }

    public String getName() {
        return name;
    }
}
