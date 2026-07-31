package com.mathverse.core.generator;

import com.mathverse.core.entity.Operation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class TaskGeneratorFactoryTest {

    @Mock
    private MultiplyMatricesGenerator multiplyMatricesGenerator;

    @Mock
    private TransposeGenerator transposeGenerator;

    @Mock
    private FindDeterminantGenerator findDeterminantGenerator;

    @Mock
    private FindInverseMatrix findInverseMatrix;

    @InjectMocks
    private TaskGeneratorFactory factory;

    @Test
    void getGenerator_shouldReturnCorrectGeneratorForEveryOperation() {
        assertThat(factory.getGenerator(Operation.MULTIPLY_TWO_MATRICES)).isEqualTo(multiplyMatricesGenerator);
        assertThat(factory.getGenerator(Operation.TRANSPOSE)).isEqualTo(transposeGenerator);
        assertThat(factory.getGenerator(Operation.FIND_DETERMINANT)).isEqualTo(findDeterminantGenerator);
        assertThat(factory.getGenerator(Operation.FIND_INVERSE_MATRIX)).isEqualTo(findInverseMatrix);
    }
}
