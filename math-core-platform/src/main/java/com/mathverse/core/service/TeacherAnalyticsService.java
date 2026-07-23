package com.mathverse.core.service;

import com.mathverse.core.dto.TeacherDashboardResponse;
import com.mathverse.core.dto.TopicDifficultyDto;
import com.mathverse.core.repository.AttemptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherAnalyticsService {

    private final AttemptRepository attemptRepository;

    public TeacherDashboardResponse getDashboard() {
        LocalDateTime since = LocalDateTime.now().minusMinutes(15);
        long onlineCount = attemptRepository.countDistinctActiveUsersSince(since);
        List<TopicDifficultyDto> difficultTopics = attemptRepository.findTopicDifficulty();

        TeacherDashboardResponse response = new TeacherDashboardResponse();
        response.setOnlineStudentsCount(onlineCount);
        response.setDifficultTopics(difficultTopics);
        return response;
    }
}
