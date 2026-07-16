package com.mathverse.core.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginRequest {

    private String email;
    @ToString.Exclude
    private String password;
}
