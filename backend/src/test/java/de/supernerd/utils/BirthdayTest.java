package de.supernerd.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Period;

import static org.junit.jupiter.api.Assertions.*;

class BirthdayTest {

    @Test
    void getAge_birthdayIsNull() {

        assertEquals(0, Birthday.getAge(null));
    }

    @Test
    void getAge_birtshdayIsNotNull() {
        LocalDate birthday = LocalDate.of(1981, 8, 11);
        int expectedAge = Period.between(birthday, LocalDate.now()).getYears();

        assertEquals(expectedAge, Birthday.getAge(birthday));
    }
}