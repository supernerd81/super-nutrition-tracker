package de.supernerd.utils;

import java.time.LocalDate;
import java.time.Period;

public class Birthday {

    private Birthday() {

    }

    public static int getAge(LocalDate birthday) {
        if(birthday != null) {
            LocalDate now = LocalDate.now();

            return Period.between(birthday, now).getYears();
        }

        return 0;
    }
}