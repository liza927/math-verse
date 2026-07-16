package com.mathverse.core.generator;

import com.mathverse.core.entity.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskGeneratorFactory {

    private final MultiplyMatricesGenerator multiplyMatricesGenerator;
    private final TransposeGenerator transposeGenerator;


    public TaskGenerator getGenerator(Operation operation){
        return switch (operation){
            case MULTIPLY_TWO_MATRICES -> multiplyMatricesGenerator;
            case TRANSPOSE -> transposeGenerator;
            case FIND_DETERMINANT, FIND_INVERSE_MATRIX
                    -> throw new RuntimeException("Такого задания не существует");
        };
    }
}
