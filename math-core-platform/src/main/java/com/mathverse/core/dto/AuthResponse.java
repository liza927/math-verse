package com.mathverse.core.dto;

import com.mathverse.core.entity.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthResponse {

    private String token;
    private Role role;
}
