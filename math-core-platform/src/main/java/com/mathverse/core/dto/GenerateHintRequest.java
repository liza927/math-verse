package com.mathverse.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenerateHintRequest {
    private String taskCondition;
    private List<String> wrongAnswers;
}