package de.supernerd.meal.requestDto;

public record MealByBarcodeResponseDto(
        String barcode,
        String mealName,
        int protein,
        int carbohydrates,
        int fat
) {
}
