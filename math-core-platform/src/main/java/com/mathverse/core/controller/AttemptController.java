package com.mathverse.core.controller;

import com.mathverse.core.dto.StartAttemptRequest;
import com.mathverse.core.dto.StartAttemptResponse;
import com.mathverse.core.dto.SubmitAnswerRequest;
import com.mathverse.core.dto.TopicStatsDto;
import com.mathverse.core.entity.Attempt;
import com.mathverse.core.service.AttemptService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attempts")
public class AttemptController {

    private final AttemptService attemptService;

    @PostMapping("/start")
    public StartAttemptResponse startAttempt(@RequestBody StartAttemptRequest request){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Attempt attempt = attemptService.startAttempt(request,email);
        return new StartAttemptResponse(attempt.getId(),attempt.getGeneratedTask());
    }

    @PostMapping("/submit")
    public Attempt submitAnswer(@RequestBody SubmitAnswerRequest request){
        return attemptService.submitAnswer(request);
    }

    @GetMapping("/stats")
    public List<TopicStatsDto> getTopicStats(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return attemptService.getTopicStats(email);
    }
}
