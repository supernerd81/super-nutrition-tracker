package de.supernerd.user;

import java.time.LocalDate;

public record ResponseUserWithAgeDto(
    String id,
    String firstname,
    String lastname,
    LocalDate birthday,
    int age,
    int weight,
    int height
) {
}
