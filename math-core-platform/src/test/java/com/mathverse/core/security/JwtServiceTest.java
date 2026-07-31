package com.mathverse.core.security;

import com.mathverse.core.entity.Role;
import com.mathverse.core.entity.User;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class JwtServiceTest {

    private final JwtService jwtService = new JwtService();

    @Test
    void generateAndParseToken_shouldContainCorrectEmailAndRole() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setRole(Role.STUDENT);

        String token = jwtService.generateToken(user);
        Claims claims = jwtService.parseToken(token);

        assertThat(claims.getSubject()).isEqualTo("test@example.com");
        assertThat(claims.get("role", String.class)).isEqualTo("STUDENT");
    }

    @Test
    void correctTime(){
        User user = new User();
        user.setEmail("test@example.com");
        user.setRole(Role.STUDENT);

        String token = jwtService.generateToken(user);
        Claims claims = jwtService.parseToken(token);

        assertThat(claims.getExpiration()).isAfter(claims.getIssuedAt());    }

    @Test
    void invalidToken() {
        assertThatThrownBy(() -> jwtService.parseToken("not.a.valid.token"))
                .isInstanceOf(Exception.class);
    }
}