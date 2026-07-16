package com.mathverse.core.dto;

import com.mathverse.core.entity.Operation;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateTaskTemplateRequest {
    private Long topicId;
    private Operation operation;
    private Integer complexity;
    private String generationParam;
}
