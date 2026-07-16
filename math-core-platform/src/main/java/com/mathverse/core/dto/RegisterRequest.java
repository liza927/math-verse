package com.mathverse.core.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegisterRequest {

    private String email;
    private String password;

}


