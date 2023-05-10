package cs5500sp23.application.project.server.trips;

import cs5500sp23.application.project.model.DAO.ActivitySummaryDAO;
import cs5500sp23.application.project.model.model.LongestTripEntry;
import cs5500sp23.application.project.server.common.RequestParamsParser;
import java.time.LocalDate;

/**
 * class named Trips that is used to retrieve the longest trips taken by a person within a certain
 * time range.
 */
public class Trips {

    private ActivitySummaryDAO activitySummaryDAO;

    /**
     * constructor that initializes an instance variable of type ActivitySummaryDAO.
     */
    public Trips() {
        this.activitySummaryDAO = ActivitySummaryDAO.getInstance();
    }

    /**
     * public method getLongestTrips that takes in three parameters - personId, date, and daysAfter
     * - and returns an object of type LongestTripEntry. The getLongestTrips method first validates
     * the input parameters using a RequestParamsParser object. If the input parameters are valid,
     * it calls a method named getLongestTripsInRangeByPersonID on the activitySummaryDAO object to
     * retrieve the longest trips taken by the person within the specified time range. If any
     * exception is thrown during the input parameter validation or while retrieving data from the
     * DAO object, it rethrows the exception as an IllegalArgumentException.
     *
     * @param personId  - the id of the person
     * @param date      - the date starts
     * @param daysAfter - the range of the date
     * @return the longest trips taken by the person within the specified time range
     * @throws IllegalArgumentException is thrown during the input parameter validation or while
     *                                  retrieving data from the * DAO object
     */
    public LongestTripEntry getLongestTrips(String personId, String date, String daysAfter)
        throws IllegalArgumentException {
        LongestTripEntry result = null;
        try {
            String id = RequestParamsParser.getPersonID(personId);
            Long daysAfterLong = RequestParamsParser.getDays(daysAfter);
            LocalDate startDate = RequestParamsParser.getLocalDate(date);
            result = activitySummaryDAO.getLongestTripsInRangeByPersonID(id, startDate,
                daysAfterLong);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return result;
    }

}
