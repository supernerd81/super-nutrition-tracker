package de.supernerd.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final AuthAppUserRepository authAppUserRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        AuthAppUser authAppUser = authAppUserRepository.findById(oAuth2User.getName())
                .orElseGet(() -> saveUser(oAuth2User));
        authAppUser.setSimpleGrantedAuthorities(List.of(new SimpleGrantedAuthority("ROLE_" + authAppUser.getRole())));
        return authAppUser;
    }

    private AuthAppUser saveUser(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        LocalDate birthday = null;
        if (attributes.containsKey("birthday")) {
            birthday = LocalDate.parse(attributes.get("birthday").toString());
        }

        return authAppUserRepository.save(AuthAppUser.builder()
                .id(oAuth2User.getName())
                .username(oAuth2User.getAttributes().get("login").toString())
                .birthday(birthday)
                .role(AuthAppUserRoles.USER)
                .build()
        );
    }
}
