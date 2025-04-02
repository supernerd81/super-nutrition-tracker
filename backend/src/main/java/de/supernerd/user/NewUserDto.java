package de.supernerd.user;

import java.time.LocalDate;

public record NewUserDto(
        String firstname,
        String lastname,
        LocalDate birthday,
        int weight,
        int height
) {
}
