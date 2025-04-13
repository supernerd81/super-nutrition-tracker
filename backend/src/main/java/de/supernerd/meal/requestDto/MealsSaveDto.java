package de.supernerd.meal.requestDto;

public record MealsSaveDto(
    String id,
    String barcode,
    String name,
    int protein,
    int carbohydrates,
    int fat
) {
}
