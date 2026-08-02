package com.mathverse.core.exception;

import com.mathverse.core.dto.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleEmailAlreadyExists_shouldReturn409_withMessageFromException() {
        EmailAlreadyExistsException ex = new EmailAlreadyExistsException("Email найден!");

        ResponseEntity<ErrorResponse> response = handler.handleEmailAlreadyExists(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody().getMessage()).isEqualTo("Email найден!");
        assertThat(response.getBody().getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
    }

    @Test
    void handleInvalidCredentials() {
        InvalidCredentialsException exception = new InvalidCredentialsException("Неверный логин или пароль");
        ResponseEntity<ErrorResponse> response = handler.handleInvalidCredentials((exception));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody().getMessage()).isEqualTo("Неверный логин или пароль");
        assertThat(response.getBody().getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void handleException_shouldReturn500_withGenericMessage_notLeakingInternalDetails() {
        Exception exception = new Exception("some internal detail that should not leak");

        ResponseEntity<ErrorResponse> response = handler.handleException(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody().getMessage()).isEqualTo("Произошла непредвиденная ошибка. Попробуйте позже.");
        assertThat(response.getBody().getMessage()).isNotEqualTo(exception.getMessage());
    }

    @Test
    void handleRuntimeException() {
        RuntimeException exception = new RuntimeException("Что-то пошло не так");
        ResponseEntity<ErrorResponse> response = handler.handleRuntimeException((exception));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(response.getBody().getMessage()).isEqualTo("Что-то пошло не так");
        assertThat(response.getBody().getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    void handleMethodArgumentNotValid_shouldReturn400_withFieldErrorsMap() {
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("registerRequest", "email", "не должно быть пустым");

        when(exception.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));

        ResponseEntity<ErrorResponse> response = handler.handleMethodArgumentNotValid(exception);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getFieldErrors()).containsEntry("email", "не должно быть пустым");
    }

    @Test
    void handleTaskTemplateNotFound_shouldReturn404_withMessageFromException() {
        TaskTemplateNotFoundException ex = new TaskTemplateNotFoundException("Шаблон задачи не найден");

        ResponseEntity<ErrorResponse> response = handler.handleTaskTemplateNotFound(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody().getMessage()).isEqualTo("Шаблон задачи не найден");
        assertThat(response.getBody().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void handleUserNotFound_shouldReturn404_withMessageFromException() {
        UserNotFoundException ex = new UserNotFoundException("Email не найден");

        ResponseEntity<ErrorResponse> response = handler.handleUserNotFound(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody().getMessage()).isEqualTo("Email не найден");
        assertThat(response.getBody().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void handleAttemptNotFound_shouldReturn404_withMessageFromException() {
        AttemptNotFoundException ex = new AttemptNotFoundException("Попытка не найдена");

        ResponseEntity<ErrorResponse> response = handler.handleAttemptNotFound(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody().getMessage()).isEqualTo("Попытка не найдена");
        assertThat(response.getBody().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}
