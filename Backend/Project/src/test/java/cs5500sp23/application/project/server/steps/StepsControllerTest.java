package cs5500sp23.application.project.server.steps;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cs5500sp23.application.project.server.health_check.HealthCheckController;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(StepsController.class)
public class StepsControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testGetMostSteps() throws Exception {
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/most-steps")
            .param("personId", "1")
            .param("date", "20130209")
            .param("daysAfter", "2"))
        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    String expectedResponse = "{\"date\":\"20130211\",\"totalSteps\":5768,\"calories\":203.0,\"summaryActivities\":[{\"activityType\":\"walking\",\"group\":\"walking\",\"duration\":3552.0,\"distance\":4057.0,\"steps\":5768,\"calories\":203.0}]}";
    JSONAssert.assertEquals(expectedResponse, result.getResponse().getContentAsString(), false);
  }

  @Test
  public void testGetMostStepsInvalidParam() throws Exception {
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/most-steps")
            .param("personId", "1")
            .param("date", "20130209")
            .param("daysAfter", "-1"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
  }
  @Test
  public void testGetAllSteps() throws Exception {
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/all-steps")
            .param("personId", "1")
            .param("date", "20160820")
            .param("daysAfter", "1"))
        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    String expectedResponse = "{\"result\":[{\"date\":\"20160820\",\"totalSteps\":8906,\"calories\":407.0,\"summaryActivities\":[{\"activityType\":\"walking\",\"group\":\"walking\",\"duration\":4433.0,\"distance\":5448.0,\"steps\":6669,\"calories\":269.0},{\"activityType\":\"running\",\"group\":\"running\",\"duration\":873.0,\"distance\":2303.0,\"steps\":2237,\"calories\":138.0}]},{\"date\":\"20160821\",\"totalSteps\":4421,\"calories\":153.0,\"summaryActivities\":[{\"activityType\":\"walking\",\"group\":\"walking\",\"duration\":3133.0,\"distance\":3090.0,\"steps\":4421,\"calories\":153.0}]}]}";
    JSONAssert.assertEquals(expectedResponse, result.getResponse().getContentAsString(), false);
  }

  @Test
  public void testGetAllStepsInvalidParam() throws Exception {
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/all-steps")
            .param("personId", "abc")
            .param("date", "20130209")
            .param("daysAfter", "1"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
  }
}