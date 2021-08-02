package app;

import app.exceptions.BuyingException;
import app.exceptions.SellingException;
import app.exceptions.DeletingException;
import app.activities.*;
import app.exceptions.AddingException;

import java.io.Serializable;
import java.time.LocalDateTime;

public class User implements Serializable {
    private ActivitiesContainer activitiesContainer;
    private int clocks;

    public User() {
        activitiesContainer = new ActivitiesContainer();
        clocks = 0;
    }

    public void addActivity(Activity activity) throws AddingException {
        activitiesContainer.addActivity(activity);
    }

    public void delActivity(Activity activity) throws DeletingException {
        activitiesContainer.delActivity(activity);
    }

    public void buyPleasure(Activity activity) throws BuyingException {
        if (hasEnoughClocks(activity)) {
            activitiesContainer.buyPleasure(activity);
            clocks -= activity.getValueInClocks();
        } else {
            throw new BuyingException(BuyingException.NOT_ENOUGH_CLOCKS);
        }
    }

    private boolean hasEnoughClocks(Activity activity) {
        return clocks >= activity.getValueInClocks() || !activity.isPleasure();
    }

    public void sellPleasure(Activity activity) throws SellingException {
        activitiesContainer.sellPleasure(activity);
        clocks += activity.getValueInClocks();
    }

    public void scheduleSegment(Scheduler scheduler, LocalDateTime localDateTime, int durationInSeconds) {

    }

    public void cancelActivity() {

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

    public ActivitiesContainer getActivitiesContainer() {
        return activitiesContainer;
    }
}