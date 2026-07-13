package com.mathverse.core.config;

import com.mathverse.core.security.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, RequestFilter requestFilter) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .sessionManagement(session->session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class)
                        .authorizeHttpRequests(auth->auth
                                .requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/api/teacher/**").hasRole("TEACHER")
                                .anyRequest().authenticated())
                                .build();

    }

    @Bean
    public RequestFilter requestFilter(JwtService jwtService){
        return new RequestFilter(jwtService);
    }
}
