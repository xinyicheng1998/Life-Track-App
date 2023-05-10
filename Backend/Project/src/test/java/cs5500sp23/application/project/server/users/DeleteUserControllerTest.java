package cs5500sp23.application.project.server.users;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cs5500sp23.application.project.server.user.UserController;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(UserController.class)
public class DeleteUserControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testDeleteUser() throws Exception {
        MvcResult prep = mockMvc.perform(MockMvcRequestBuilders.get("/create-user")
                .param("firstName", "Jane")
                .param("lastName", "Ma")
                .param("email", "alice@gmail.com"))
            .andExpect(status().isOk()).andReturn();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/update-user")
                .param("firstName", "Alice")
                .param("lastName", "Bonnet")
                .param("email", "alice@gmail.com"))
            .andExpect(status().isOk()).andReturn();
        String expectedResponse = "{\"success\":true}";
        JSONAssert.assertEquals(expectedResponse, result.getResponse().getContentAsString(), false);
        MvcResult delResult = mockMvc.perform(MockMvcRequestBuilders.delete("/delete-user")
                .param("email", "alice@gmail.com"))
            .andExpect(status().isOk()).andReturn();
        String delExpectedResponse = "{\"success\":true}";
        JSONAssert.assertEquals(delExpectedResponse, delResult.getResponse().getContentAsString(), false);
    }

    @Test
    public void testDeleteUserIllegal() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/delete-user")
            )
            .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
    }
}
