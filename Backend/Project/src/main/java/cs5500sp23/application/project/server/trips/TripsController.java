package cs5500sp23.application.project.server.trips;

import cs5500sp23.application.project.model.model.LongestTripEntry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is a Java Spring Boot RestController class named TripsController that handles HTTP GET
 * requests for /longest-trips endpoint.
 * <p>
 * The getLongestTrips() method accepts three request parameters: personId, date, and daysAfter. It
 * returns a ResponseEntity object that contains an HTTP status code and a response body.
 * <p>
 * The Trips class is used to calculate the longest trip for a given person based on the input
 * parameters. If an invalid input is detected, the method returns a bad request response with an
 * error message. Otherwise, it returns an ok response with the calculated result in JSON format.
 */
@RestController
public class TripsController {

    @GetMapping(path = "/longest-trips", produces = "application/json")
    public ResponseEntity<?> getLongestTrips(
        @RequestParam("personId") String personId,
        @RequestParam("date") String date,
        @RequestParam("daysAfter") String daysAfter) {
        Trips trips = new Trips();
        LongestTripEntry result;
        try {
            result = trips.getLongestTrips(personId, date, daysAfter);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(result);
    }
}
