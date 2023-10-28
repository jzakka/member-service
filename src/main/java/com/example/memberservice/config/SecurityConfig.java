package com.example.memberservice.config;

import com.example.memberservice.filter.AuthenticationFilter;
import com.example.memberservice.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final MemberService memberService;
    private final Environment env;

    @SneakyThrows
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationConfiguration authConfig) {
        http.csrf(AbstractHttpConfigurer::disable);
        return http.authorizeHttpRequests(auth -> auth
//                                .requestMatchers(new AntPathRequestMatcher("/login")).permitAll()
//                                .requestMatchers(new AntPathRequestMatcher("/members", HttpMethod.POST.toString())).permitAll()
//                                .anyRequest().authenticated()
                                .requestMatchers(new AntPathRequestMatcher("/**")).permitAll()
                )
                .addFilter(authenticationFilter(authConfig))
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @SneakyThrows
    private AuthenticationFilter authenticationFilter(AuthenticationConfiguration authConfig) {
        AuthenticationManager authManager = authConfig.getAuthenticationManager();

        AuthenticationFilter authFilter = new AuthenticationFilter(authManager, memberService, env);
        authFilter.setAuthenticationManager(authManager);

        return authFilter;
    }
}
