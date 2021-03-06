package app;

import app.activities.*;
import app.exceptions.SellingException;
import app.exceptions.BuyingException;
import app.exceptions.DeletingException;
import app.exceptions.AddingException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

public class ActivitiesContainer implements Serializable {
    private Map<Integer, Activity> notBoughtPleasures;
    private Map<Integer, Activity> notScheduledActivities;
    private List<Segment> scheduledSegments;
    private Map<Integer, Segment> completedSegments;
    private Map<Integer, ProjectActivity> completedProjects;
    private SegmentsExecutor segmentsExecutor;
    //TODO after segment is ended move it to completedSegments
    //TODO after project is ended move it to completedProjects

    public ActivitiesContainer() {
        notBoughtPleasures = new HashMap<>();
        notScheduledActivities = new HashMap<>();
        scheduledSegments = new ArrayList<>();
        completedSegments = new HashMap<>();
        completedProjects = new HashMap<>();
        segmentsExecutor = new SegmentsExecutor(scheduledSegments);
        segmentsExecutor.start();
    }

    public void addActivity(Activity activity) throws AddingException {
        if (activity.isPleasure()) {
            addPleasure(activity);
        } else {
            addDuty(activity);
        }
    }

    private void addPleasure(Activity activity) throws AddingException {
        if (notBoughtPleasures.containsValue(activity) || notScheduledActivities.containsValue(activity)) {
            throw new AddingException(AddingException.PLEASURE_ALREADY_ADDED);
        }
        notBoughtPleasures.put(activity.getId(), activity);
    }

    private void addDuty(Activity activity) throws AddingException {
        if (notScheduledActivities.containsValue(activity)) {
            throw new AddingException(AddingException.DUTY_ALREADY_ADDED);
        }
        notScheduledActivities.put(activity.getId(), activity);
    }

    public void delActivity(Activity activity) throws DeletingException {
        if (activity.isPleasure()) {
            delPleasure(activity);
        } else {
            delDuty(activity);
        }
    }

    private void delPleasure(Activity activity) throws DeletingException {
        if (notBoughtPleasures.containsValue(activity)) {
            notBoughtPleasures.remove(activity.getId());
        } else if (notScheduledActivities.containsValue(activity)) {
            throw new DeletingException(DeletingException.SELL_PLEASURE_BEFORE_DELETING);
        } else {
            throw new DeletingException(DeletingException.ADD_PLEASURE_BEFORE_DELETING);
        }
    }

    private void delDuty(Activity activity) throws DeletingException {
        if (notScheduledActivities.containsValue(activity)) {
            notScheduledActivities.remove(activity.getId());
        } else {
            throw new DeletingException(DeletingException.ADD_DUTY_BEFORE_DELETING);
        }
    }

    public void buyPleasure(Activity activity) throws BuyingException {
        checkBuyConditions(activity);
        notBoughtPleasures.remove(activity.getId());
        notScheduledActivities.put(activity.getId(), activity);
    }

    private void checkBuyConditions(Activity activity) throws BuyingException {
        if (!activity.isPleasure()) {
            throw new BuyingException(BuyingException.CANNOT_BUY_DUTY);
        }
        if (!notBoughtPleasures.containsKey(activity.getId())) {
            throw new BuyingException(BuyingException.PLEASURE_NOT_FOUND);
        }
    }

    public void sellPleasure(Activity activity) throws SellingException {
        checkSellConditions(activity);
        notScheduledActivities.remove(activity.getId());
        notBoughtPleasures.put(activity.getId(), activity);
    }

    private void checkSellConditions(Activity activity) throws SellingException {
         if (!activity.isPleasure()) {
             throw new SellingException(SellingException.CANNOT_SELL_DUTY);
         }
         if (!notScheduledActivities.containsKey(activity.getId())) {
             throw new SellingException(SellingException.PLEASURE_NOT_FOUND);
         }
    }

    public void schedulePeriodicActivity(PeriodicActivity periodicActivity, LocalDateTime scheduleDateTime, int durationInSeconds) {
        Segment segment = periodicActivity.scheduleSegment(scheduleDateTime, durationInSeconds);
        scheduledSegments.add(segment);
        Collections.sort(scheduledSegments);
    }

    public void scheduleProjectActivity(ProjectActivity projectActivity, LocalDateTime scheduleDateTime, int durationInSeconds) {
        Segment segment = projectActivity.scheduleSegment(scheduleDateTime, durationInSeconds);
        scheduledSegments.add(segment);
        Collections.sort(scheduledSegments);
    }

    public boolean notBoughtPleasuresContainsValue(Activity activity) {
        return notBoughtPleasures.containsValue(activity);
    }

    public boolean notScheduledActivitiesContainsValue(Activity activity) {
        return notScheduledActivities.containsValue(activity);
    }
}
