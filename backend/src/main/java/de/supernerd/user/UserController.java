package de.supernerd.user;

import de.supernerd.utils.Birthday;
import de.supernerd.utils.MetabolismUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) { this.userService = userService; }

    @PutMapping("/update/{userId}")
    public ResponseUserDto updateUser(@RequestBody UpdateUserDto updateUser, @PathVariable String userId) {
        AppUserUpdate savedUser = userService.saveUser(new AppUserUpdate(userId, userId, updateUser.firstname(), updateUser.lastname(), updateUser.birthday(), updateUser.weight(), updateUser.height(), updateUser.gender()));
        return new ResponseUserDto(userId, userId, savedUser.firstname(), savedUser.lastname(), savedUser.birthday(), Birthday.getAge(savedUser.birthday()), savedUser.weight(), savedUser.height(), savedUser.gender(), MetabolismUtils.calculateBasalMetabolicRate(Birthday.getAge(savedUser.birthday()), savedUser.weight(), savedUser.height(), savedUser.gender().getDisplayName()));
    }

    @GetMapping("/{id}")
    public ResponseUserWithAgeDto getNerdUserById(@PathVariable String id) {

        try {
            AppUserUpdate user = userService.getById(id);
            return new ResponseUserWithAgeDto(user.id(), user.userid(), user.firstname(), user.lastname(), user.birthday(), Birthday.getAge(user.birthday()), user.weight(), user.height(), user.gender(), MetabolismUtils.calculateBasalMetabolicRate(Birthday.getAge(user.birthday()), user.weight(), user.height(), user.gender().getDisplayName()));
        } catch(NoSuchElementException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }
}