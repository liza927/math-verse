package com.mathverse.gateway.controller;

import com.mathverse.gateway.dto.GenerateHintRequest;
import com.mathverse.gateway.dto.GenerateHintResponse;
import com.mathverse.gateway.service.GeminiAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiHintController {

    private final GeminiAiService geminiAiService;

    @PostMapping("/generate-hint")
    public GenerateHintResponse generateHint(@RequestBody GenerateHintRequest request) {
        return geminiAiService.generateHint(request);
    }
}