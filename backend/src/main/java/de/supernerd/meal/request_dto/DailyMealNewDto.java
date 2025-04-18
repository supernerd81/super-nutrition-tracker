package de.supernerd.meal.request_dto;

import java.time.LocalDateTime;

public record DailyMealNewDto(
        String userId,
        LocalDateTime dateTime,
        String mealsId,
        int protein,
        int carbohydrates,
        int fat
) {
}
