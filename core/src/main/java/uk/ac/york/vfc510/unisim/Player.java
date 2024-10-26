package uk.ac.york.vfc510.unisim;

public class Player {
    private int funds;
    private int income;
    private int satisfaction;

    public Player() {
        funds = 100_000; // Initial funds *May need tweak
        income = 1_000; // Initial income *May need tweak
        satisfaction = 50; // Initial satisfaction *May need tweak
    }

    public void placeBuilding(Building building) {
        if (funds >= building.getCost()) {
            funds -= building.getCost();
            income += building.getIncome();
            satisfaction += building.getSatisfaction();
        }
    }

    public void handleEvent(Events events) {
        funds += events.getEffectOnFunds();
        income *= events.getIncomeMultiplier();
        satisfaction += events.getEffectOnSatisfaction();
    }

    public int getFunds() {
        return funds;
    }

    public void setFunds(int funds) {
        this.funds = funds;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public int getSatisfaction() {
        return satisfaction;
    }
}
