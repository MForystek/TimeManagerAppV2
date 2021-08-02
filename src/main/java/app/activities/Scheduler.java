package app.activities;

import java.time.LocalDateTime;

public interface Scheduler {
    Segment scheduleSegment(LocalDateTime localDateTime, int durationInSeconds);
}
