package com.mathverse.core.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SubmitAnswerRequest {
    private Long attemptId;
    private String studentAnswer;
}
