package cs5500sp23.application.project.server.active_days;

import cs5500sp23.application.project.model.model.ActiveDaysEntry;
import java.util.ArrayList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActiveDaysController {

  @GetMapping(path = "/active-days", produces = "application/json")
  public ResponseEntity<?> getAllActiveDays(
      @RequestParam("personId") String personId) {
    ActiveDaysEntry allDays = null;
    ActiveDays activeDays = new ActiveDays();
    try {
      allDays = activeDays.getAllActiveDays(personId);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
    return ResponseEntity.ok(allDays);
  }
}
