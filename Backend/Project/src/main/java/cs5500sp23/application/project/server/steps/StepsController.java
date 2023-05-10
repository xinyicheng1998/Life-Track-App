package cs5500sp23.application.project.server.steps;

import cs5500sp23.application.project.model.model.AllStepEntry;
import cs5500sp23.application.project.model.model.MostStepEntry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StepsController {
  @GetMapping(path = "/most-steps", produces = "application/json")
  public ResponseEntity<?> getMostSteps(
      @RequestParam("personId") String personId,
      @RequestParam("date") String date,
      @RequestParam("daysAfter") String daysAfter) {
    Steps steps = new Steps();
    MostStepEntry result;
    try {
      result = steps.getMostSteps(personId, date, daysAfter);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
    return ResponseEntity.ok(result);
  }

  @GetMapping(path = "/all-steps", produces = "application/json")
  public ResponseEntity<?> getAllSteps(
      @RequestParam("personId") String personId,
      @RequestParam("date") String date,
      @RequestParam("daysAfter") String daysAfter) {
    Steps steps = new Steps();
    AllStepEntry result;
    try {
      result = steps.getAllSteps(personId, date, daysAfter);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
    return ResponseEntity.ok(result);
  }
}
