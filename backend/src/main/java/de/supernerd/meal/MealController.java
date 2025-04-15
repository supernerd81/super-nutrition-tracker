package de.supernerd.meal;

import de.supernerd.meal.models.DailyMeal;
import de.supernerd.meal.models.Meals;
import de.supernerd.meal.request_dto.DailyMealNewRequestDto;
import de.supernerd.meal.request_dto.DailyMealResponseDto;
import de.supernerd.meal.request_dto.DailyMealsTodayResponseDto;
import de.supernerd.meal.request_dto.MealByBarcodeResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/meal")
public class MealController {

    private final MealService mealService;

    public MealController(MealService mealService) { this.mealService = mealService; }

    @GetMapping("/all")
    public String getAllMeals() {

        //196103614
        mealService.getMealsByUserId("196103614");
        return "";
    }

    @GetMapping("/{userid}")
    public List<DailyMealResponseDto> getMealById(@PathVariable String userid) {

        return mealService.getAllMealsByUserId(userid);
    }

    @PostMapping("/new")
    public DailyMealResponseDto newMeal(@RequestBody DailyMealNewRequestDto dailyMealDto) {
        Meals meals = mealService.saveMeals(new Meals(null, dailyMealDto.barcode(), dailyMealDto.mealName(), dailyMealDto.protein(), dailyMealDto.carbohydrates(), dailyMealDto.fat()));
        DailyMeal dailyMeal = mealService.saveDailyMeal(new DailyMeal(null, dailyMealDto.userId(), meals.id(), dailyMealDto.dateTime(), dailyMealDto.protein(), dailyMealDto.carbohydrates(), dailyMealDto.fat()));

        return new DailyMealResponseDto(dailyMeal.id(), dailyMeal.userId(), meals.id(), meals, dailyMeal.datetime());
    }

    @GetMapping("/barcode/{barcode}")
    public ResponseEntity<MealByBarcodeResponseDto> mealByBarcode(@PathVariable String barcode) {
        try {
            Meals meal = mealService.getMealByBarcode(barcode);
            return ResponseEntity.ok(new MealByBarcodeResponseDto(meal.barcode(), meal.name(), meal.protein(), meal.carbohydrates(), meal.fat()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/today/{userid}")
    public ResponseEntity<DailyMealsTodayResponseDto> mealsToday(@PathVariable String userid) {
        try {
            return ResponseEntity.ok(mealService.getMealsByUserId(userid));
        } catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/update")
    public DailyMealNewRequestDto updateMeal(DailyMealNewRequestDto dailyMealDto) {

        //MealsSaveDto mealCatalogSave = new MealsSaveDto(dailyMealDto.barcode(), dailyMealDto.mealName(), dailyMealDto.protein(), dailyMealDto.carbohydrates(), dailyMealDto.fat());


        return dailyMealDto;
    }

    @DeleteMapping("/delete/{mealTodayId}")
    public void deleteMeal(@PathVariable String mealTodayId) {

    }
}
