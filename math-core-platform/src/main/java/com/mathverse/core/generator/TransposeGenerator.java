package com.mathverse.core.generator;

import org.springframework.stereotype.Component;

import java.util.Random;

import static com.mathverse.core.generator.MatrixUtils.matrixToString;

@Component
public class TransposeGenerator implements TaskGenerator{

    @Override
    public GeneratedTask generate(Integer complexity, String generationParam) {
        Random random = new Random();
        int[][] matrix= new int [2][2];
        int[][] result = new int[2][2];

        for(int i=0;i< matrix.length;i++){
            for(int j=0;j<matrix.length;j++){
                matrix[i][j] = random.nextInt(11) - 5;
            }
        }

        for(int i=0;i< matrix.length;i++){
            for(int j=0;j<matrix.length;j++){
                result[j][i] = matrix[i][j];
            }
        }
        String task = "Транспонирование матрицы: A = " + matrixToString(matrix);
        String correctAnswer   = matrixToString(result);
        return new GeneratedTask(task,correctAnswer);
    }
}
