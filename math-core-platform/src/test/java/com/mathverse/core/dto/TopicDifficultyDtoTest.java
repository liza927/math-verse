package com.mathverse.core.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TopicDifficultyDtoTest {

    @Test
    void getIncorrectPercentage_shouldCalculateCorrectly_whenAttemptsExist() {
        TopicDifficultyDto dto = new TopicDifficultyDto("Алгебра", 5L, 2L);

        double result = dto.getIncorrectPercentage();

        assertThat(result).isEqualTo(40.0);
    }

    @Test
    void getIncorrectPercentage_shouldReturnZero_whenNoAttempts() {
        TopicDifficultyDto dto = new TopicDifficultyDto("Алгебра", 0L, 0L);

        double result = dto.getIncorrectPercentage();

        assertThat(result).isEqualTo(0.0);
    }
}