package de.supernerd.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    private AppUserUpdate mockUser;

    @BeforeEach
    void setUp() {
        mockUser = new AppUserUpdate(
                "55664233",
                "55555",
                "Test",
                "User",
                LocalDate.of(1990, 1, 1),
                75.0,
                180.0,
                Gender.MALE
        );
    }

    @Test
    void saveUser_shouldReturnSavedUser() {
        // GIVEN
        when(userRepository.save(mockUser)).thenReturn(mockUser);

        // WHEN
        AppUserUpdate result = userService.saveUser(mockUser);

        // THEN
        assertEquals("55555", result.userid());
        verify(userRepository).save(mockUser);
    }

    @Test
    void getById_shouldReturnUser_whenUserExists() {
        // GIVEN
        when(userRepository.findByUserid("55555")).thenReturn(Optional.of(mockUser));

        // WHEN
        AppUserUpdate result = userService.getById("55555");

        // THEN
        assertNotNull(result);
        assertEquals("Test", result.firstname());
        verify(userRepository).findByUserid("55555");
    }

    @Test
    void getById_shouldThrowException_whenUserNotFound() {
        // GIVEN
        when(userRepository.findByUserid("9956")).thenReturn(Optional.empty());

        // WHEN / THEN
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                userService.getById("9956")
        );
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertNotNull(exception.getReason());
        assertTrue(exception.getReason().contains("User not found"));
    }
}