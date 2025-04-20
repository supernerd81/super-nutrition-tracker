package de.supernerd.meal;

import de.supernerd.auth.AuthAppUser;
import de.supernerd.auth.AuthAppUserRoles;
import de.supernerd.meal.models.DailyMeal;
import de.supernerd.meal.models.Meals;
import de.supernerd.meal.repository.DailyMealRepository;
import de.supernerd.meal.repository.MealsRepository;
import de.supernerd.meal.request_dto.DailyMealResponseDto;
import de.supernerd.meal.request_dto.DailyMealsTodayResponseDto;
import de.supernerd.user.Gender;
import de.supernerd.utils.MetabolismUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MealServiceTest {

    @Mock
    private MealsRepository mealsRepository;

    @Mock private DailyMealRepository dailyMealRepository;

    @InjectMocks
    private MealService mealService;

    @Test
    void saveDailyMeal() {
        //GIVEN
        DailyMeal dailyMeal = new DailyMeal(null, "55555", "5564", LocalDateTime.now(), 12, 30, 68);
        when(dailyMealRepository.save(dailyMeal)).thenReturn(dailyMeal);

        //WHEN
        DailyMeal result = mealService.saveDailyMeal(dailyMeal);

        //THEN
        assertEquals(dailyMeal, result);
        verify(dailyMealRepository).save(dailyMeal);

    }

    @Test
    void saveMeals_withBarcode() {
        //GIVEN
        Meals meals = new Meals("5564", "55555", "Test Meal", 12, 30, 68);
        when(mealsRepository.save(meals)).thenReturn(meals);

        //WHEN
        Meals result = mealService.saveMeals(meals);

        //THEN
        assertEquals(meals, result);
        verify(mealsRepository).save(meals);
    }

    @Test
    void saveMeals_withoutBarcode() {
        //GIVEN
        Meals meals = new Meals("5564", "", "Test Meal", 12, 30, 68);
        when(mealsRepository.save(meals)).thenReturn(meals);

        //WHEN
        Meals result = mealService.saveMeals(meals);

        //THEN
        assertEquals(meals, result);
        verify(mealsRepository).save(meals);
    }

    @Test
    void saveMeals_barcodeIsNull() {
        //GIVEN
        Meals meals = new Meals("5564", null, "Test Meal", 12, 30, 68);
        when(mealsRepository.save(meals)).thenReturn(meals);

        //WHEN
        Meals result = mealService.saveMeals(meals);

        //THEN
        assertEquals(meals, result);
        verify(mealsRepository).save(meals);
    }

    @Test
    void getAllMealsByUserId_returnDtoList() {
        //GIVEN
        String userId = "55555";
        String mealsId = "5564";
        LocalDateTime now = LocalDateTime.now();

        DailyMeal dailyMeal = new DailyMeal("meal1", userId, mealsId, now, 12, 30, 68);
        List<DailyMeal> dailyMeals = List.of(dailyMeal);

        Meals meals = new Meals(mealsId, userId, "Test Meal", 12, 30, 68);
        List<Meals> mealsList = List.of(meals);

        when(dailyMealRepository.findAllByUserId(userId)).thenReturn(dailyMeals);
        when(mealsRepository.findAllById(List.of(mealsId))).thenReturn(mealsList);

        //WHEN
        List<DailyMealResponseDto> result = mealService.getAllMealsByUserId(userId);


        //THEN
        assertEquals(1, result.size());

        DailyMealResponseDto dto = result.getFirst();
        assertEquals("meal1", dto.id());
        assertEquals(userId, dto.userId());
        assertEquals(mealsId, dto.mealsId());
        assertEquals("Test Meal", dto.meals().name());
        assertEquals(now, dto.datetime());

        verify(dailyMealRepository).findAllByUserId(userId);
        verify(mealsRepository).findAllById(List.of(mealsId));
    }

    @Test
    void getAllMealsByUserId_ThrowsNotFoundIfEmpty() {
        //GIVEN
        String userId = "55555";
        when(dailyMealRepository.findAllByUserId(userId)).thenReturn(List.of());

         //WHEN
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> mealService.getAllMealsByUserId(userId));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("no meals found with this userid", exception.getReason());

        verify(dailyMealRepository).findAllByUserId(userId);
        verifyNoMoreInteractions(mealsRepository);
    }

    @Test
    void getMealByBarcode_returnMeal() {
        //GIVEN
        String barcode = "5564";
        Meals meals = new Meals(barcode, "5564", "Test Meal", 12, 30, 68);

        when(mealsRepository.findByBarcode(barcode)).thenReturn(java.util.Optional.of(meals));

        //WHEN
        Meals result = mealService.getMealByBarcode(barcode);

        //THEN
        assertNotNull(result);
        assertEquals("Test Meal", result.name());
        assertEquals(barcode, result.barcode());

        verify(mealsRepository).findByBarcode(barcode);
    }

    @Test
    void getMealByBarcode_ThrowsNotFoundIfNotFound() {
        //GIVEN
        String barcode = "5564";
        Meals meal = new Meals(barcode, "5564", "Test Meal", 12, 30, 68);

        when(mealsRepository.findByBarcode(barcode)).thenReturn(Optional.of(meal));

        //WHEN
        Meals result = mealService.getMealByBarcode(barcode);

        //THEN
        assertNotNull(result);
        assertEquals("Test Meal", result.name());
        assertEquals(barcode, result.barcode());

        verify(mealsRepository).findByBarcode(barcode);
    }



    @Test
    void getMealsByUserId_todayValuesCorrect() {
        //GIVEN
        AuthAppUser mockUser = AuthAppUser.builder()
                .id("55555")
                .username("testUser")
                .role(AuthAppUserRoles.USER)
                .firstname("Test")
                .lastname("User")
                .birthday(LocalDate.of(1995, 1, 1))
                .weight(70)
                .height(180)
                .gender(Gender.MALE)
                .age(43)
                .metabolicRate(0)
                .simpleGrantedAuthorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                .attributes((Map.of()))
                .build();

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(mockUser);

        SecurityContextHolder.setContext(securityContext);

        //WHEN
        LocalDateTime now = LocalDateTime.now();

        DailyMeal meal1 = new DailyMeal("meal1", mockUser.getId(), "5564", now, 10, 20, 5);
        DailyMeal meal2 = new DailyMeal("meal2", mockUser.getId(), "5564", now, 15, 30, 7);

        when(dailyMealRepository.findAllByUserId(mockUser.getId())).thenReturn(List.of(meal1, meal2));

        try (MockedStatic<MetabolismUtils> mockedMetabolismUtils = mockStatic(MetabolismUtils.class)) {
            mockedMetabolismUtils.when(() -> MetabolismUtils.calculateBasalMetabolicRate(ArgumentMatchers.anyInt(), anyDouble(), anyDouble(), anyString()))
                    .thenReturn(2000.0);

            mockedMetabolismUtils.when(() -> MetabolismUtils.calculateMaxFatRate(2000.0)).thenReturn(80);
            mockedMetabolismUtils.when(() -> MetabolismUtils.calculateMaxProteinRate(2000.0)).thenReturn(100);
            mockedMetabolismUtils.when(() -> MetabolismUtils.calculateMaxCarbohydratesRate(2000.0)).thenReturn(300);

            DailyMealsTodayResponseDto result = mealService.getMealsByUserId(mockUser.getId());

            //THEN
            assertEquals(12, result.fatCurrent());
            assertEquals(80, result.fatMax());
            assertEquals(15, result.fatPercent());

            assertEquals(25, result.proteinCurrent());
            assertEquals(100, result.proteinMax());
            assertEquals(25, result.proteinPercent());

            assertEquals(50, result.carbohydratesCurrent());
            assertEquals(300, result.carbohydratesMax());
            assertEquals(16, result.carbohydratesPercent());

            assertEquals((12 * 9) + (25 * 4) + (50 * 4), result.kcalToday());
        }

    }


    @BeforeEach
    void setUp() {
        AuthAppUser mockUser = AuthAppUser.builder()
                .id("55555")
                .username("mockuser")
                .birthday(LocalDate.of(1990, 1, 1))
                .weight(75)
                .height(180)
                .gender(Gender.MALE)
                .simpleGrantedAuthorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                .attributes(Map.of())
                .build();

        Authentication auth = mock(Authentication.class);
        lenient().when(auth.getPrincipal()).thenReturn(mockUser);

        SecurityContext context = mock(SecurityContext.class);
        lenient().when(context.getAuthentication()).thenReturn(auth);

        SecurityContextHolder.setContext(context);
    }

    @Test
    void getDailyMealsList_shouldReturnMealsForDate() {
        //GIVEN
        LocalDate today = LocalDate.now();
        DailyMeal meal1 = new DailyMeal("1", "55555", "5564", LocalDateTime.of(today, LocalDateTime.now().toLocalTime()), 10, 20, 5);
        when(dailyMealRepository.findAllByUserId("55555")).thenReturn(List.of(meal1));

        //WHEN
        List<DailyMeal> result = mealService.getDailyMealsList(today);

        //THEN
        assertEquals(1, result.size());
        assertEquals("1", result.getFirst().id());
    }

    @Test
    void getDailyMealsList_shouldThrowExceptionIfEmpty() {
        //GIVEN
        when(dailyMealRepository.findAllByUserId("55555")).thenReturn(List.of());

        //WHEN
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> mealService.getDailyMealsList(LocalDate.now()));

        //THEN
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void getMealById_shouldReturnMeal() {
        //GIVEN
        Meals meal = new Meals("5564", "55555", "Test Meal", 10, 20, 5);
        when(mealsRepository.findById("5564")).thenReturn(Optional.of(meal));

        //WHEN
        Meals result = mealService.getMealById("5564");

        //THEN
        assertEquals("Test Meal", result.name());
    }

    @Test
    void getMealById_shouldThrowExceptionIfNotFound() {
        when(mealsRepository.findById("5564")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> mealService.getMealById("5564"));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void deleteMealById_shouldDeleteMeal() {
        //GIVEN
        when(dailyMealRepository.existsById("5564")).thenReturn(true);

        //WHEN
        mealService.deleteMealById("5564");

        verify(dailyMealRepository).deleteById("5564");
    }

    @Test
    void deleteMealById_shouldThrowExceptionIfNotFound() {
        when(dailyMealRepository.existsById("5564")).thenReturn(false);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> mealService.deleteMealById("5564"));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void updateDailyMeal() {
        //GIVEN
        DailyMeal dailyMeal = new DailyMeal("5564", "55555", "5564", LocalDateTime.now(), 12, 30, 68);
        when(dailyMealRepository.save(dailyMeal)).thenReturn(dailyMeal);

        //WHEN
        DailyMeal result = mealService.updateDailyMeal(dailyMeal);

        //THEN
        assertEquals("5564", result.id());
        verify(dailyMealRepository).save(dailyMeal);

    }
}