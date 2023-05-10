package cs5500sp23.application.project.server.health_check;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import cs5500sp23.application.project.server.health_check.HealthCheckController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

@WebMvcTest(HealthCheckController.class)
public class HealthCheckControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testHealthCheck() throws Exception {
    mockMvc.perform(get("/health-check")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string("OK"));
  }
}