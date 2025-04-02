package de.supernerd.utils;

import java.time.LocalDate;
import java.time.Period;

public class Birthday {
    public static int getAge(LocalDate birthday) {
        LocalDate now = LocalDate.now();

        return Period.between(birthday, now).getYears();
    }
}
