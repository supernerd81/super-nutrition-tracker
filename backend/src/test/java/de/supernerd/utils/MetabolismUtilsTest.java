package de.supernerd.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MetabolismUtilsTest {


    @Test
    void calculateBasalMetabolicRate_shouldReturnCorrectValueForMale() {
        double result = MetabolismUtils.calculateBasalMetabolicRate(30, 70, 180, "male");

        double expected = (10 * 70 + 6.25 * 180 - 5 * 30 + 5) * 1.5;
        assertEquals(expected, result, 0.01);
    }

    @Test
    void calculateBasalMetabolicRate_shouldReturnCorrectValueForFemale() {
        double result = MetabolismUtils.calculateBasalMetabolicRate(30, 70, 180, "female");
        double expected = (10 * 70 + 6.25 * 180 - 5 * 30 - 161) * 1.5;
        assertEquals(expected, result, 0.01);
    }

    @Test
    void calculateBasalMetabolicRate_shouldReturn0IfGenderIsEmpty() {
        double result = MetabolismUtils.calculateBasalMetabolicRate(30, 70, 180, "");
        assertEquals(0, result);
    }

    @Test
    void calculateBasalMetabolicRate_shouldThrowExceptionForInvalidGender() {
        assertThrows(IllegalArgumentException.class, () ->
                MetabolismUtils.calculateBasalMetabolicRate(30, 70, 180, "nonbinary")
        );
    }

    @Test
    void calculateMaxFatRate_shouldCalculateCorrectly() {
        int result = MetabolismUtils.calculateMaxFatRate(2000);
        int expected = (int) Math.round(2000 * 0.25 / 9);
        assertEquals(expected, result);
    }

    @Test
    void calculateMaxCarbohydratesRate_shouldCalculateCorrectly() {
        int result = MetabolismUtils.calculateMaxCarbohydratesRate(2000);
        int expected = (int) Math.round(2000 * 0.5 / 4);
        assertEquals(expected, result);
    }

    @Test
    void calculateMaxProteinRate_shouldCalculateCorrectly() {
        int result = MetabolismUtils.calculateMaxProteinRate(2000);
        int expected = (int) Math.round(2000 * 0.25 / 4);
        assertEquals(expected, result);
    }
}