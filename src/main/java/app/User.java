package app;

import app.exceptions.ActivityNotEligibleToBeBoughtException;
import app.exceptions.ActivityNotEligibleToBeSoldException;
import app.exceptions.ActivityNotFoundException;
import app.activities.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User implements Serializable {

    private ActivitiesContainer activitiesContainer;

    protected Map<Integer, Activity> notBoughtPleasures;
    protected Map<Integer, Activity> notScheduledActivities;
    protected List<Schedulable> scheduledSegments;
    protected Map<Integer, Activity> completedActivities;
    private int clocks;

    public User() {
        activitiesContainer = new ActivitiesContainer();
        notBoughtPleasures = new HashMap<>();
        notScheduledActivities = new HashMap<>();
        scheduledSegments = new ArrayList<>();
        completedActivities = new HashMap<>();
        clocks = 0;
    }

    public void addActivity(Activity activity) {
        if (activity.isPleasure() && !notScheduledActivities.containsValue(activity)) {
            notBoughtPleasures.put(activity.getId(), activity);
        } else {
            notScheduledActivities.put(activity.getId(), activity);
        }
    }

    public void delActivity(Activity activity) throws ActivityNotFoundException {
        if (activity.isPleasure() && notBoughtPleasures.containsValue(activity)) {
                notBoughtPleasures.remove(activity.getId());
        } else if (!activity.isPleasure() && notScheduledActivities.containsValue(activity)) {
                notScheduledActivities.remove(activity.getId());
        } else {
            throw new ActivityNotFoundException();
        }
    }

    public void buyPleasure(Activity activity) throws ActivityNotEligibleToBeBoughtException {
        if (isEligibleToBeBought(activity)) {
            notScheduledActivities.put(activity.getId(), activity);
            notBoughtPleasures.remove(activity.getId());
            clocks -= activity.getValueInClocks();
        } else {
            throw new ActivityNotEligibleToBeBoughtException();
        }
    }

    private boolean isEligibleToBeBought(Activity activity) {
        return activity.isPleasure() && notBoughtPleasures.containsKey(activity.getId()) && clocks >= activity.getValueInClocks();
    }

    public void sellPleasure(Activity activity) throws ActivityNotEligibleToBeSoldException {
        if (isEligibleToBeSold(activity)) {
            notScheduledActivities.remove(activity.getId());
            notBoughtPleasures.put(activity.getId(), activity);
            clocks += activity.getValueInClocks();
        } else {
            throw new ActivityNotEligibleToBeSoldException();
        }
    }

    private boolean isEligibleToBeSold(Activity activity) {
        return activity.isPleasure() && notScheduledActivities.containsKey(activity.getId());
    }

    public void scheduleSegment(Scheduler scheduler, LocalDateTime localDateTime, int durationInSeconds) {

    }

    public void cancelActivity() {

    }

    public void startActivity() {

    }

    public void finishActivity() {

    }

    public void saveActivitiesToTheFile() {

    }

    public void loadActivitiesToTheFile() {

    }

    protected void setClocks(int clocks) {
        this.clocks = Math.max(clocks, 0);
    }

    public int getClocks() {
        return clocks;
    }
}