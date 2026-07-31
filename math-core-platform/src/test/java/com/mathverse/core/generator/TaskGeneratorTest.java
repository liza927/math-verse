package com.mathverse.core.generator;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TaskGeneratorTest {

    private final FindDeterminantGenerator determinantGenerator = new FindDeterminantGenerator();
    private final MultiplyMatricesGenerator multiplyGenerator = new MultiplyMatricesGenerator();
    private final TransposeGenerator transposeGenerator = new TransposeGenerator();
    private final FindInverseMatrix inverseMatrixGenerator = new FindInverseMatrix();

    @Test
    void findDeterminantGenerator_shouldGenerateTask() {
        GeneratedTask task = determinantGenerator.generate(1, "");
        assertThat(task).isNotNull();
        assertThat(task.getTaskCondition()).startsWith("Найдите определитель матрицы: А = ");
        assertThat(task.getCorrectAnswer()).isNotNull();
    }

    @Test
    void multiplyMatricesGenerator_shouldGenerateTask() {
        GeneratedTask task = multiplyGenerator.generate(1, "");
        assertThat(task).isNotNull();
        assertThat(task.getTaskCondition()).startsWith("Перемножьте матрицы: A = ");
        assertThat(task.getCorrectAnswer()).isNotNull();
    }

    @Test
    void transposeGenerator_shouldGenerateTask() {
        GeneratedTask task = transposeGenerator.generate(1, "");
        assertThat(task).isNotNull();
        assertThat(task.getTaskCondition()).startsWith("Транспонирование матрицы: A = ");
        assertThat(task.getCorrectAnswer()).isNotNull();
    }

    @RepeatedTest(20)
    void findInverseMatrix_shouldGenerateTaskOrThrowExceptionWhenDetIsZero() {
        try {
            GeneratedTask task = inverseMatrixGenerator.generate(1, "");
            assertThat(task).isNotNull();
            assertThat(task.getTaskCondition()).startsWith("Найдите обратную матрицу матрицы А = ");
            assertThat(task.getCorrectAnswer()).isNotNull();
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo("Обратной матрицы не существует");
        }
    }
}