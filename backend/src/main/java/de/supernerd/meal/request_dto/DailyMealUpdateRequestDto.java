package de.supernerd.meal.request_dto;

import java.time.LocalDateTime;

public record DailyMealUpdateRequestDto(
        String id,
        String userId,
        String mealsId,
        LocalDateTime dateTime,
        int protein,
        int carbohydrates,
        int fat
) {
}
