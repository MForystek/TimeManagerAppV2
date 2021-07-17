package app;

import app.exceptions.BuyingException;
import app.exceptions.SellingException;
import app.exceptions.DeletingException;
import app.activities.*;
import app.exceptions.AddingException;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User implements Serializable {
    //TODO Change methods in class to call ActivitiesContainer instead of fields, then remove unnecessary fields
    //TODO Ensure that logic related to adding and removing clocks works after upper changes

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

    public ActivitiesContainer getActivitiesContainer() {
        return activitiesContainer;
    }
}