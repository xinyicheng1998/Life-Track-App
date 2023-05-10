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
public class UpdateUserControllerTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testUpdateUserInvalidParam() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/update-user")
                .param("email", "alice@gmail.com"))
            .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();
    }

}
