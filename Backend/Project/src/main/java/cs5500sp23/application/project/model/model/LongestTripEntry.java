package cs5500sp23.application.project.model.model;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Java class LongestTripEntry with three private fields: trips, startTimeOfLongestTrip, and
 * longestTrip.
 */
public class LongestTripEntry {

    private Map<LocalDateTime, Long> trips;
    private LocalDateTime startTimeOfLongestTrip;
    private Long longestTrip;

    /**
     * The constructor of the class takes in three parameters to initialize these fields, and the
     * class also provides getter methods for each of these fields.
     *
     * @param trips                  - The trips field is a Map with keys of type LocalDateTime and
     *                               values of type Long, representing a mapping of trip start times
     *                               to their durations.
     * @param startTimeOfLongestTrip - The startTimeOfLongestTrip field is a LocalDateTime
     *                               representing the start time of the longest trip in the trips
     *                               map
     * @param longestTrip            - The longestTrip field is a Long representing the duration of
     *                               the longest trip in the trips map.
     */
    public LongestTripEntry(Map<LocalDateTime, Long> trips, LocalDateTime startTimeOfLongestTrip,
        Long longestTrip) {
        this.trips = trips;
        this.startTimeOfLongestTrip = startTimeOfLongestTrip;
        this.longestTrip = longestTrip;
    }

    public Map<LocalDateTime, Long> getTrips() {
        return trips;
    }

    public LocalDateTime getStartTimeOfLongestTrip() {
        return startTimeOfLongestTrip;
    }

    public Long getLongestTrip() {
        return longestTrip;
    }
}
