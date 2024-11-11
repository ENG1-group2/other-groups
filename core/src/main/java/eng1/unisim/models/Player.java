package eng1.unisim.models;

/**
 * represents the player/manager of the university simulation game
 * handles the core player metrics: funds, income, and satisfaction
 */
public class Player {
    // tracks the player's current money/budget
    private int funds;
    // tracks how much money the player earns per time period
    private int income;
    // represents overall university satisfaction/rating (0-100)
    private int satisfaction;

    /**
     * creates a new player with default starting values
     */
    public Player() {
        funds = 100_000; // initial funds *may need tweak
        income = 1_000; // initial income *may need tweak
        satisfaction = 50; // initial satisfaction *may need tweak
    }

    /**
     * attempts to place a new building, updating player metrics if affordable
     * @param building the building to be placed
     */
    public void placeBuilding(Building building) {
        if (funds >= building.getCost()) {
            funds -= building.getCost();
            income += building.getIncome();
            satisfaction += building.getSatisfaction();
        }
    }

    /**
     * processes game events that affect the player's metrics
     * @param events the event to process
     */
    public void handleEvent(Events events) {
        funds += events.getEffectOnFunds();
        income *= events.getIncomeMultiplier();
        satisfaction += events.getEffectOnSatisfaction();
    }

    // getter for current funds
    public int getFunds() {
        return funds;
    }

    // setter for funds amount
    public void setFunds(int funds) {
        this.funds = funds;
    }

    // getter for current income
    public int getIncome() {
        return income;
    }

    // setter for income amount
    public void setIncome(int income) {
        this.income = income;
    }

    // getter for current satisfaction level
    public int getSatisfaction() {
        return satisfaction;
    }
}
