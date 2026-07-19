package com.mathverse.core.controller;

import com.mathverse.core.dto.StartAttemptRequest;
import com.mathverse.core.dto.StartAttemptResponse;
import com.mathverse.core.dto.SubmitAnswerRequest;
import com.mathverse.core.dto.TopicStatsDto;
import com.mathverse.core.entity.Attempt;
import com.mathverse.core.entity.Topic;
import com.mathverse.core.service.AttemptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attempts")
@Tag(name = "Попытки решения")
public class AttemptController {

    private final AttemptService attemptService;

    @Operation(summary = "Начало решения задач,генерация условия")
    @PostMapping("/start")
    public StartAttemptResponse startAttempt(@RequestBody StartAttemptRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Attempt attempt = attemptService.startAttempt(request, email);
        return new StartAttemptResponse(attempt.getId(), attempt.getGeneratedTask());
    }

    @Operation(summary = "Отправка ответа на проверку")
    @PostMapping("/submit")
    public Attempt submitAnswer(@RequestBody SubmitAnswerRequest request) {
        return attemptService.submitAnswer(request);
    }

    @Operation(summary = "Получение статистики")
    @GetMapping("/stats")
    public List<TopicStatsDto> getTopicStats() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return attemptService.getTopicStats(email);
    }

    @Operation(summary = "Просмотр всех тем")
    @GetMapping("/teacher/stats")
    public List<TopicStatsDto>  getOverallStats(){
        return attemptService.getOverallTopicStats();
    }
}
