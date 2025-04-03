package de.supernerd.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final AuthAppUserRepository userRepository;
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
        return authAppUserRepository.save(AuthAppUser.builder()
                .id(oAuth2User.getName())
                .username(oAuth2User.getAttributes().get("login").toString())
                .role(AuthAppUserRoles.USER)
                .build()
        );
    }
}
