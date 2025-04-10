package de.supernerd.user;

import java.time.LocalDate;

public record ResponseUserDto(
        String id,
        String userid,
        String firstname,
        String lastname,
        LocalDate birthday,
        double age,
        double weight,
        double height,
        Gender gender,
        double metabolicRate
) {
}
