package com.mathverse.core.generator;

import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.assertThat;

public class FindInverseMatrixTest {

    @RepeatedTest(20)
    void generate_shouldReturnValidTaskOrThrowExceptionWhenDetIsZero() {
        FindInverseMatrix generator = new FindInverseMatrix();

        try {
            GeneratedTask result = generator.generate(5, "{}");
            assertThat(result).isNotNull();
            assertThat(result.getTaskCondition()).contains("Найдите обратную матрицу");
            assertThat(result.getCorrectAnswer()).isNotEmpty();
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo("Обратной матрицы не существует");
        }
    }
}
