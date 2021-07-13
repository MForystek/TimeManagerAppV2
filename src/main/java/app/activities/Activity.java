package app.activities;

import java.io.Serializable;

public abstract class Activity implements Serializable {
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

        //TODO think if undermentioned invalid initialization arguments should throw exceptions

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

    public int getDurationInSeconds() {
        return durationInSeconds;
    }

    public boolean isPleasure() {
        return isPleasure;
    }
}
