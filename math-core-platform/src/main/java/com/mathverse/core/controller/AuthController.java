package com.mathverse.core.controller;

import com.mathverse.core.dto.AuthResponse;
import com.mathverse.core.dto.LoginRequest;
import com.mathverse.core.dto.RegisterRequest;
import com.mathverse.core.entity.Role;
import com.mathverse.core.entity.User;
import com.mathverse.core.security.JwtService;
import com.mathverse.core.service.UserService;
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
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody  RegisterRequest request){
        AuthResponse authResponse = new AuthResponse();
        User newStudent = userService.register(request.getEmail(), request.getPassword());
        String token = jwtService.generateToken(newStudent);
        authResponse.setToken(token);
        authResponse.setRole(Role.valueOf(newStudent.getRole().name()));
        return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        AuthResponse authResponse = new AuthResponse();
        User loggedInUser = userService.login(request.getEmail(), request.getPassword());
        String token = jwtService.generateToken(loggedInUser);
        authResponse.setToken(token);
        authResponse.setRole(Role.valueOf(loggedInUser.getRole().name()));
        return ResponseEntity.status(HttpStatus.OK).body(authResponse);
    }
}
