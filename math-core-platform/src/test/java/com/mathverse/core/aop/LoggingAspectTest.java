package com.mathverse.core.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoggingAspectTest {

    @Mock
    private ProceedingJoinPoint joinPoint;

    @Mock
    private Signature signature;

    private final LoggingAspect loggingAspect = new LoggingAspect();

    @Test
    void logMethod_shouldProceedAndReturnResult() throws Throwable {
        when(joinPoint.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn("someControllerMethod");
        when(joinPoint.getArgs()).thenReturn(new Object[]{"arg1", 42});
        when(joinPoint.proceed()).thenReturn("expectedResult");

        Object result = loggingAspect.logMethod(joinPoint);

        assertThat(result).isEqualTo("expectedResult");
        verify(joinPoint).proceed();
    }
}
