package de.supernerd.meal;

import lombok.With;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("dailymeal")
@With
public record DailyMeal(
     @id
     String id,
     String mealCatalogId,
     LocalDateTime datetime
) {
}
