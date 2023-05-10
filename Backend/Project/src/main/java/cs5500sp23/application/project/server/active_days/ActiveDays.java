package cs5500sp23.application.project.server.active_days;

import cs5500sp23.application.project.model.DAO.ActiveDaysDAO;
import cs5500sp23.application.project.model.model.ActiveDaysEntry;
import cs5500sp23.application.project.server.common.RequestParamsParser;
import java.util.ArrayList;

public class ActiveDays {

  private ActiveDaysDAO activeDaysDAO;

  public ActiveDays() {
    this.activeDaysDAO = ActiveDaysDAO.getInstance();
  }

  public ActiveDaysEntry getAllActiveDays(String personId) {
    ActiveDaysEntry result = null;

    try {
      String id = RequestParamsParser.getPersonID(personId);
      result = activeDaysDAO.getAllActiveDays(id);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
    return result;
  }
}
