package cs5500sp23.application.project.model.DAO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import cs5500sp23.application.project.model.model.MostStepEntry;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;

public class ActivitySummaryDAOTest {
  private ActivitySummaryDAO dao = ActivitySummaryDAO.getInstance();

  @Test
  public void testGetMostStepsInRangeByPersonID() {
    // Define test data
    String personID = "1";
    LocalDate startDate = LocalDate.of(2013, Month.FEBRUARY, 9);
    long daysAfter = 2;

    // Expect result
    LocalDate expectDate = LocalDate.of(2013,Month.FEBRUARY,11);
    // Call the method under test
    MostStepEntry result = dao.getMostStepsInRangeByPersonID(personID, startDate, daysAfter);

    System.out.println(result.getDate());
    // Verify the result
    assertNotNull(result);
    assertNotNull(result.getSummaryActivities());
    assertFalse(result.getSummaryActivities().isEmpty());
    assertEquals(LocalDate.parse(result.getDate(), DateTimeFormatter.ofPattern("yyyyMMdd")), expectDate);
    assertEquals(result.getTotalSteps(), 5768);
    assertEquals(result.getCalories(), 203);
  }
}