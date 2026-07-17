package com.mathverse.core.generator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TransposeGeneratorTest {

    @Test
    void generate_shouldReturnValidTask() {
        TransposeGenerator transposeGenerator = new TransposeGenerator();
        GeneratedTask result = transposeGenerator.generate(5,"{}");
        assertThat(result).isNotNull();
        assertThat(result.getTaskCondition()).contains("Транспонирование матрицы");
        assertThat(result.getCorrectAnswer()).isNotEmpty();
    }
}
