package de.supernerd.meal.requestDto;

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
