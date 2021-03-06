package app.activities;

import java.time.LocalDateTime;

public class Segment implements Comparable<Segment> {
    private final Activity parent;
    private LocalDateTime scheduledDateTime;
    private int durationInSeconds;

    public Segment(Activity parent, LocalDateTime scheduledDateTime, int durationInSeconds) {
        this.parent = parent;
        this.scheduledDateTime = scheduledDateTime;
        this.durationInSeconds = durationInSeconds;
    }

    //TODO implement startOfSegment and endOfSegment
    public void startOfSegment() {
    }

    public void endOfSegment() {
    }

    public Activity getParent() {
        return parent;
    }

    public LocalDateTime getDateTimeOfStart() {
        return scheduledDateTime;
    }

    public LocalDateTime getDateTimeOfEnd() {
        return scheduledDateTime.plusSeconds(durationInSeconds);
    }

    @Override
    public int compareTo(Segment other) {
        if (scheduledDateTime.isBefore(other.scheduledDateTime)) {
            return -1;
        } else if (scheduledDateTime.isAfter(other.scheduledDateTime)) {
            return 1;
        } else {
            return 0;
        }
    }
}
