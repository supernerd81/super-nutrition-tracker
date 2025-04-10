package de.supernerd.meal;

import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("meal_catalog")
@With
public record MealCatalog(
    @Id
    String id,
    String barcode,
    String name,
    int protein,
    int carbohydrates,
    int fat
) {
}
