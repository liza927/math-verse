package com.mathverse.core.generator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FindDeterminantGeneratorTest {

    @Test
    void generate_shouldReturnValidTask() {
        FindDeterminantGenerator generator = new FindDeterminantGenerator();
        GeneratedTask result = generator.generate(5, "{}");

        assertThat(result).isNotNull();
        assertThat(result.getTaskCondition()).contains("Найдите определитель матрицы");
        assertThat(result.getCorrectAnswer()).isNotEmpty();
    }
}
