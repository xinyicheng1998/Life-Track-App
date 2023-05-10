package cs5500sp23.application.project.server.places;

import cs5500sp23.application.project.model.DAO.ActivitySummaryDAO;
import cs5500sp23.application.project.model.model.LongestVisitedPlaceEntry;
import cs5500sp23.application.project.model.model.MostVisitedPlaceEntry;
import cs5500sp23.application.project.server.common.RequestParamsParser;
import java.time.LocalDate;

public class Places {

  private ActivitySummaryDAO activitySummaryDAO;

  public Places() {
    this.activitySummaryDAO = ActivitySummaryDAO.getInstance();
  }

  /**
   * Get most steps in a range of time
   * @param personId - query personID
   * @param date - start date of a range
   * @param daysAfter - length of a range
   * @return most visited places entry
   * @throws IllegalArgumentException - if the params are malformed
   */
  public MostVisitedPlaceEntry getMostVisitedPlaces(String personId, String date, String daysAfter)
      throws IllegalArgumentException {
    MostVisitedPlaceEntry result = null;
    try {
      String id = RequestParamsParser.getPersonID(personId);
      Long daysAfterLong = RequestParamsParser.getDays(daysAfter);
      LocalDate startDate = RequestParamsParser.getLocalDate(date);
      result = activitySummaryDAO.getMostVisitedPlacesInRangeByPersonID(id, startDate, daysAfterLong);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
    return result;
  }

  public LongestVisitedPlaceEntry getLongestVisitedPlaces(String personId, String date, String daysAfter)
      throws IllegalArgumentException {
    LongestVisitedPlaceEntry result = null;
    try {
      String id = RequestParamsParser.getPersonID(personId);
      Long daysAfterLong = RequestParamsParser.getDays(daysAfter);
      LocalDate startDate = RequestParamsParser.getLocalDate(date);
      result = activitySummaryDAO.getLongestVisitedPlacesInRangeByPersonID(id, startDate, daysAfterLong);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
    return result;
  }
}
