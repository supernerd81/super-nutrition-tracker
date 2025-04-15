package de.supernerd.auth;

import de.supernerd.user.Gender;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import org.springframework.data.annotation.Transient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AuthAppUser implements OAuth2User {
    private String id;
    private String username;
    private AuthAppUserRoles role;
    private String firstname;
    private String lastname;
    private LocalDate birthday;
    private int weight;
    private int height;
    private Gender gender;
    private double metabolicRate;
    private int age;

    @Transient
    @JsonIgnore
    private Map<String, Object> attributes;


    @Transient
    @JsonIgnore
    private List<SimpleGrantedAuthority> simpleGrantedAuthorities;

    @Override
    @JsonIgnore
    public Map<String, Object> getAttributes() { return attributes; }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() { return simpleGrantedAuthorities; }

    @Override
    @JsonIgnore
    public String getName() { return id; }

}
