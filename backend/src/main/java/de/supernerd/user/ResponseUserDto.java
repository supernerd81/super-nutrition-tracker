package de.supernerd.user;

import java.time.LocalDate;

public record ResponseUserDto(
        String id,
        String userid,
        String firstname,
        String lastname,
        LocalDate birthday,
        int weight,
        int height
) {
}
