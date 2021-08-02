package app;

import app.exceptions.*;
import app.activities.PeriodicActivity;
import app.activities.ProjectActivity;
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
            assertEquals(AddingException.PLEASURE_ALREADY_ADDED, e.getMessage());
        }

        try {
            activitiesContainer.addActivity(projectActivity);
            activitiesContainer.addActivity(projectActivity);
        } catch (AddingException e) {
            assertEquals(AddingException.DUTY_ALREADY_ADDED, e.getMessage());
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
            assertEquals(DeletingException.ADD_PLEASURE_BEFORE_DELETING, e.getMessage());
        }

        try {
            activitiesContainer.delActivity(projectActivity);
        } catch (DeletingException e) {
            assertEquals(DeletingException.ADD_DUTY_BEFORE_DELETING, e.getMessage());
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
            assertEquals(DeletingException.SELL_PLEASURE_BEFORE_DELETING, e.getMessage());
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
            assertEquals(BuyingException.CANNOT_BUY_DUTY, e.getMessage());
        }
    }

    @Test
    void buyingNotAddedOrAlreadyBoughtPleasureThrowsException() {
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
            assertEquals(BuyingException.PLEASURE_NOT_FOUND, e.getMessage());
        }

        try {
            activitiesContainer.buyPleasure(projectActivity);
        } catch (BuyingException e) {
            assertEquals(BuyingException.PLEASURE_NOT_FOUND, e.getMessage());
        }
    }

    @Test
    void sellPleasure() {
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
            activitiesContainer.sellPleasure(periodicActivity);
            activitiesContainer.sellPleasure(projectActivity);
        } catch (AddingException | BuyingException | SellingException e) {
            e.printStackTrace();
            fail();
        }

        assertFalse(activitiesContainer.notScheduledActivitiesContainsValue(periodicActivity));
        assertFalse(activitiesContainer.notScheduledActivitiesContainsValue(projectActivity));
    }

    @Test
    void sellingDutyThrowsException() {
        PeriodicActivity periodicActivity = new PeriodicActivity(
                "Periodic",
                "Do it as many times as you want",
                13,
                5,
                false);

        try {
            activitiesContainer.addActivity(periodicActivity);
            activitiesContainer.sellPleasure(periodicActivity);
        } catch (AddingException e) {
            e.printStackTrace();
            fail();
        } catch (SellingException e) {
            assertEquals(SellingException.CANNOT_SELL_DUTY, e.getMessage());
        }
    }

    @Test
    void sellingNotAddedOrNotBoughtPleasureThrowsException() {
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
        } catch (AddingException e) {
            e.printStackTrace();
            fail();
        }

        try {
            activitiesContainer.sellPleasure(periodicActivity);
        } catch (SellingException e) {
            assertEquals(SellingException.PLEASURE_NOT_FOUND, e.getMessage());
        }

        try {
            activitiesContainer.sellPleasure(projectActivity);
        } catch (SellingException e) {
            assertEquals(SellingException.PLEASURE_NOT_FOUND, e.getMessage());
        }
    }
}