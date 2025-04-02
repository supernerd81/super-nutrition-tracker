package de.supernerd.user;

import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) { this.userRepository = userRepository; }

    public User getUser(String email) {
        return null;
    }

    public User saveUser(User user) {
        String uuid = UUID.randomUUID().toString();
        User userToSave = user.withId(uuid);

        return userRepository.save(userToSave);
    }

    public User getById(String id) {
        return userRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }
}
