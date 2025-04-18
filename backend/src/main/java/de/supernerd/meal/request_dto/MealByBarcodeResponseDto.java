package de.supernerd.meal.request_dto;

public record MealByBarcodeResponseDto(
        String barcode,
        String mealName,
        int protein,
        int carbohydrates,
        int fat
) {
}
