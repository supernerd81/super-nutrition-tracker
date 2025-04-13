package de.supernerd.meal.requestDto;

import de.supernerd.meal.models.Meals;

import java.time.LocalDateTime;

public record DailyMealResponseDto(
         String id,
        String userId,
        String mealCatalogId,
        Meals meals,
        LocalDateTime datetime
) {
}
