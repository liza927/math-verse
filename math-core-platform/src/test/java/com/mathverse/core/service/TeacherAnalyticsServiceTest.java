package com.mathverse.core.service;

import com.mathverse.core.dto.TeacherDashboardResponse;
import com.mathverse.core.dto.TopicDifficultyDto;
import com.mathverse.core.repository.AttemptRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeacherAnalyticsServiceTest {

    @Mock
    private AttemptRepository attemptRepository;

    @InjectMocks
    private TeacherAnalyticsService teacherAnalyticsService;

    @Test
    void getDashboard_shouldReturnTeacherDashboardResponse() {
        TopicDifficultyDto difficultyDto = new TopicDifficultyDto("Матрицы", 10L,5L);

        when(attemptRepository.countDistinctActiveUsersSince(any(LocalDateTime.class))).thenReturn(5L);
        when(attemptRepository.findTopicDifficulty()).thenReturn(List.of(difficultyDto));

        TeacherDashboardResponse response = teacherAnalyticsService.getDashboard();

        assertThat(response).isNotNull();
        assertThat(response.getOnlineStudentsCount()).isEqualTo(5L);
        assertThat(response.getDifficultTopics()).hasSize(1);
        assertThat(response.getDifficultTopics().get(0).getTopicName()).isEqualTo("Матрицы");
    }
}