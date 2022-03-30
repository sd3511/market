package ru.sd351.market.auth;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import ru.sd351.market.auth.services.UserService;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    private String badRequest = "{\n" +
            "    \"username\": \"user\",\n" +
            "    \"password\": \"user\"\n" +
            "}";
    private String request = "{\n" +
            "    \"username\": \"user\",\n" +
            "    \"password\": \"100\"\n" +
            "}";

    @Test
    public void createAuthTokenTestFail() throws Exception {

        UserDetails userDetails = new User("user", "user", Collections.singleton(new SimpleGrantedAuthority("ADMIN")));

        Mockito.doReturn(userDetails).when(userService).loadUserByUsername("user");
        mockMvc.perform(
                post("/auth")
                        .content(badRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void createAuthTokenTestSuccess() throws Exception {

        UserDetails userDetails = new User("bob", "$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i", Collections.singleton(new SimpleGrantedAuthority("ADMIN")));

        Mockito.doReturn(userDetails).when(userService).loadUserByUsername("user");
        mockMvc.perform(
                post("/auth")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
