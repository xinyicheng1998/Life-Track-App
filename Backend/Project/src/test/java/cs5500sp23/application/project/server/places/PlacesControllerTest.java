package cs5500sp23.application.project.server.places;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(PlacesController.class)
public class PlacesControllerTest {
  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testGetMostVisitedPlaces() throws Exception {
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/most-visited-places")
            .param("personId", "1")
            .param("date", "20160820")
            .param("daysAfter", "2"))
        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    String expectedResponse = "{\"visitedPlaces\":{\"Road Runner Sports\":1,\"Apptio\":1,\"Green Lake United Methodist Church\":1,\"My Home\":10},\"mostVisitedPlace\":\"My Home\"}";
    JSONAssert.assertEquals(expectedResponse, result.getResponse().getContentAsString(), false);
  }

  @Test
  public void testGetMostVisitedPlacesInvalidParam() throws Exception {
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/most-visited-places")
            .param("personId", "1")
            .param("date", "20160820")
            .param("daysAfter", "-1"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
  }
  @Test
  public void testGetLongestVisitedPlaces() throws Exception {
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/longest-visited-places")
            .param("personId", "1")
            .param("date", "20160820")
            .param("daysAfter", "1"))
        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    System.out.println(result.getResponse().getContentAsString());
    String expectedResponse = "{\"visitedPlaces\":{\"Road Runner Sports\":17,\"My Home\":4443},\"longestVisitedPlace\":\"My Home\"}";
    JSONAssert.assertEquals(expectedResponse, result.getResponse().getContentAsString(), false);
  }

  @Test
  public void testGetLongestVisitedPlacesInvalidParam() throws Exception {
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/longest-visited-places")
            .param("personId", "abc")
            .param("date", "20160820")
            .param("daysAfter", "1"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
  }

}
