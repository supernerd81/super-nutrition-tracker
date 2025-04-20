package de.supernerd.meal;

import de.supernerd.auth.AuthAppUser;
import de.supernerd.meal.models.DailyMeal;
import de.supernerd.meal.models.Meals;
import de.supernerd.meal.repository.DailyMealRepository;
import de.supernerd.meal.repository.MealsRepository;
import de.supernerd.user.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MealControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private DailyMealRepository dailyMealRepository;

    @Autowired
    private MealsRepository mealsRepository;
    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;


    @BeforeEach
    void setupSecurityContext() {
        AuthAppUser user = new AuthAppUser();
        user.setId("55555");
        user.setUsername("testUser");
        user.setBirthday(LocalDate.of(1995, 1, 1));
        user.setHeight(175);
        user.setWeight(70);
        user.setGender(Gender.MALE);

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(user, null, List.of());

        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    @DirtiesContext
    void getDailyMealById_notExistingMeals() throws Exception {
        //GIVEN
        DailyMeal existingDailyMeal = new DailyMeal("12345", "55555", "5564", LocalDateTime.of(2022, 1, 1, 12, 0), 12, 30, 68);
        dailyMealRepository.save(existingDailyMeal);
        Meals existingMmeals = new Meals("5564", "55555", "Test Meal", 12, 30, 68);
        mealsRepository.save(existingMmeals);

        //WHEN
        mockMvc.perform(get("/api/meal/4586")
                        .with(oidcLogin().userInfoToken(token -> token
                                .claim("login", "testUser")
                                .claim("avatar_url", "testAvatarUrl")
                        )))

                //THEN
                .andExpect(status().isNotFound());
    }

    @Test
    @DirtiesContext
    void newMeal() throws Exception {
        //GIVEN

        //WHEN
        mockMvc.perform(post("/api/meal/new")
                        .with(oidcLogin().userInfoToken(token -> token
                                .claim("login", "testUser")
                                .claim("avatar_url", "testAvatarUrl")
                        ))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                           { 
                                 "userId": "55555",
                                 "dateTime": "2022-01-01T12:00:00",
                                 "barcode": "556441177786fsag556",
                                 "mealName": "Test Meal",
                                 "protein": 12,
                                 "carbohydrates": 30,
                                 "fat": 68
                            }
                        """))

                //THEN
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                    {
                        "userId": "55555",
                        "meals": {
                                "barcode": "556441177786fsag556",
                                "name": "Test Meal",
                                "protein": 12,
                                "carbohydrates": 30,
                                "fat": 68
                            },
                        "datetime": "2022-01-01T12:00:00"
                    }
          """))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.meals.id").isNotEmpty());
    }

    @Test
    @DirtiesContext
    void mealByBarcode() throws Exception {
        //GIVEN
        Meals existingMeals = new Meals("5564", "556447893221", "Test Meal", 12, 30, 68);
        mealsRepository.save(existingMeals);

        //WHEN
        mockMvc.perform(get("/api/meal/barcode/556447893221")
                        .with(oidcLogin().userInfoToken(token -> token
                                .claim("login", "testUser")
                                .claim("avatar_url", "testAvatarUrl")
                        )))

                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                          {
                            "barcode": "556447893221",
                            "mealName": "Test Meal",
                            "protein": 12,
                            "carbohydrates": 30,
                            "fat": 68
                          }
                """));
    }

    @Test
    @DirtiesContext
    void mealByBarcode_notExists() throws Exception {
        //GIVEN
        Meals existingMeals = new Meals("5564", "556447893221", "Test Meal", 12, 30, 68);
        mealsRepository.save(existingMeals);

        //WHEN
        mockMvc.perform(get("/api/meal/barcode/5564473221")
                        .with(oidcLogin().userInfoToken(token -> token
                                .claim("login", "testUser")
                                .claim("avatar_url", "testAvatarUrl")
                        )))

                //THEN
                .andExpect(status().isNotFound());
    }



    @Test
    @DirtiesContext
    void mealsToday_foundMeals() throws Exception {
        //GIVEN
        DailyMeal existingDailyMeal = new DailyMeal("123456", "196103614", "5564", LocalDateTime.now().withHour(12), 30, 68, 12);
        dailyMealRepository.save(existingDailyMeal);

        //WHEN
        mockMvc.perform(get("/api/meal/today/196103614"))

                //THEN
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.fatCurrent").value(12))
                .andExpect(jsonPath("$.proteinCurrent").value(30))
                .andExpect(jsonPath("$.carbohydratesCurrent").value(68))
                .andExpect(jsonPath("$.kcalToday").value((12 * 9) + (30 * 4) + (68 * 4)))
                .andExpect(jsonPath("$.fatMax").isNumber())
                .andExpect(jsonPath("$.fatPercent").isNumber())
                .andExpect(jsonPath("$.proteinMax").isNumber())
                .andExpect(jsonPath("$.proteinPercent").isNumber())
                .andExpect(jsonPath("$.carbohydratesMax").isNumber())
                .andExpect(jsonPath("$.carbohydratesPercent").isNumber());
    }

    @Test
    @DirtiesContext
    void mealsToday_notFoundMeals() throws Exception {
        //GIVEN
        DailyMeal existingDailyMeal = new DailyMeal("123456", "55555", "5564", LocalDateTime.now().withHour(12), 30, 68, 12);
        dailyMealRepository.save(existingDailyMeal);

        //WHEN
        mockMvc.perform(get("/api/meal/today/55555554")
                        .with(oidcLogin().userInfoToken(token -> token
                                .claim("login", "testUser")
                                .claim("avatar_url", "testAvatarUrl")
                        )))

                //THEN
                .andExpect(status().isNotFound());
    }

    @Test
    @DirtiesContext
    void updateMeal_updateSuccessful() throws Exception {
        //GIVEN
        DailyMeal existingDailyMeal = new DailyMeal("123456", "55555", "5564", LocalDateTime.now().withHour(12), 30, 68, 12);
        dailyMealRepository.save(existingDailyMeal);
        Meals existingMmeals = new Meals("5564", "55555", "Test Meal", 12, 30, 68);
        mealsRepository.save(existingMmeals);


        //WHEN
        mockMvc.perform(put("/api/meal/update/123456")
                .with(oidcLogin().userInfoToken(token -> token
                        .claim("login", "testUser")
                        .claim("avatar_url", "testAvatarUrl")
                ))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                           { 
                                 "id": "123456",
                                 "userId": "55555",
                                 "mealsId": "5564",
                                 "dateTime": "2024-01-01T12:00:00",          
                                 "protein": 20,
                                 "carbohydrates": 31,              
                                 "fat": 50                       
                            }
                        """))


        //THEN
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.content().json("""
                                    {
                                         "id": "123456",
                                         "userId": "55555",
                                         "mealsId": "5564",
                                         "dateTime": "2024-01-01T12:00:00",
                                         "name": "Test Meal",
                                         "fat": 50,
                                         "carbohydrates": 31,
                                         "protein": 20
                                    }
                        """));
    }

    @Test
    @DirtiesContext
    void updateMeal_badDailyMealId_inPathVariable() throws Exception {
        //GIVEN
        DailyMeal existingDailyMeal = new DailyMeal("123456", "55555", "5564", LocalDateTime.now().withHour(12), 30, 68, 12);
        dailyMealRepository.save(existingDailyMeal);
        Meals existingMmeals = new Meals("5564", "55555", "Test Meal", 12, 30, 68);
        mealsRepository.save(existingMmeals);


        //WHEN
        mockMvc.perform(put("/api/meal/update/1235463456")
                        .with(oidcLogin().userInfoToken(token -> token
                                .claim("login", "testUser")
                                .claim("avatar_url", "testAvatarUrl")
                        ))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                           { 
                                 "id": "123456",
                                 "userId": "55555",
                                 "mealsId": "5564",
                                 "dateTime": "2024-01-01T12:00:00",          
                                 "protein": 20,
                                 "carbohydrates": 31,              
                                 "fat": 50                       
                            }
                        """))


                //THEN
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DirtiesContext
    void updateMeal_mealsIdNotFound() throws Exception {
        //GIVEN
        DailyMeal existingDailyMeal = new DailyMeal("123456", "55555", "5564", LocalDateTime.now().withHour(12), 30, 68, 12);
        dailyMealRepository.save(existingDailyMeal);
        Meals existingMmeals = new Meals("5564", "55555", "Test Meal", 12, 30, 68);
        mealsRepository.save(existingMmeals);


        //WHEN
        mockMvc.perform(put("/api/meal/update/123456")
                        .with(oidcLogin().userInfoToken(token -> token
                                .claim("login", "testUser")
                                .claim("avatar_url", "testAvatarUrl")
                        ))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                           { 
                                 "id": "123456",
                                 "userId": "55555",
                                 "mealsId": "5654564",
                                 "dateTime": "2024-01-01T12:00:00",          
                                 "protein": 20,
                                 "carbohydrates": 31,              
                                 "fat": 50                       
                            }
                        """))


                //THEN
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DirtiesContext
    void deleteMeal_isDeleted() throws Exception {
        //GIVEN
        DailyMeal existingDailyMeal = new DailyMeal("123456", "55555", "5564", LocalDateTime.now().withHour(12), 30, 68, 12);
        dailyMealRepository.save(existingDailyMeal);

        //WHEN
        mockMvc.perform(delete("/api/meal/delete/123456"))

        //THEN
                .andExpect(status().isOk());

    }

    @Test
    @DirtiesContext
    void deleteMeal_idNotFound() throws Exception {
        //GIVEN
        DailyMeal existingDailyMeal = new DailyMeal("123456", "55555", "5564", LocalDateTime.now().withHour(12), 30, 68, 12);
        dailyMealRepository.save(existingDailyMeal);

        //WHEN
        mockMvc.perform(delete("/api/meal/delete/123455556"))

                //THEN
                .andExpect(status().isNotFound());

    }

    @Test
    @DirtiesContext
    void getDailyMealsOverview_twoMealsAvailable() throws Exception{
        //GIVEN
        DailyMeal dailyMeal1 = new DailyMeal("123456", "55555", "5564", LocalDateTime.now().withHour(12), 30, 68, 12);
        DailyMeal dailyMeal2 = new DailyMeal("123457", "55555", "5565", LocalDateTime.now().withHour(12), 50, 40, 55);
        dailyMealRepository.saveAll(List.of(dailyMeal1, dailyMeal2));

        Meals meals1 = new Meals("5564", "55555", "Test Meal 1", 12, 30, 68);
        Meals meals2 = new Meals("5565", "55555", "Test Meal 2", 50, 40, 55);
        mealsRepository.saveAll(List.of(meals1, meals2));

        //WHEN
        mockMvc.perform(get("/api/meal/overview/today"))

        //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [{
                            "id": "123456",
                            "userId": "55555",
                            "mealsId": "5564",
                            "name": "Test Meal 1",
                            "fat": 12,
                            "carbohydrates": 68,
                            "protein": 30
                        },
                        {
                            "id": "123457",
                            "userId": "55555",
                            "mealsId": "5565",
                            "name": "Test Meal 2",
                            "fat": 55,
                            "carbohydrates": 40,
                            "protein": 50
                        }]
                """))
                .andExpect(jsonPath("$[0].dateTime").isNotEmpty())
                .andExpect(jsonPath("$[1].dateTime").isNotEmpty());
    }

    @Test
    @DirtiesContext
    void getDailyMealsOverview_oneMealsAvailable_forThisUser() throws Exception{
        //GIVEN
        DailyMeal dailyMeal1 = new DailyMeal("123456", "55555", "5564", LocalDateTime.now().withHour(12), 30, 68, 12);
        DailyMeal dailyMeal2 = new DailyMeal("123457", "55553", "5565", LocalDateTime.now().withHour(12), 50, 40, 55);
        dailyMealRepository.saveAll(List.of(dailyMeal1, dailyMeal2));

        Meals meals1 = new Meals("5564", "55555", "Test Meal 1", 12, 30, 68);
        Meals meals2 = new Meals("5565", "55555", "Test Meal 2", 50, 40, 55);
        mealsRepository.saveAll(List.of(meals1, meals2));

        //WHEN
        mockMvc.perform(get("/api/meal/overview/today"))

                //THEN
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [{
                            "id": "123456",
                            "userId": "55555",
                            "mealsId": "5564",
                            "name": "Test Meal 1",
                            "fat": 12,
                            "carbohydrates": 68,
                            "protein": 30
                        }]
                """))
                .andExpect(jsonPath("$[0].dateTime").isNotEmpty());
    }

    @Test
    @DirtiesContext
    void getDailyMealsOverview_noneMealsAvailable_forThisUser() throws Exception{
        //GIVEN
        DailyMeal dailyMeal1 = new DailyMeal("123456", "55565", "5564", LocalDateTime.now().withHour(12), 30, 68, 12);
        DailyMeal dailyMeal2 = new DailyMeal("123457", "55553", "5565", LocalDateTime.now().withHour(12), 50, 40, 55);
        dailyMealRepository.saveAll(List.of(dailyMeal1, dailyMeal2));

        Meals meals1 = new Meals("5564", "5555566", "Test Meal 1", 12, 30, 68);
        Meals meals2 = new Meals("5565", "55555445", "Test Meal 2", 50, 40, 55);
        mealsRepository.saveAll(List.of(meals1, meals2));

        //WHEN
        mockMvc.perform(get("/api/meal/overview/today"))

                //THEN
                .andExpect(status().isNotFound());
    }

    @Test
    @DirtiesContext
    void getDailyMealsOverview_noValidDate() throws Exception{
        //GIVEN
        DailyMeal dailyMeal1 = new DailyMeal("123456", "55555", "5564", null, 30, 68, 12);
        DailyMeal dailyMeal2 = new DailyMeal("123457", "55553", "5565", LocalDateTime.now().withHour(12), 50, 40, 55);
        dailyMealRepository.saveAll(List.of(dailyMeal1, dailyMeal2));

        Meals meals1 = new Meals("5564", "5555566", "Test Meal 1", 12, 30, 68);
        Meals meals2 = new Meals("5565", "55555445", "Test Meal 2", 50, 40, 55);
        mealsRepository.saveAll(List.of(meals1, meals2));

        //WHEN
        mockMvc.perform(get("/api/meal/overview/today"))

                //THEN
                .andExpect(status().isBadRequest());
    }
}