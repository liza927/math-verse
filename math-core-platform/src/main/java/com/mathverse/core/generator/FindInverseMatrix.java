package com.mathverse.core.generator;

import org.springframework.stereotype.Component;

import java.util.Random;

import static com.mathverse.core.generator.MatrixUtils.matrixToString;

@Component
public class FindInverseMatrix implements TaskGenerator {
    @Override
    public GeneratedTask generate(Integer complexity, String generationParam) {
        Random random = new Random();
        int[][] matrix = new int[2][2];
        double[][] matrix2 = new double[2][2];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = random.nextInt(11) - 5;
            }
        }
        int det = matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        if (det == 0) {
            throw new RuntimeException("Обратной матрицы не существует");
        }
        matrix2[0][0] = (double) matrix[1][1] / det;
        matrix2[1][1] = (double) matrix[0][0] / det;
        matrix2[0][1] = (double) -matrix[0][1] / det;
        matrix2[1][0] = (double) -matrix[1][0] / det;
        String task = matrixToString(matrix);
        String correctAnswer = matrixToString(matrix2);
        return new GeneratedTask("Найдите обратную матрицу матрицы А = " + task, correctAnswer);
    }
}
