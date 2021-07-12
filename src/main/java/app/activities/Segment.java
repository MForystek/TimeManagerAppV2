package app.activities;

import java.time.LocalDateTime;
import java.time.Month;

public class Segment implements Schedulable {
    private Activity parent;
    private LocalDateTime scheduledDateTime;
    private int durationInSeconds;

    public Segment(Activity parent, LocalDateTime scheduledDateTime, int durationInSeconds) {
        this.parent = parent;
        this.scheduledDateTime = scheduledDateTime;
        this.durationInSeconds = durationInSeconds;
    }
}
