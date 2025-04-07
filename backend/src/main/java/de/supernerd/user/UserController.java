package de.supernerd.user;

import de.supernerd.utils.Birthday;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) { this.userService = userService; }

    @PostMapping("/new")
    public ResponseUserDto newUser(@RequestBody NewUserDto newUser) {
        AppUserDetails savedUser = userService.saveUser(new AppUserDetails(null, "196103614", newUser.firstname(), newUser.lastname(), newUser.birthday(), newUser.weight(), newUser.height()));
        return new ResponseUserDto(savedUser.id(), savedUser.userid(), savedUser.firstname(), savedUser.lastname(), savedUser.birthday(), savedUser.weight(), savedUser.height());
    }

    @GetMapping("/{id}")
    public ResponseUserWithAgeDto getNerdUserById(@PathVariable String id) {

        try {
            AppUserDetails user = userService.getById(id);
            return new ResponseUserWithAgeDto(user.id(), user.userid(), user.firstname(), user.lastname(), user.birthday(), Birthday.getAge(user.birthday()), user.weight(), user.height());
        } catch(NoSuchElementException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }
}