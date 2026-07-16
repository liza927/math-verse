package com.mathverse.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class TopicStatsDto {
    private String topicName;
    private long totalAttempts;
    private long correctAttempts;
    private double correctPercentage;
}
