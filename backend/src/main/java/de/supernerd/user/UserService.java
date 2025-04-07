package de.supernerd.user;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) { this.userRepository = userRepository; }

    public AppUserDetails getUser(String email) {
        return null;
    }

    public AppUserDetails saveUser(AppUserDetails user) {
        String uuid = UUID.randomUUID().toString();
        AppUserDetails userToSave = user.withId(uuid);

        return userRepository.save(userToSave);
    }

    public AppUserDetails getById(String id) {

        return  userRepository.findByUserid(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }
}
