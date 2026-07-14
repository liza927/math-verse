package com.mathverse.core.dto;

import com.mathverse.core.entity.Operation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTaskTemplateRequest {
    private Long topicId;
    private Operation operation;
    private Integer complexity;
    private String generationParam;
}
