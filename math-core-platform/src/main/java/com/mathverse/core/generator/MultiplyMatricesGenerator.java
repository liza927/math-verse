package com.mathverse.core.generator;

import org.springframework.stereotype.Component;
import java.util.Random;

import static com.mathverse.core.generator.MatrixUtils.matrixToString;

@Component
public class MultiplyMatricesGenerator implements TaskGenerator {
    @Override
    public GeneratedTask generate(Integer complexity, String generationParam) {
        Random random = new Random();
        int[][] matrix1 = new int[2][2];
        int[][] matrix2 = new int[2][2];
        int[][] result = new int[2][2];

        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1.length; j++) {
                matrix1[i][j] = random.nextInt(11) - 5;
            }
        }

        for (int i = 0; i < matrix2.length; i++) {
            for (int j = 0; j < matrix2.length; j++) {
                matrix2[i][j] = random.nextInt(11) - 5;
            }
        }

        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result.length; j++) {
                int sum = 0;
                for (int k = 0; k < result.length; k++) {
                    sum += matrix1[i][k] * matrix2[k][j];
                }
                result[i][j] = sum;
            }
        }

        String task = "Перемножьте матрицы: A = " + matrixToString(matrix1)
                + " и B = " + matrixToString(matrix2);
        String correctAnswer   = matrixToString(result);

        return new GeneratedTask(task,correctAnswer);

    }
}
