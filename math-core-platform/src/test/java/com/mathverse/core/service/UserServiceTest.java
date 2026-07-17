package com.mathverse.core.service;

import com.mathverse.core.entity.Role;
import com.mathverse.core.entity.User;
import com.mathverse.core.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void register_shouldSaveNewUser_whenEmailNotTaken() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn("hashedPassword123");
        when(userRepository.save(any(User.class))).thenAnswer(invocation->invocation.getArgument(0));

        User result = userService.register("test@example.com", "password123");
        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("test@example.com");
        assertThat(result.getPassword()).isEqualTo("hashedPassword123");
        assertThat(result.getRole()).isEqualTo(Role.STUDENT);
    }

    @Test
    void register_shouldThrowException_whenEmailAlreadyTaken(){
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(new User()));
        assertThatThrownBy(() -> userService.register("test@example.com", "password123"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Email найден!");
    }

    @Test
    void login_shouldReturnUser_whenCredentialsCorrect(){
        User mockUser = new User();
        mockUser.setEmail("test@example.com");
        mockUser.setPassword("hashedPassword123");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches(anyString(),anyString())).thenReturn(true);
        User result = userService.login("test@example.com","qwerty123");
        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("test@example.com");
    }
    @Test
    void login_shouldThrowException_whenEmailNotFound(){
        when(userRepository.findByEmail("testNotFound@example.com")).thenReturn(Optional.empty());
        assertThatThrownBy(()->userService.login("testNotFound@example.com","qwerty123"))
                .isInstanceOf(RuntimeException.class).hasMessage("Неверный логин или пароль");
    }

    @Test
    void login_shouldThrowException_whenPasswordIncorrect(){
        User mockUser = new User();
        mockUser.setEmail("test@example.com");
        mockUser.setPassword("hashedPassword123");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);
        assertThatThrownBy(() -> userService.login("test@example.com", "wrongPassword"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Неверный логин или пароль");
    }
}