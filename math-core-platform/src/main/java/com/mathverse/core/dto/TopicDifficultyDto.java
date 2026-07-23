package com.mathverse.core.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TopicDifficultyDto {
    private String topicName;
    private Long totalAttempts;
    private Long incorrectAttempts;

    public double getIncorrectPercentage() {
        if (totalAttempts == 0) {
            return 0.0;
        }
        return (incorrectAttempts * 100.0) / totalAttempts;
    }
}
