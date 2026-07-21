package com.mathverse.core.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.mathverse.core.dto.ErrorResponse;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED.value(), LocalDateTime.now(),null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException exception){
        Map<String,String> fieldErrors = new HashMap<>();
        for(FieldError fieldError: exception.getBindingResult().getFieldErrors()){
            fieldErrors.put(fieldError.getField(),fieldError.getDefaultMessage());
        }
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("Ошибка валидации данных");
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setFieldErrors(fieldErrors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyExists(EmailAlreadyExistsException ex)   {
    ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.CONFLICT.value(), LocalDateTime.now(),null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
}
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentials(InvalidCredentialsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED.value(), LocalDateTime.now(),null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex){
        log.error("Необработанное исключение: ", ex);
        ErrorResponse errorResponse = new ErrorResponse("Произошла непредвиденная ошибка. Попробуйте позже.", HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now(),null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
