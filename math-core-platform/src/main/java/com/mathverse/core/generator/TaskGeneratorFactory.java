package com.mathverse.core.generator;

import com.mathverse.core.entity.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskGeneratorFactory {

    private final MultiplyMatricesGenerator multiplyMatricesGenerator;

    public TaskGenerator getGenerator(Operation operation){
        return switch (operation){
            case MULTIPLY_TWO_MATRICES -> multiplyMatricesGenerator;
            case FIND_DETERMINANT, FIND_INVERSE_MATRIX, TRANSPOSE
                    -> throw new RuntimeException("Такого задания не существует");
        };
    }
}
