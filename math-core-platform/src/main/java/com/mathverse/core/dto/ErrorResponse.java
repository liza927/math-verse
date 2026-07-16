package com.mathverse.core.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ErrorResponse {

    private String message ;
    private int status;
    private LocalDateTime timestamp;
}
