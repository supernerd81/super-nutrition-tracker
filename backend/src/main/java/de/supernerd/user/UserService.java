package de.supernerd.user;

import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) { this.userRepository = userRepository; }

    public AppUser getUser(String email) {
        return null;
    }

    public AppUser saveUser(AppUser user) {
        String uuid = UUID.randomUUID().toString();
        AppUser userToSave = user.withId(uuid);

        return userRepository.save(userToSave);
    }

    public AppUser getById(String id) {
        return userRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }
}
