package app.activities;

import java.time.LocalDateTime;

public class ProjectActivity extends Activity implements Scheduler {
    //TODO add bonus for completing whole project (bonus clocks)

    public ProjectActivity(String name, String description, int valueInClocks, int durationInSeconds, boolean isPleasure) {
        super(name, description, valueInClocks, durationInSeconds, isPleasure);
    }

    @Override
    public Segment scheduleSegment(LocalDateTime localDateTime, int durationInSeconds) {
        return new Segment(this, localDateTime, durationInSeconds);
    }

    public void update(int durationInSeconds) {
        setDurationInSeconds(Math.max(0, getDurationInSeconds() - durationInSeconds));
        //TODO change amount of clocks accordingly to the amount of seconds left
    }
}
