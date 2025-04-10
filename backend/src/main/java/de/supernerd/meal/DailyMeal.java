package de.supernerd.meal;

import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("dailymeal")
@With
public record DailyMeal(
     @Id
     String id,
     String userId,
     String mealCatalogId,
     LocalDateTime datetime
) {
}
