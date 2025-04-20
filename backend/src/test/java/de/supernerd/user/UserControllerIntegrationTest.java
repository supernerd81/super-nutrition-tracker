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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
        AppUserUpdate existingUser = new AppUserUpdate( "5545", "196103614","Manuel", "Simon", LocalDate.of(1981, 8, 11), 95, 183, Gender.MALE);
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
                      "id": "5545",
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
    void getUserById_userDoesNotExist() throws Exception {
        //GIVEN
        AppUserUpdate existingUser = new AppUserUpdate("196103614", "22", "Manuel", "Simon", LocalDate.of(1981, 8, 11), 95, 183, Gender.MALE);
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
    void updateUser_userExists() throws Exception {
        //GIVEN
        AppUserUpdate existingUser = new AppUserUpdate("196103614", "196103614", "Manuel", "Simon", LocalDate.of(1981, 8, 11), 95, 183, Gender.MALE);
        userRepository.save(existingUser);

        //WHEN
        String saveResult = mockMvc.perform(put("/api/user/update/196103614")
                        .with(oidcLogin().userInfoToken(token -> token
                                .claim("login", "testUser")
                                .claim("avatar_url", "testAvatarUrl")
                        ))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                          {
                              "id": "196103614",
                              "userId": "196103614",
                              "firstname": "Manuel",
                              "lastname": "Simon",
                              "birthday": "1981-08-11",
                              "age": 22,
                              "weight": 95,
                              "height": 183,
                              "gender": "MALE",
                              "metabolicRate": 2200
                            }
                          """))
                .andReturn()
                .getResponse()
                .getContentAsString();

        //THEN
        ResponseUserWithAgeDto actualUser = objectMapper.readValue(saveResult, ResponseUserWithAgeDto.class);
        assertThat(actualUser.id())
                .isNotBlank();

    }
}