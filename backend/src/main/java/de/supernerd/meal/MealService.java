package de.supernerd.meal;

import de.supernerd.auth.AuthAppUser;
import de.supernerd.meal.models.DailyMeal;
import de.supernerd.meal.models.Meals;
import de.supernerd.meal.repository.DailyMealRepository;
import de.supernerd.meal.repository.MealsRepository;
import de.supernerd.meal.request_dto.DailyMealResponseDto;
import de.supernerd.meal.request_dto.DailyMealsTodayResponseDto;
import de.supernerd.utils.Birthday;
import de.supernerd.utils.MetabolismUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
                .map(DailyMeal::mealsId)
                .distinct()
                .toList();

        Map<String, Meals> catalogMap = mealsRepository.findAllById(catalogIds)
                .stream()
                .collect(Collectors.toMap(Meals::id, Function.identity()));

        return mealsList.stream()
                .map(meal -> new DailyMealResponseDto(
                        meal.id(),
                        meal.userId(),
                        meal.mealsId(),
                        catalogMap.get(meal.mealsId()),
                        meal.datetime()
                ))
                .toList();
    }

    public Meals getMealByBarcode(String barcode) {
        return mealsRepository.findByBarcode(barcode).orElseThrow( () -> new NoSuchElementException("No meals with barcode " + barcode) );
    }

    public DailyMealsTodayResponseDto getMealsByUserId(String userId) {
        AuthAppUser currentUser = (AuthAppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<DailyMeal> mealsList = dailyMealRepository.findAllByUserId(userId);
        LocalDate today = LocalDate.now();

        double metabolicRate = MetabolismUtils.calculateBasalMetabolicRate(Birthday.getAge(currentUser.getBirthday()), currentUser.getWeight(), currentUser.getHeight(), currentUser.getGender().toString());

        int totalFat  = mealsList.stream()
                .filter(meal -> meal.datetime().toLocalDate().equals(today))
                .mapToInt(DailyMeal::fat).sum();

        int totalProtein = mealsList.stream()
                .filter(meal -> meal.datetime().toLocalDate().equals(today))
                .mapToInt(DailyMeal::protein).sum();

        int totalCarbohydrates = mealsList.stream()
                .filter(meal -> meal.datetime().toLocalDate().equals(today))
                .mapToInt(DailyMeal::carbohydrates).sum();

        int maxFatRate = MetabolismUtils.calculateMaxFatRate(metabolicRate);
        int maxMetabolicRate = MetabolismUtils.calculateMaxCarbohydratesRate(metabolicRate);
        int maxProteinRate = MetabolismUtils.calculateMaxProteinRate(metabolicRate);

        int kcalToday = (totalFat*9)+(totalCarbohydrates*4)+(totalProtein*4);

        return new DailyMealsTodayResponseDto(totalFat, maxFatRate, (totalFat*100/maxFatRate),
                totalCarbohydrates, maxMetabolicRate, (totalCarbohydrates*100/maxMetabolicRate),
                totalProtein, maxProteinRate, (totalProtein*100/maxProteinRate), kcalToday);
    }

    public List<DailyMeal> getDailyMealsList(LocalDate date) {
        AuthAppUser currentUser = (AuthAppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<DailyMeal> dailyMeals = dailyMealRepository.findAllByUserId(currentUser.getId());

        return dailyMeals.stream().filter(dailyMeal -> dailyMeal.datetime().toLocalDate().equals(date)).toList();
    }

    public Meals getMealById(String id)   {
        try {
            return mealsRepository.findById(id).orElseThrow( () -> new NoSuchElementException("No meals with id " + id) );
        } catch(NoSuchElementException ex) {
            throw new NoSuchElementException("No meals with id " + id);
        }
    }

    public void deleteMealById(String id) {
        dailyMealRepository.deleteById(id);
    }
}
