package app;

import app.exceptions.*;
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
            user.addActivity(periodicActivity);
            user.addActivity(projectActivity);
        } catch (AddingException e) {
            e.printStackTrace();
            fail();
        }

        assertTrue(user.getActivitiesContainer().notBoughtPleasuresContainsValue(periodicActivity));
        assertTrue(user.getActivitiesContainer().notScheduledActivitiesContainsValue(projectActivity));
    }

    @Test
    void addingAddedActivityThrowsException() {
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
            user.addActivity(periodicActivity);
            user.addActivity(projectActivity);
        } catch (AddingException e) {
            e.printStackTrace();
            fail();
        }

        assertThrows(AddingException.class, () -> user.addActivity(periodicActivity));
        assertThrows(AddingException.class, () -> user.addActivity(projectActivity));
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
            user.addActivity(periodicActivity);
            user.addActivity(projectActivity);
            user.delActivity(periodicActivity);
            user.delActivity(projectActivity);
        } catch (AddingException | DeletingException e) {
            e.printStackTrace();
            fail();
        }

        assertFalse(user.getActivitiesContainer().notScheduledActivitiesContainsValue(periodicActivity));
        assertFalse(user.getActivitiesContainer().notBoughtPleasuresContainsValue(projectActivity));
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

        assertThrows(DeletingException.class, () -> user.delActivity(periodicActivity));
        assertThrows(DeletingException.class, () -> user.delActivity(projectActivity));
    }

    @Test
    void deletingBoughtPleasureThrowsException() {
        PeriodicActivity periodicActivity = new PeriodicActivity(
                "Periodic",
                "Do it as many times as you want",
                13,
                5,
                true);

        user.setClocks(1000);
        try {
            user.addActivity(periodicActivity);
            user.buyPleasure(periodicActivity);
        } catch (AddingException | BuyingException e) {
            e.printStackTrace();
        }

        assertThrows(DeletingException.class, () -> user.delActivity(periodicActivity));
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

        user.setClocks(1000);
        try {
            user.addActivity(periodicActivity);
            user.addActivity(projectActivity);
            user.buyPleasure(periodicActivity);
            user.buyPleasure(projectActivity);
        } catch (AddingException | BuyingException e) {
            e.printStackTrace();
            fail();
        }

        assertTrue(user.getActivitiesContainer().notScheduledActivitiesContainsValue(periodicActivity));
        assertTrue(user.getActivitiesContainer().notScheduledActivitiesContainsValue(projectActivity));
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

        try {
            user.addActivity(periodicActivity);
            user.addActivity(projectActivity);
        } catch (AddingException e) {
            e.printStackTrace();
            fail();
        }

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

        try {
            user.addActivity(periodicActivity);
            user.addActivity(projectActivity);
        } catch (AddingException e) {
            e.printStackTrace();
            fail();
        }

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

        try {
            user.addActivity(periodicActivity);
            user.buyPleasure(periodicActivity);
        } catch (AddingException | BuyingException e) {
            e.printStackTrace();
            fail();
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

        user.setClocks(500);
        try {
            user.addActivity(periodicActivity);
            user.addActivity(projectActivity);

            user.buyPleasure(periodicActivity);
            user.buyPleasure(projectActivity);

            user.sellPleasure(periodicActivity);
            user.sellPleasure(projectActivity);
        } catch (AddingException | BuyingException | SellingException e) {
            e.printStackTrace();
            fail();
        }

        assertFalse(user.getActivitiesContainer().notScheduledActivitiesContainsValue(periodicActivity));
        assertFalse(user.getActivitiesContainer().notScheduledActivitiesContainsValue(projectActivity));
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

        try {
            user.addActivity(periodicActivity);
            user.addActivity(projectActivity);
        } catch (AddingException e) {
            e.printStackTrace();
            fail();
        }

        assertThrows(SellingException.class, () -> user.sellPleasure(periodicActivity));
        assertThrows(SellingException.class, () -> user.sellPleasure(projectActivity));
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

        try {
            user.addActivity(periodicActivity);
        } catch (AddingException e) {
            e.printStackTrace();
            fail();
        }

        assertThrows(SellingException.class, () -> user.sellPleasure(periodicActivity));
        assertThrows(SellingException.class, () -> user.sellPleasure(projectActivity));
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
        try {
            user.addActivity(periodicActivity);
            user.buyPleasure(periodicActivity);
            user.sellPleasure(periodicActivity);
        } catch (AddingException | BuyingException | SellingException e) {
            e.printStackTrace();
            fail();
        }

        assertEquals(20, user.getClocks());
    }

    @Test
    void settingClocks() {
        user.setClocks(-500);
        assertEquals(0, user.getClocks());
    }
}