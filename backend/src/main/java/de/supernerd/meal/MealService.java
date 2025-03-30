package de.supernerd.meal;

import org.springframework.stereotype.Service;

@Service
public class MealService {
    private final MealRepository mealRepository;

    public MealService(MealRepository mealRepository) { this.mealRepository = mealRepository;}
}
