package de.supernerd.utils;

public class MetabolismUtils {
    public static double calculateBasalMetabolicRate(int age, double weight, double height, String gender) {

        if(gender.isEmpty()) {
            return 0;
        }

        if(gender.equalsIgnoreCase("male")) {
            return (10 * weight + 6.25 * height - 5 * age + 5) * 1.8;
        } else if(gender.equalsIgnoreCase("female")) {
            return (10 * weight + 6.25 * height - 5 * age - 161) * 1.8;
        } else {
            throw new IllegalArgumentException("Invalid gender: " + gender);
        }
    }
}
