package de.supernerd.meal.models;

import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Meals")
@With
public record Meals(
    @Id
    String id,
    String barcode,
    String name,
    int protein,
    int carbohydrates,
    int fat
) {
}
