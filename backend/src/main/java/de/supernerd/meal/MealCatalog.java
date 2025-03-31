package de.supernerd.meal;

import lombok.With;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("meal_catalog")
@With
public record MealCatalog(
    @id
    String id,
    String barcode,
    String name,
    int protein,
    int carbohydrates,
    int fat
) {
}
