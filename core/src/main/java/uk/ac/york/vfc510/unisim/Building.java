package uk.ac.york.vfc510.unisim;

public class Building {
    private int cost;
    private int effectOnSatisfaction;
    private int effectOnIncome;
    private String name;

    public Building(String name, int cost, int effectOnSatisfaction, int effectOnIncome){
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
