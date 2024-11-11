package eng1.unisim.models;

/**
 * represents random events that can occur during gameplay, affecting the university's stats
 */
public class Events {
    // description of the event that will be displayed to the player
    private String description;
    // immediate change to university funds when event occurs
    private int effectOnFunds;
    // immediate change to student satisfaction when event occurs
    private int effectOnSatisfaction;
    // multiplier applied to income while event is active
    private float incomeMultiplier;
    // how many turns the event lasts for
    private int duration;
    // chance of this event occurring (0-1)
    private float probability;
    // stores when the event started to track duration
    public int startTime;

    /**
     * creates a new event with specified effects and properties
     */
    public Events(String description, int effectOnFunds, int effectOnSatisfaction, float incomeMultiplier, int duration,
            float probability) {
        this.description = description;
        this.effectOnFunds = effectOnFunds;
        this.effectOnSatisfaction = effectOnSatisfaction;
        this.incomeMultiplier = incomeMultiplier;
        this.duration = duration;
        this.probability = probability;
    }

    // getter methods for accessing event properties
    public int getEffectOnFunds() {
        return effectOnFunds;
    }

    public int getEffectOnSatisfaction() {
        return effectOnSatisfaction;
    }

    public float getIncomeMultiplier() {
        return incomeMultiplier;
    }

    public int getDuration() {
        return duration;
    }

    public float getProbability() {
        return probability;
    }

    public String getDescription() {
        return description;
    }

    /**
     * records when the event starts to track its duration
     * @param currentTime the current game turn when event starts
     */
    public void setStartTime(int currentTime) {
        this.startTime = currentTime;
    }
}
