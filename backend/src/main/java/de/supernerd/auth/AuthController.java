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
        if(oAuth2User instanceof AuthAppUser appUser) {
            int age = Birthday.getAge(appUser.getBirthday());
            String gender = "MALE";
            if(appUser.getFirstname() == null || appUser.getFirstname().isEmpty()) {
                appUser.setFirstname("");
            }

            if(appUser.getLastname() == null || appUser.getFirstname().isEmpty()) {
                appUser.setLastname("Unbekannt");
            }

            if(appUser.getBirthday() == null) {
                appUser.setBirthday(LocalDate.of(1981, 8, 11));
            }

            if(appUser.getWeight() == 0) {
                appUser.setWeight(80);
            }

            if(appUser.getHeight() == 0) {
                appUser.setHeight(180);
            }

            if(appUser.getAge() == 0) {
                appUser.setAge(43);
            }

            if(appUser.getGender() != null) {
                gender = appUser.getGender().toString();
            }
            appUser.setGender(Gender.valueOf(gender));

            appUser.setMetabolicRate(MetabolismUtils.calculateBasalMetabolicRate( age, appUser.getWeight(), appUser.getHeight(), gender ));
            appUser.setAge(age);
            return appUser;
        } else {
            return null;
        }
    }
}
