package com.mathverse.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StartAttemptResponse {
    @JsonProperty("attemptId")
    private Long id;
    private String task;
}
