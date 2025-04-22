package de.supernerd.auth;

import de.supernerd.user.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AuthControllerTest {

    private AuthController controller;

    @BeforeEach
    void setUp() {
        controller = new AuthController();
    }

    @Test
    void testSanitizeUser() {
        AuthAppUser user = new AuthAppUser();
        user.setFirstname(null);
        user.setLastname(null);
        user.setBirthday(null);
        user.setWeight(0);
        user.setHeight(0);
        user.setAge(0);

        controller.sanitizeUser(user);

        assertEquals("", user.getFirstname());
        assertEquals("Unbekannt", user.getLastname());
        assertEquals(LocalDate.of(1981, 8, 11), user.getBirthday());
        assertEquals(80, user.getWeight());
        assertEquals(180, user.getHeight());
        assertEquals(43, user.getAge());
    }

    @Test
    void testGetValidGenderWhenNull() {
        AuthAppUser user = new AuthAppUser();
        user.setGender(null);

        String gender = controller.getValidGender(user);

        assertEquals("MALE", gender);
    }

    @Test
    void testGetMeReturnsSanitizedUser() {
        AuthAppUser mockUser = new AuthAppUser();
        mockUser.setBirthday(LocalDate.of(1990, 1, 1));
        mockUser.setWeight(70);
        mockUser.setHeight(175);
        mockUser.setGender(Gender.MALE);

        AuthAppUser result = controller.getMe(mockUser);  // getMe ist öffentlich!

        assertNotNull(result);
        assertEquals(70, result.getWeight());
        assertEquals(175, result.getHeight());
        assertNotNull(result.getMetabolicRate());
    }
}