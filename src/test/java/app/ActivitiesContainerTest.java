package app;

import app.activities.PeriodicActivity;
import app.activities.ProjectActivity;
import app.exceptions.BuyingException;
import app.exceptions.DeletingException;
import app.exceptions.AddingException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActivitiesContainerTest {
    ActivitiesContainer activitiesContainer;

    @BeforeEach
    void before() {
        activitiesContainer = new ActivitiesContainer();
    }

    @AfterEach
    void after() {
        activitiesContainer = null;
    }

    @Test
    void addActivity() {
        PeriodicActivity periodicActivity = new PeriodicActivity(
                "Periodic",
                "Do it as many times as you want",
                13,
                5,
                true);
        ProjectActivity projectActivity = new ProjectActivity(
                "Project",
                "Something bigger",
                300,
                300,
                false);

        try {
            activitiesContainer.addActivity(periodicActivity);
            activitiesContainer.addActivity(projectActivity);
        } catch (AddingException e) {
            e.printStackTrace();
            fail();
        }

        assertTrue(activitiesContainer.notBoughtPleasuresContainsValue(periodicActivity));
        assertTrue(activitiesContainer.notScheduledActivitiesContainsValue(projectActivity));
    }

    @Test
    void addingTheSameActivityThrowsException() {
        PeriodicActivity periodicActivity = new PeriodicActivity(
                "Periodic",
                "Do it as many times as you want",
                13,
                5,
                true);
        ProjectActivity projectActivity = new ProjectActivity(
                "Project",
                "Something bigger",
                300,
                300,
                false);

        try {
            activitiesContainer.addActivity(periodicActivity);
            activitiesContainer.addActivity(periodicActivity);
        } catch (AddingException e) {
            assertEquals("Pleasure is already added", e.getMessage());
        }

        try {
            activitiesContainer.addActivity(projectActivity);
            activitiesContainer.addActivity(projectActivity);
        } catch (AddingException e) {
            assertEquals("Duty is already added", e.getMessage());
        }
    }

    @Test
    void delActivity() {
        PeriodicActivity periodicActivity = new PeriodicActivity(
                "Periodic",
                "Do it as many times as you want",
                13,
                5,
                false);
        ProjectActivity projectActivity = new ProjectActivity(
                "Project",
                "Something bigger",
                300,
                300,
                true);

        try {
            activitiesContainer.addActivity(periodicActivity);
            activitiesContainer.addActivity(projectActivity);
            activitiesContainer.delActivity(periodicActivity);
            activitiesContainer.delActivity(projectActivity);
        } catch (AddingException | DeletingException e) {
            e.printStackTrace();
            fail();
        }

        assertFalse(activitiesContainer.notScheduledActivitiesContainsValue(periodicActivity));
        assertFalse(activitiesContainer.notBoughtPleasuresContainsValue(projectActivity));
    }

    @Test
    void deletingNotAddedActivityThrowsException() {
        PeriodicActivity periodicActivity = new PeriodicActivity(
                "Periodic",
                "Do it as many times as you want",
                13,
                5,
                true);
        ProjectActivity projectActivity = new ProjectActivity(
                "Project",
                "Something bigger",
                300,
                300,
                false);

        try {
            activitiesContainer.delActivity(periodicActivity);
        } catch (DeletingException e) {
            assertEquals("Trying to delete not added pleasure", e.getMessage());
        }

        try {
            activitiesContainer.delActivity(projectActivity);
        } catch (DeletingException e) {
            assertEquals("Trying to delete not added duty", e.getMessage());
        }
    }

    @Test
    void deletingBoughtPleasureThrowsException() {
        PeriodicActivity periodicActivity = new PeriodicActivity(
                "Periodic",
                "Do it as many times as you want",
                13,
                5,
                true);

        try {
            activitiesContainer.addActivity(periodicActivity);
            activitiesContainer.buyPleasure(periodicActivity);
            activitiesContainer.delActivity(periodicActivity);
        } catch (AddingException | BuyingException e) {
            e.printStackTrace();
            fail();
        } catch (DeletingException e) {
            assertEquals("Pleasures must be sold before deletion", e.getMessage());
        }
    }

    @Test
    void buyPleasure() {
        PeriodicActivity periodicActivity = new PeriodicActivity(
                "Periodic",
                "Do it as many times as you want",
                13,
                5,
                true);
        ProjectActivity projectActivity = new ProjectActivity(
                "Project",
                "Something bigger",
                300,
                300,
                true);

        try {
            activitiesContainer.addActivity(periodicActivity);
            activitiesContainer.addActivity(projectActivity);
            activitiesContainer.buyPleasure(periodicActivity);
            activitiesContainer.buyPleasure(projectActivity);
        } catch (AddingException | BuyingException e) {
            e.printStackTrace();
            fail();
        }

        assertTrue(activitiesContainer.notScheduledActivitiesContainsValue(periodicActivity));
        assertTrue(activitiesContainer.notScheduledActivitiesContainsValue(projectActivity));
    }

    @Test
    void buyingDutyThrowsException() {
        PeriodicActivity periodicActivity = new PeriodicActivity(
                "Periodic",
                "Do it as many times as you want",
                13,
                5,
                false);

        try {
            activitiesContainer.addActivity(periodicActivity);
            activitiesContainer.buyPleasure(periodicActivity);
        } catch (AddingException e) {
            e.printStackTrace();
            fail();
        } catch (BuyingException e) {
            assertEquals("Duties cannot be bought", e.getMessage());
        }
    }

    @Test
    void buyingNotAddedOrBoughtPleasureThrowsException() {
        PeriodicActivity periodicActivity = new PeriodicActivity(
                "Periodic",
                "Do it as many times as you want",
                13,
                5,
                true);
        ProjectActivity projectActivity = new ProjectActivity(
                "Project",
                "Something bigger",
                300,
                300,
                true);

        try {
            activitiesContainer.addActivity(periodicActivity);
            activitiesContainer.buyPleasure(periodicActivity);
        } catch (AddingException | BuyingException e) {
            e.printStackTrace();
            fail();
        }

        try {
            activitiesContainer.buyPleasure(periodicActivity);
        } catch (BuyingException e) {
            assertEquals("Pleasure eligible for buying not found", e.getMessage());
        }

        try {
            activitiesContainer.buyPleasure(projectActivity);
        } catch (BuyingException e) {
            assertEquals("Pleasure eligible for buying not found", e.getMessage());
        }
    }
}