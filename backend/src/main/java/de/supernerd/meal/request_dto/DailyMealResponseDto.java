package de.supernerd.meal.request_dto;

import de.supernerd.meal.models.Meals;

import java.time.LocalDateTime;

public record DailyMealResponseDto(
         String id,
        String userId,
        String mealsId,
        Meals meals,
        LocalDateTime datetime
) {
}
