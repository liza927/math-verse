package com.mathverse.core.generator;

public class MatrixUtils {

    public static String matrixToString(int[][] matrix) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                stringBuilder.append(matrix[i][j]);
                if (j < matrix[i].length - 1) {
                    stringBuilder.append(", ");
                }
            }
            stringBuilder.append(";");
        }
        return stringBuilder.toString();
    }

    public static String matrixToString(double[][] matrix) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                stringBuilder.append(String.format("%.2f", matrix[i][j]));
                if (j < matrix[i].length - 1) {
                    stringBuilder.append(", ");
                }
            }
            stringBuilder.append(";");
        }
        return stringBuilder.toString();
    }
}