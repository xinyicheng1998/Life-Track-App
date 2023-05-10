package cs5500sp23.application.project.server.common;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


/**
 * Common parser for all endpoints
 */
public class RequestParamsParser {

  /**
   * Validate string input id.
   * Allow leading 0s in the input. for example:
   * 00009 is allowed and will be parsed as 9
   * @param personID - id
   * @return - parsed personID
   * @throws IllegalArgumentException if id is malformed
   */
  public static String getPersonID(String personID) throws IllegalArgumentException {
    Integer id = null;
    if (personID.matches("[0-9]*[1-9][0-9]*")) {
      id = Integer.valueOf(personID);
    } else {
      throw new IllegalArgumentException("Person ID should be an integer.");
    }
    return id.toString();
  }

  /**
   * Parse and valid the daysAfter parameters
   * @param daysAfter - days in request parameters
   * @return a valid long days
   * @throws IllegalArgumentException if the param is malformed
   */
  public static Long getDays(String daysAfter) throws IllegalArgumentException {
    Long daysAfterLong = null;
    try {
      daysAfterLong = Long.parseLong(daysAfter);
    } catch (Exception e) {
      throw new IllegalArgumentException("Parameter daysAfter malformed.");
    }
    if (daysAfterLong < 0) {
      throw new IllegalArgumentException("Parameter daysAfter must be a positive integer.");
    }
    return daysAfterLong;
  }

  /**
   * Parse and valid the date parameters
   * @param date - date in request parameters
   * @return - a valid localDate format date
   * @throws IllegalArgumentException - if date is malformed
   */
  public static LocalDate getLocalDate(String date) throws IllegalArgumentException {
    LocalDate startDate = null;
    try {
      startDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd"));
    } catch (DateTimeParseException e) {
      throw new IllegalArgumentException("Parameter date should be formatted with yyyyMMdd. e.g. 20230101.");
    }
    return startDate;
  }

}
