package de.supernerd.user;

import java.time.LocalDate;

public record UpdateUserDto(
        String firstname,
        String lastname,
        LocalDate birthday,
        int weight,
        int height,
        Gender gender
) {
}
