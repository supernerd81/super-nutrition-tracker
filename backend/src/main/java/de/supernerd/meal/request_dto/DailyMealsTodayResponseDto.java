package de.supernerd.meal.request_dto;

public record DailyMealsTodayResponseDto(
        int fatCurrent,
        int fatMax,
        int fatPercent,
        int carbohydratesCurrent,
        int carbohydratesMax,
        int carbohydratesPercent,
        int proteinCurrent,
        int proteinMax,
        int proteinPercent,
        int kcalToday
) {
}
