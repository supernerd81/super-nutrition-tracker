package de.supernerd.meal.models;

import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("DailyMeal")
@With
public record DailyMeal(
     @Id
     String id,
     String userId,
     String mealsId,
     LocalDateTime datetime,
     int protein,
     int carbohydrates,
     int fat
) {
}