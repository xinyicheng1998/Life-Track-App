package cs5500sp23.application.project.server.health_check;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

  @GetMapping(path = "/health-check", produces = "application/json")
  public ResponseEntity<?> healthCheck() {
    return ResponseEntity.ok("OK");
  }
}
