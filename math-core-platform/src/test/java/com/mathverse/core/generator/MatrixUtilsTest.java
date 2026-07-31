package com.mathverse.core.generator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MatrixUtilsTest {

    @Test
    void matrixToString_intMatrix_shouldFormatCorrectly() {
        int[][] matrix = {
                {1, 2},
                {3, 4}
        };

        String result = MatrixUtils.matrixToString(matrix);
        assertThat(result).isEqualTo("1, 2;3, 4;");
    }

    @Test
    void matrixToString_doubleMatrix_shouldFormatCorrectly() {
        double[][] matrix = {
                {1.234, 2.5},
                {-0.5, 4.0}
        };

        String result = MatrixUtils.matrixToString(matrix);
        assertThat(result).contains(";");
        assertThat(result).endsWith(";");
    }
}
