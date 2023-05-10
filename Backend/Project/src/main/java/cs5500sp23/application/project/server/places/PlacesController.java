package cs5500sp23.application.project.server.places;

import cs5500sp23.application.project.model.model.LongestVisitedPlaceEntry;
import cs5500sp23.application.project.model.model.MostVisitedPlaceEntry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlacesController {
  @GetMapping(path = "/most-visited-places", produces = "application/json")
  public ResponseEntity<?> getMostVisitedPlaces(
      @RequestParam("personId") String personId,
      @RequestParam("date") String date,
      @RequestParam("daysAfter") String daysAfter) {
    Places places = new Places();
    MostVisitedPlaceEntry result;
    try {
      result = places.getMostVisitedPlaces(personId, date, daysAfter);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
    return ResponseEntity.ok(result);
  }

  @GetMapping(path = "/longest-visited-places", produces = "application/json")
  public ResponseEntity<?> getLongestVisitedPlaces(
      @RequestParam("personId") String personId,
      @RequestParam("date") String date,
      @RequestParam("daysAfter") String daysAfter) {
    Places places = new Places();
    LongestVisitedPlaceEntry result;
    try {
      result = places.getLongestVisitedPlaces(personId, date, daysAfter);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
    return ResponseEntity.ok(result);
  }
}
