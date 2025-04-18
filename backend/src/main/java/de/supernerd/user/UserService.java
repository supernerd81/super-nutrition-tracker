package de.supernerd.user;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) { this.userRepository = userRepository; }

    public AppUserUpdate getUser(String email) {
        return null;
    }

    public AppUserUpdate saveUser(AppUserUpdate user) {
//        String uuid = UUID.randomUUID().toString();
//        AppUserUpdate userToSave = user.withId(uuid);

            return userRepository.save(user);
    }

    public AppUserUpdate getById(String id) {

        return  userRepository.findByUserid(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }
}
