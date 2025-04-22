package de.supernerd.auth;

import de.supernerd.user.Gender;
import de.supernerd.utils.Birthday;
import de.supernerd.utils.MetabolismUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/me")
    public AuthAppUser getMe(@AuthenticationPrincipal OAuth2User oAuth2User) {
        if (!(oAuth2User instanceof AuthAppUser appUser)) {
            return null;
        }

        sanitizeUser(appUser);
        int age = Birthday.getAge(appUser.getBirthday());
        String gender = getValidGender(appUser);

        appUser.setGender(Gender.valueOf(gender));
        appUser.setMetabolicRate(MetabolismUtils.calculateBasalMetabolicRate(
                age, appUser.getWeight(), appUser.getHeight(), gender));
        appUser.setAge(age);

        return appUser;
    }

    private void sanitizeUser(AuthAppUser user) {
        if (isNullOrEmpty(user.getFirstname())) user.setFirstname("");
        if (isNullOrEmpty(user.getLastname())) user.setLastname("Unbekannt");
        if (user.getBirthday() == null) user.setBirthday(LocalDate.of(1981, 8, 11));
        if (user.getWeight() == 0) user.setWeight(80);
        if (user.getHeight() == 0) user.setHeight(180);
        if (user.getAge() == 0) user.setAge(43);
    }

    private String getValidGender(AuthAppUser user) {
        return user.getGender() != null ? user.getGender().toString() : "MALE";
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }
}
