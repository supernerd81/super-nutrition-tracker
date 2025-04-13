package de.supernerd.meal;

import de.supernerd.meal.models.DailyMeal;
import de.supernerd.meal.models.Meals;
import de.supernerd.meal.repository.DailyMealRepository;
import de.supernerd.meal.repository.MealsRepository;
import de.supernerd.meal.requestDto.DailyMealResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MealService {
    private final MealsRepository mealsRepository;
    private final DailyMealRepository dailyMealRepository;

    public MealService(MealsRepository mealsRepository, DailyMealRepository dailyMealRepository) { this.mealsRepository = mealsRepository;
        this.dailyMealRepository = dailyMealRepository;
    }

    public DailyMeal saveDailyMeal(DailyMeal dailyMeal) {
        return dailyMealRepository.save(dailyMeal);
    }

    public Meals saveMeals(Meals meals) {

        if(meals.barcode() != null && !meals.barcode().isEmpty()) {
            Optional<Meals> existingByBarcode = mealsRepository.findByBarcode(meals.barcode());
            if(existingByBarcode.isPresent()) {
                return existingByBarcode.get();
            }

            Optional<Meals> existingByName = mealsRepository.findByName(meals.name());
            return existingByName.orElseGet(() -> mealsRepository.save(meals));

        }

        return mealsRepository.save(meals);
    }

    public DailyMeal deleteMeal(DailyMeal dailyMeal) {
        return new DailyMeal("", "", "", null, 0, 0, 0);
    }

    public List<DailyMealResponseDto> getAllMealsByUserId(String userId) {
        List<DailyMeal> mealsList = dailyMealRepository.findAllByUserId(userId);

        List<String> catalogIds = mealsList.stream()
                .map(DailyMeal::mealCatalogId)
                .distinct()
                .toList();

        Map<String, Meals> catalogMap = mealsRepository.findAllById(catalogIds)
                .stream()
                .collect(Collectors.toMap(Meals::id, Function.identity()));

        return mealsList.stream()
                .map(meal -> new DailyMealResponseDto(
                        meal.id(),
                        meal.userId(),
                        meal.mealCatalogId(),
                        catalogMap.get(meal.mealCatalogId()),
                        meal.datetime()
                ))
                .toList();
    }

    public DailyMeal getMealById(String mealId) {
        return new DailyMeal("", "", "", null, 0, 0, 0);
    }

    public Meals getMealByBarcode(String barcode) {
        return mealsRepository.findByBarcode(barcode).orElseThrow( () -> new NoSuchElementException("No meals with barcode " + barcode) );
    }
}
