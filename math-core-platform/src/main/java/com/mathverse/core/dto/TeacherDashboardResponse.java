package com.mathverse.core.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TeacherDashboardResponse {
    private Long onlineStudentsCount;
    private List<TopicDifficultyDto> difficultTopics;
}
