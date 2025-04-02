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
        User existingUser = new User("12345", "Manuel", "Simon", LocalDate.of(1981, 8, 11), 95, 183);
        userRepository.save(existingUser);

        //WHEN
        mockMvc.perform(get("/api/user/12345"))

                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                    {
                      "id": "12345",
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
        User existingUser = new User("12345", "Manuel", "Simon", LocalDate.of(1981, 8, 11), 95, 183);
        userRepository.save(existingUser);

        //WHEN
        mockMvc.perform(get("/api/user/55"))


                //THEN
                .andExpect(status().isNotFound());

    }

    @Test
    @DirtiesContext
    void newUser_newUserCreated() throws Exception {
        //GIVEN

        //WHEN
        mockMvc.perform(get("/api/user/12345"))
                        .andExpect(status().isNotFound());

        String saveResult = mockMvc.perform(post("/api/user/new")
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
        User actualUser = objectMapper.readValue(saveResult, User.class);
        assertThat(actualUser.id())
                .isNotBlank();

    }
}