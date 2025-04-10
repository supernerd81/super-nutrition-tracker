package de.supernerd.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Value("${app.url}")
    private String appUrl;

    @Bean
    public SecurityFilterChain securityFileterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(a -> a
                        .requestMatchers("/appi/auth/me").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api").hasRole(AuthAppUserRoles.ADMIN.toString())
                        .anyRequest().authenticated()
                )
                .sessionManagement(s->
                        s.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                )
                .logout(l -> l.logoutSuccessUrl(appUrl))
                .oauth2Login(o -> o.defaultSuccessUrl(appUrl))
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                );

        return http.build();
    }
}