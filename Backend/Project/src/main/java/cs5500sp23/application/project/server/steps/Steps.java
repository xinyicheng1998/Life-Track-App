package cs5500sp23.application.project.server.steps;

import cs5500sp23.application.project.model.DAO.ActivitySummaryDAO;
import cs5500sp23.application.project.model.model.AllStepEntry;
import cs5500sp23.application.project.model.model.MostStepEntry;
import cs5500sp23.application.project.server.common.RequestParamsParser;
import java.time.LocalDate;
public class Steps {

  private ActivitySummaryDAO activitySummaryDAO;

  public Steps() {
    this.activitySummaryDAO = ActivitySummaryDAO.getInstance();
  }

  /**
   * Get most steps in a range of time
   * @param personId - query personID
   * @param date - start date of a range
   * @param daysAfter - length of a range
   * @return most steps entry
   * @throws IllegalArgumentException - if the params are malformed
   */
  public MostStepEntry getMostSteps(String personId, String date, String daysAfter)
      throws IllegalArgumentException {
    MostStepEntry result = null;
    try {
      String id = RequestParamsParser.getPersonID(personId);
      Long daysAfterLong = RequestParamsParser.getDays(daysAfter);
      LocalDate startDate = RequestParamsParser.getLocalDate(date);
      result = activitySummaryDAO.getMostStepsInRangeByPersonID(id, startDate, daysAfterLong);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
    return result;
  }

  public AllStepEntry getAllSteps(String personId, String date, String daysAfter)
      throws IllegalArgumentException {
    AllStepEntry result = null;
    try {
      String id = RequestParamsParser.getPersonID(personId);
      Long daysAfterLong = RequestParamsParser.getDays(daysAfter);
      LocalDate startDate = RequestParamsParser.getLocalDate(date);
      result = activitySummaryDAO.getAllStepsInRangeByPersonID(id, startDate, daysAfterLong);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
    return result;
  }
}
