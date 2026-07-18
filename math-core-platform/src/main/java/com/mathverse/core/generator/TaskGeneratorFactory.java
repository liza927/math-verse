package com.mathverse.core.generator;

import com.mathverse.core.entity.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskGeneratorFactory {

    private final MultiplyMatricesGenerator multiplyMatricesGenerator;
    private final TransposeGenerator transposeGenerator;
    private final FindDeterminantGenerator findDeterminantGenerator;
    private final FindInverseMatrix findInverseMatrix;


    public TaskGenerator getGenerator(Operation operation){
        return switch (operation){
            case MULTIPLY_TWO_MATRICES -> multiplyMatricesGenerator;
            case TRANSPOSE -> transposeGenerator;
            case FIND_DETERMINANT -> findDeterminantGenerator;
            case FIND_INVERSE_MATRIX -> findInverseMatrix;
        };
    }
}
