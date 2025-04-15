package de.supernerd.meal.request_dto;

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