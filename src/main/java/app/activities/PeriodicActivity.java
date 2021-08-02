package app.activities;

import java.time.LocalDateTime;

public class PeriodicActivity extends Activity {

    public PeriodicActivity(String name, String description, int valueInClocks, int durationInSeconds, boolean isPleasure) {
        super(name, description, valueInClocks, durationInSeconds, isPleasure);
    }

    @Override
    public Segment scheduleSegment(LocalDateTime localDateTime, int durationInSeconds) {
        return new Segment(this, localDateTime, durationInSeconds);
    }
}
