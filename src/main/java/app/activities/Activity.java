package app.activities;

import java.io.Serializable;

public abstract class Activity implements Serializable, Scheduler {
    private static int activityMaxId = 0;

    private final int id;
    private final String name;
    private String description;

    private int valueInClocks;

    private int durationInSeconds;
    private boolean isPleasure;
    public Activity(
            String name,
            String description,
            int valueInClocks,
            int durationInSeconds,
            boolean isPleasure) {
        this.id = activityMaxId;
        activityMaxId++;
        this.name = name;
        this.description = description;

        //TODO think if invalid initialization arguments of below fields should throw exceptions
        this.valueInClocks = Math.max(valueInClocks, 1);
        this.durationInSeconds = Math.max(durationInSeconds, 1);
        this.isPleasure = isPleasure;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getValueInClocks() {
        return valueInClocks;
    }

    public void setValueInClocks(int valueInClocks) {
        this.valueInClocks = valueInClocks;
    }

    public int getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(int durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public boolean isPleasure() {
        return isPleasure;
    }
}
