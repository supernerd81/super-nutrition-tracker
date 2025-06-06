package de.supernerd.meal.request_dto;

import java.time.LocalDateTime;

public record DailyMealOverviewResponseDto(
        String id,
        String userId,
        String mealsId,
        LocalDateTime dateTime,
        String name,
        int fat,
        int carbohydrates,
        int protein
) {
}
