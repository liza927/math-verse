package com.mathverse.core.generator;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class MatrixUtilsTest {

    @Test
    void matrixToString_shouldFormatCorrectly() {
        int[][] matrix = {{1, 2}, {3, 4}};
        String result = MatrixUtils.matrixToString(matrix);
        assertThat(result).isEqualTo("1, 2;3, 4;");
    }


}