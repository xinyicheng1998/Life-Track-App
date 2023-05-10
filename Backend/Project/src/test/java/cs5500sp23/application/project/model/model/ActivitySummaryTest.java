package cs5500sp23.application.project.model.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


public class ActivitySummaryTest {

  @Test
  public void testConstructorWithAllFields() {
    ActivitySummary activitySummary = new ActivitySummary(ActivityType.WALKING, "group", 1.5, 2.0, 1000, 150.0);

    assertEquals(ActivityType.WALKING.getType(), activitySummary.getActivityType());
    assertEquals("group", activitySummary.getGroup());
    assertEquals(Double.valueOf(1.5), activitySummary.getDuration());
    assertEquals(Double.valueOf(2.0), activitySummary.getDistance());
    assertEquals(Integer.valueOf(1000), activitySummary.getSteps());
    assertEquals(Double.valueOf(150.0), activitySummary.getCalories());
  }

  @Test
  public void testConstructorWithRequiredFields() {
    ActivitySummary activitySummary = new ActivitySummary(ActivityType.CYCLING, "group", 3.0, 4.0);

    assertEquals(ActivityType.CYCLING.getType(), activitySummary.getActivityType());
    assertEquals("group", activitySummary.getGroup());
    assertEquals(Double.valueOf(3.0), activitySummary.getDuration());
    assertEquals(Double.valueOf(4.0), activitySummary.getDistance());
    assertNull(activitySummary.getSteps());
    assertNull(activitySummary.getCalories());
  }

  @Test
  public void testGettersAndSetters() {
    ActivitySummary activitySummary = new ActivitySummary(ActivityType.RUNNING, "group", 1.5, 2.0, 1000, 150.0);

    assertEquals(ActivityType.RUNNING.getType(), activitySummary.getActivityType());
    assertEquals("group", activitySummary.getGroup());
    assertEquals(Double.valueOf(1.5), activitySummary.getDuration());
    assertEquals(Double.valueOf(2.0), activitySummary.getDistance());
    assertEquals(Integer.valueOf(1000), activitySummary.getSteps());
    assertEquals(Double.valueOf(150.0), activitySummary.getCalories());

    activitySummary.setGroup("newGroup");
    activitySummary.setDuration(2.0);
    activitySummary.setDistance(3.0);
    activitySummary.setSteps(2000);
    activitySummary.setCalories(300.0);

    assertEquals("newGroup", activitySummary.getGroup());
    assertEquals(Double.valueOf(2.0), activitySummary.getDuration());
    assertEquals(Double.valueOf(3.0), activitySummary.getDistance());
    assertEquals(Integer.valueOf(2000), activitySummary.getSteps());
    assertEquals(Double.valueOf(300.0), activitySummary.getCalories());
  }
}