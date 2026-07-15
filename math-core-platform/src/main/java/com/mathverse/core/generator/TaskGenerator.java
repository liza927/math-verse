package com.mathverse.core.generator;

public interface TaskGenerator {
    GeneratedTask generate(Integer complexity, String generationParam);
}
