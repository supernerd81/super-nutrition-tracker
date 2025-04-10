package de.supernerd.auth;

import de.supernerd.user.Gender;
import de.supernerd.utils.Birthday;
import de.supernerd.utils.MetabolismUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/me")
    public AuthAppUser getMe(@AuthenticationPrincipal OAuth2User oAuth2User) {
        if(oAuth2User instanceof AuthAppUser appUser) {
            int age = Birthday.getAge(appUser.getBirthday());
            String gender = "MALE";

            if(appUser.getGender() != null) {
                gender = appUser.getGender().toString();
            }
            appUser.setGender(Gender.valueOf(gender));

            appUser.setMetabolicRate(MetabolismUtils.calculateBasalMetabolicRate( age, appUser.getHeight(), appUser.getWeight(), gender ));
            appUser.setAge(age);
            return appUser;
        } else {
            return null;
        }
    }
}
