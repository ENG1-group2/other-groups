package eng1.unisim.models;

/**
 * represents a building that can be placed in the game world
 * buildings affect the game's economy and student satisfaction
 */
public class Building {
    // cost to purchase this building
    private final int cost;
    // how much this building affects student satisfaction
    private final int effectOnSatisfaction;
    // how much this building affects university income
    private final int effectOnIncome;
    // display name of the building
    private final String name;

    /**
     * creates a new building with specified properties
     * @param name the display name of the building
     * @param cost the purchase cost in game currency
     * @param effectOnSatisfaction the impact on student satisfaction
     * @param effectOnIncome the impact on university income
     */
    public Building(String name, int cost, int effectOnSatisfaction, int effectOnIncome) {
        this.name = name;
        this.cost = cost;
        this.effectOnSatisfaction = effectOnSatisfaction;
        this.effectOnIncome = effectOnIncome;
    }

    // returns the cost to purchase this building
    public int getCost() {
        return cost;
    }

    // returns how much this building affects satisfaction
    public int getSatisfaction() {
        return effectOnSatisfaction;
    }

    // returns how much this building affects income
    public int getIncome() {
        return effectOnIncome;
    }

    // returns the display name of the building
    public String getName() {
        return name;
    }
}
