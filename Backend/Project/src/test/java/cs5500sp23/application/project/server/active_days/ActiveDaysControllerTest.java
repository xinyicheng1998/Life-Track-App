package cs5500sp23.application.project.server.active_days;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(ActiveDaysController.class)
public class ActiveDaysControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testGetActiveDays() throws Exception {
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/active-days")
            .param("personId", "1"))
        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
  }

  @Test
  public void testGetActiveDaysIllegal() throws Exception {
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/active-days")
            .param("personId", "abc"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
  }
}
