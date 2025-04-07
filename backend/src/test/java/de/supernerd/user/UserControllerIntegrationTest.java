package de.supernerd.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;


    @Test
    @DirtiesContext
    void getUserById_NerdUserExists() throws Exception {

        //GIVEN
        AppUserDetails existingUser = new AppUserDetails("12345", "196103614","Manuel", "Simon", LocalDate.of(1981, 8, 11), 95, 183);
        userRepository.save(existingUser);

        //WHEN
        mockMvc.perform(get("/api/user/196103614")
                .with(oidcLogin().userInfoToken(token -> token
                        .claim("login", "testUser")
                        .claim("avatar_url", "testAvatarUrl")
                )))

                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                    {
                      "id": "12345",
                      "userid": "196103614",
                      "firstname": "Manuel",
                      "lastname": "Simon",
                      "birthday": "1981-08-11",
                      "weight": 95,
                      "height": 183
                    }
                """));
    }

    @Test
    @DirtiesContext
    void getUserByid_userDoesNotExist() throws Exception {
        //GIVEN
        AppUserDetails existingUser = new AppUserDetails("12345", "196103614", "Manuel", "Simon", LocalDate.of(1981, 8, 11), 95, 183);
        userRepository.save(existingUser);

        //WHEN
        mockMvc.perform(get("/api/user/55")
                        .with(oidcLogin().userInfoToken(token -> token
                                .claim("login", "testUser")
                                .claim("avatar_url", "testAvatarUrl")
                        )))


                //THEN
                .andExpect(status().isNotFound());

    }

    @Test
    @DirtiesContext
    void newUser_newUserCreated() throws Exception {
        //GIVEN

        //WHEN
        mockMvc.perform(get("/api/user/12345")
                        .with(oidcLogin().userInfoToken(token -> token
                                .claim("login", "testUser")
                                .claim("avatar_url", "testAvatarUrl")
                        )))
                        .andExpect(status().isNotFound());

        String saveResult = mockMvc.perform(post("/api/user/new")
                        .with(oidcLogin().userInfoToken(token -> token
                                .claim("login", "testUser")
                                .claim("avatar_url", "testAvatarUrl")
                        ))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                          {
                              "firstname": "Manuel",
                              "lastname": "Simon",
                              "birthday": "1981-08-11",
                              "weight": 95,
                              "height": 183
                            }
                          """))
                .andReturn()
                .getResponse()
                .getContentAsString();

        //THEN
        AppUserDetails actualUser = objectMapper.readValue(saveResult, AppUserDetails.class);
        assertThat(actualUser.id())
                .isNotBlank();

    }
}