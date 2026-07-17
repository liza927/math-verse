package com.mathverse.core.generator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MultiplyMatricesGeneratorTest {

    @Test
    void generate_shouldReturnValidTask() {
        MultiplyMatricesGenerator multiplyMatricesGenerator = new MultiplyMatricesGenerator();
        GeneratedTask result = multiplyMatricesGenerator.generate(5, "{}");
        assertThat(result).isNotNull();
        assertThat(result.getTaskCondition()).contains("Перемножьте");
        assertThat(result.getCorrectAnswer()).isNotEmpty();
    }
}
