package de.supernerd.meal;

import org.springframework.stereotype.Service;

@Service
public class MealService {
    private final MealCatalogRepository mealCatalogRepository;

    public MealService(MealCatalogRepository mealCatalogRepository) { this.mealCatalogRepository = mealCatalogRepository;}
}
