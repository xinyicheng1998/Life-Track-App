package cs5500sp23.application.project.model.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ActivityTypeTest {

  @Test
  public void testBuildType() {
    assertEquals(ActivityType.WALKING, ActivityType.buildType("walking"));
    assertEquals(ActivityType.TRANSPORT, ActivityType.buildType("transport"));
    assertEquals(ActivityType.CYCLING, ActivityType.buildType("cycling"));
    assertEquals(ActivityType.RUNNING, ActivityType.buildType("running"));

    assertNull(ActivityType.buildType("jogging"));
  }
}