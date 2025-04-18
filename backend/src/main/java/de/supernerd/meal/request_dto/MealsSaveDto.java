package de.supernerd.meal.request_dto;

public record MealsSaveDto(
    String id,
    String barcode,
    String name,
    int protein,
    int carbohydrates,
    int fat
) {
}
