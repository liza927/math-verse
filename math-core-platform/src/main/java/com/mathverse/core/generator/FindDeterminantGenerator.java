package com.mathverse.core.generator;

import org.springframework.stereotype.Component;

import java.util.Random;

import static com.mathverse.core.generator.MatrixUtils.matrixToString;

@Component
public class FindDeterminantGenerator implements TaskGenerator {
    @Override
    public GeneratedTask generate(Integer complexity, String generationParam) {
        Random random = new Random();
        int[][] matrix1 = new int[2][2];
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1.length; j++) {
                matrix1[i][j] = random.nextInt(11) - 5;
            }
        }
        int det = matrix1[0][0]*matrix1[1][1]-matrix1[0][1]*matrix1[1][0];
        String task = matrixToString(matrix1);
        String correctAnswer = String.valueOf(det);
        return new GeneratedTask("Найдите определитель матрицы: А = " + task,correctAnswer);
    }
}
