package uk.ac.york.vfc510.unisim;

public class Events {
    private String description;
    private int effectOnFunds;
    private int effectOnSatisfaction;
    private float incomeMultiplier;
    private int duration;
    private float probability;
    public int startTime;

    public Events(String description, int effectOnFunds, int effectOnSatisfaction, float incomeMultiplier, int duration,
            float probability) {
        this.description = description;
        this.effectOnFunds = effectOnFunds;
        this.effectOnSatisfaction = effectOnSatisfaction;
        this.incomeMultiplier = incomeMultiplier;
        this.duration = duration;
        this.probability = probability;
    }

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

    public void setStartTime(int currentTime) { // allows us to compare the duration of the event to check its end
        this.startTime = currentTime;
    }
}
