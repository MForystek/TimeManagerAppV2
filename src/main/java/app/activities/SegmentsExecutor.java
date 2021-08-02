package app.activities;

import java.time.LocalDateTime;
import java.util.List;

public class SegmentsExecutor extends Thread {
    //TODO notify user that he must update amount of clocks when segment of duty is ended
    //TODO only first element in a list is checked and then the next one. That solution should improve the performance
    private List<Segment> scheduledSegments;

    public SegmentsExecutor(List<Segment> scheduledSegments) {
        this.scheduledSegments = scheduledSegments;
    }

    @Override
    public void run() {
        try {
            updateSegmentsStates();
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {}
    }

    private void updateSegmentsStates() {
        for (var segment : scheduledSegments) {
            if (segment.getDateTimeOfEnd().isBefore(LocalDateTime.now())) {
                segment.endOfSegment();
            } else if (segment.getDateTimeOfStart().isBefore(LocalDateTime.now())) {
                segment.startOfSegment();
            }
        }
    }
}
