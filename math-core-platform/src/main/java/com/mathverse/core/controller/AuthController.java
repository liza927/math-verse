package com.mathverse.core.controller;

import com.mathverse.core.dto.AuthResponse;
import com.mathverse.core.dto.LoginRequest;
import com.mathverse.core.dto.RefreshTokenRequest;
import com.mathverse.core.dto.RegisterRequest;
import com.mathverse.core.entity.RefreshToken;
import com.mathverse.core.entity.Role;
import com.mathverse.core.entity.User;
import com.mathverse.core.security.JwtService;
import com.mathverse.core.service.RefreshTokenService;
import com.mathverse.core.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Аутентификация", description = "Регистрация, вход и обновление токена")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    @Operation(summary = "Регистрация нового пользователя", description = "Создаёт нового пользователя с ролью STUDENT")
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse authResponse = new AuthResponse();
        User newStudent = userService.register(request.getEmail(), request.getPassword());
        String token = jwtService.generateToken(newStudent);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(newStudent);
        authResponse.setToken(token);
        authResponse.setRefreshToken(refreshToken.getToken());
        authResponse.setRole(Role.valueOf(newStudent.getRole().name()));
        return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
    }

    @Operation(summary = "Вход в систему", description = "Проверяет учётные данные и возвращает JWT-токен + refresh-токен")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse authResponse = new AuthResponse();
        User loggedInUser = userService.login(request.getEmail(), request.getPassword());
        String token = jwtService.generateToken(loggedInUser);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(loggedInUser);
        authResponse.setToken(token);
        authResponse.setRefreshToken(refreshToken.getToken());
        authResponse.setRole(Role.valueOf(loggedInUser.getRole().name()));
        return ResponseEntity.status(HttpStatus.OK).body(authResponse);
    }

    @Operation(summary = "Обновление access-токена", description = "Принимает валидный refresh-токен и возвращает новый access-токен")
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenService.verifyExpiration(request.getRefreshToken());
        User user = refreshToken.getUser();
        String newAccessToken = jwtService.generateToken(user);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(newAccessToken);
        authResponse.setRefreshToken(refreshToken.getToken());
        authResponse.setRole(user.getRole());
        return ResponseEntity.ok(authResponse);
    }
}