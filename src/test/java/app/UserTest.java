package app;

import app.exceptions.BuyingException;
import app.exceptions.ActivityNotEligibleToBeSoldException;
import app.exceptions.DeletingException;
import app.activities.PeriodicActivity;
import app.activities.ProjectActivity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    User user;

    @BeforeEach
    void before() {
        user = new User();
    }

    @AfterEach
    void after() {
        user = null;
    }

    //TODO Change tests to be compatible with changes in User

    @Test
    void addActivityDuty() {
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
                false);

        user.addActivity(periodicActivity);
        user.addActivity(projectActivity);

        assertTrue(user.notScheduledActivities.containsValue(periodicActivity));
        assertTrue(user.notScheduledActivities.containsValue(projectActivity));
    }

    @Test
    void addActivityPleasure() {
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

        user.addActivity(periodicActivity);
        user.addActivity(projectActivity);

        assertTrue(user.activitiesContainer.notBoughtPleasuresContainsValue(periodicActivity));
        assertTrue(user.activitiesContainer.notBoughtPleasuresContainsValue(projectActivity));
    }

    @Test
    void delActivityDuty() {
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
                false);

        user.addActivity(periodicActivity);
        user.addActivity(projectActivity);

        try {
            user.delActivity(periodicActivity);
            user.delActivity(projectActivity);
        } catch (DeletingException e) {
            e.printStackTrace();
            fail();
        }

        assertFalse(user.activitiesContainer.notScheduledActivitiesContainsValue(periodicActivity));
        assertFalse(user.activitiesContainer.notScheduledActivitiesContainsValue(projectActivity));
    }

    @Test
    void delActivityPleasure() {
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

        user.addActivity(periodicActivity);
        user.addActivity(projectActivity);

        try {
            user.delActivity(periodicActivity);
            user.delActivity(projectActivity);
        } catch (DeletingException e) {
            e.printStackTrace();
            fail();
        }

        assertFalse(user.notBoughtPleasures.containsValue(periodicActivity));
        assertFalse(user.notBoughtPleasures.containsValue(projectActivity));
    }

    @Test
    void deletingNotAddedActivityThrowsException() {
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
                false);
        assertThrows(DeletingException.class, () -> user.delActivity(periodicActivity));
        assertThrows(DeletingException.class, () -> user.delActivity(projectActivity));
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


        user.addActivity(periodicActivity);
        user.addActivity(projectActivity);

        user.setClocks(1000);

        try {
            user.buyPleasure(periodicActivity);
            user.buyPleasure(projectActivity);
        } catch (BuyingException e) {
            e.printStackTrace();
        }

        assertTrue(user.notScheduledActivities.containsValue(periodicActivity));
        assertTrue(user.notScheduledActivities.containsValue(projectActivity));
    }

    @Test
    void buyingDutyThrowsException() {
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
                false);

        user.addActivity(periodicActivity);
        user.addActivity(projectActivity);

        assertThrows(BuyingException.class, () -> user.buyPleasure(periodicActivity));
        assertThrows(BuyingException.class, () -> user.buyPleasure(projectActivity));
    }

    @Test
    void buyingNotAddedPleasureThrowsException() {
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

        assertThrows(BuyingException.class, () -> user.buyPleasure(periodicActivity));
        assertThrows(BuyingException.class, () -> user.buyPleasure(projectActivity));
    }

    @Test
    void buyingWithoutEnoughClocksThrowsException() {
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

        user.addActivity(periodicActivity);
        user.addActivity(projectActivity);

        assertThrows(BuyingException.class, () -> user.buyPleasure(periodicActivity));
        assertThrows(BuyingException.class, () -> user.buyPleasure(projectActivity));
    }

    @Test
    void buyingPleasureRemovesClocks() {
        PeriodicActivity periodicActivity = new PeriodicActivity(
                "Periodic",
                "Do it as many times as you want",
                13,
                5,
                true);


        user.setClocks(20);
        user.addActivity(periodicActivity);
        try {
            user.buyPleasure(periodicActivity);
        } catch (BuyingException e) {
            e.printStackTrace();
        }

        assertEquals(7, user.getClocks());
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

        user.addActivity(periodicActivity);
        user.addActivity(projectActivity);
        user.setClocks(500);
        try {
            user.buyPleasure(periodicActivity);
            user.buyPleasure(projectActivity);

            user.sellPleasure(periodicActivity);
            user.sellPleasure(projectActivity);
        } catch (BuyingException | ActivityNotEligibleToBeSoldException e) {
            e.printStackTrace();
        }

        assertFalse(user.notScheduledActivities.containsValue(periodicActivity));
        assertFalse(user.notScheduledActivities.containsValue(projectActivity));
    }

    @Test
    void sellingDutyThrowsException() {
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
                false);

        user.addActivity(periodicActivity);
        user.addActivity(projectActivity);

        assertThrows(ActivityNotEligibleToBeSoldException.class, () -> user.sellPleasure(periodicActivity));
        assertThrows(ActivityNotEligibleToBeSoldException.class, () -> user.sellPleasure(projectActivity));
    }

    @Test
    void sellingNotBoughtActivityThrowsException() {
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

        user.addActivity(periodicActivity);

        assertThrows(ActivityNotEligibleToBeSoldException.class, () -> user.sellPleasure(periodicActivity));
        assertThrows(ActivityNotEligibleToBeSoldException.class, () -> user.sellPleasure(projectActivity));
    }

    @Test
    void sellingPleasureAddsClocks() {
        PeriodicActivity periodicActivity = new PeriodicActivity(
                "Periodic",
                "Do it as many times as you want",
                13,
                5,
                true);


        user.setClocks(20);
        user.addActivity(periodicActivity);
        try {
            user.buyPleasure(periodicActivity);
            user.sellPleasure(periodicActivity);
        } catch (BuyingException | ActivityNotEligibleToBeSoldException e) {
            e.printStackTrace();
        }

        assertEquals(20, user.getClocks());
    }

    @Test
    void settingClocks() {
        user.setClocks(-500);
        assertEquals(0, user.getClocks());
    }
}