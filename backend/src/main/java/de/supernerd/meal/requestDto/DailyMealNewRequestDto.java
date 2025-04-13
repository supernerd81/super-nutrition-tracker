package de.supernerd.meal.requestDto;

import java.time.LocalDateTime;

public record DailyMealNewRequestDto(
     String userId,
     LocalDateTime dateTime,
     String barcode,
     String mealName,
     int protein,
     int carbohydrates,
     int fat
) {
}