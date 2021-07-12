package app.activities;

import java.time.LocalDateTime;

public interface Scheduler {
    Schedulable scheduleSegment(LocalDateTime localDateTime, int durationInSeconds);
}
