package com.mathverse.core.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mathverse.core.dto.LoginRequest;
import com.mathverse.core.dto.RegisterRequest;
import com.mathverse.core.entity.Role;
import com.mathverse.core.entity.User;
import com.mathverse.core.security.JwtService;
import com.mathverse.core.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthController authController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void register_shouldReturn201AndToken() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@mathverse.com");
        request.setPassword("password123");

        User user = new User();
        user.setEmail("test@mathverse.com");
        user.setRole(Role.STUDENT);

        when(userService.register("test@mathverse.com", "password123")).thenReturn(user);
        when(jwtService.generateToken(any(User.class))).thenReturn("fake-jwt-token");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").value("fake-jwt-token"))
                .andExpect(jsonPath("$.role").value("STUDENT"));
    }

    @Test
    void login_shouldReturn200AndToken() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@mathverse.com");
        request.setPassword("password123");

        User user = new User();
        user.setEmail("test@mathverse.com");
        user.setRole(Role.STUDENT);

        when(userService.login("test@mathverse.com", "password123")).thenReturn(user);
        when(jwtService.generateToken(any(User.class))).thenReturn("fake-jwt-token");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("fake-jwt-token"))
                .andExpect(jsonPath("$.role").value("STUDENT"));
    }
}
