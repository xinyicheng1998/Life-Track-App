package cs5500sp23.application.project.server.trips;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(TripsController.class)
public class TripsControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testGetLongestTripsInRangeByPersonID() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/longest-trips")
                .param("personId", "1")
                .param("date", "20160820")
                .param("daysAfter", "0"))
            .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String expectedResponse = "{\"trips\":{\"2016-08-20T08:10:46\":347,\"2016-08-20T07:58:06\":468,\"2016-08-20T16:50:05\":285,\"2016-08-20T14:30:49\":946,\"2016-08-20T17:00:49\":340,\"2016-08-20T07:07:17\":2052},\"startTimeOfLongestTrip\":\"2016-08-20T07:07:17\",\"longestTrip\":2052}";
        JSONAssert.assertEquals(expectedResponse, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void testGetLongestTripsInRangeByPersonIDInvalidParam() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/longest-trips")
                .param("personId", "abc")
                .param("date", "20160820")
                .param("daysAfter", "1"))
            .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
    }
}
