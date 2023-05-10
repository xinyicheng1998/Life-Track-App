package cs5500sp23.application.project.model.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class MostStepEntryTest {

  @Test
  public void testGetSummaryActivities() {
    List<ActivitySummary> summaryActivities = new ArrayList<>();
    ActivitySummary activity1 = new ActivitySummary(ActivityType.WALKING, "outdoor", 45.0, 2.5, 6000, 200.0);
    ActivitySummary activity2 = new ActivitySummary(ActivityType.CYCLING, "indoor", 30.0, 0.0, 0, 150.0);
    summaryActivities.add(activity1);
    summaryActivities.add(activity2);
    MostStepEntry entry = new MostStepEntry(summaryActivities, LocalDate.of(2023, 2, 18), 10000, 500.0);
    assertEquals(summaryActivities, entry.getSummaryActivities());
  }

  @Test
  public void testGetDate() {
    MostStepEntry entry = new MostStepEntry(new ArrayList<>(), LocalDate.of(2023, 2, 18), 10000, 500.0);
    assertEquals("20230218", entry.getDate());
  }

  @Test
  public void testGetTotalSteps() {
    MostStepEntry entry = new MostStepEntry(new ArrayList<>(), LocalDate.of(2023, 2, 18), 10000, 500.0);
    assertEquals(Integer.valueOf(10000), entry.getTotalSteps());
  }

  @Test
  public void testGetCalories() {
    MostStepEntry entry = new MostStepEntry(new ArrayList<>(), LocalDate.of(2023, 2, 18), 10000, 500.0);
    assertEquals(Double.valueOf(500.0), entry.getCalories());
  }

  @Test
  public void testGetInvalidDate() {
    assertThrows(DateTimeException.class, () -> {
      MostStepEntry entry = new MostStepEntry(new ArrayList<>(), LocalDate.of(2023, 2, 30), 10000, 500.0);
      entry.getDate();
    });
  }

}
