package com.mathverse.gateway.service;

import com.mathverse.gateway.dto.GenerateHintRequest;
import com.mathverse.gateway.dto.GenerateHintResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GeminiAiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url:https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent}")
    private String apiUrl;

    private final RestClient restClient = RestClient.builder()
            .requestFactory(ClientHttpRequestFactories.get(
                    ClientHttpRequestFactorySettings.DEFAULTS
                            .withConnectTimeout(Duration.ofSeconds(3))
                            .withReadTimeout(Duration.ofSeconds(15))
            ))
            .build();

    public GenerateHintResponse generateHint(GenerateHintRequest request) {
        String prompt = buildPrompt(request.getTaskCondition(), request.getWrongAnswers());

        // Формируем payload для Gemini API
        Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(Map.of("text", prompt)))
                )
        );

        Map<?, ?> response = restClient.post()
                .uri(apiUrl + "?key=" + apiKey)
                .header("Content-Type", "application/json")
                .body(requestBody)
                .retrieve()
                .body(Map.class);

        String extractedHint = parseGeminiResponse(response);
        return new GenerateHintResponse(extractedHint);
    }

    private String buildPrompt(String condition, List<String> wrongAnswers) {
        return String.format("""
                Ты — дружелюбный и поддерживающий ИИ-репетитор по высшей математике.
                Студент решает следующую задачу: "%s".
                Он сделал уже несколько неверных попыток и ввёл ответы: %s.
                
                Дай ему короткую, направляющую подсказку (наводящий вопрос или намек на формулу/алгоритм),
                но НЕ ДАВАЙ ГОТОВЫЙ ОТВЕТ и решения!
                Отвечай на русском языке, лаконично (не более 2-3 предложений).
                """, condition, String.join(", ", wrongAnswers));
    }

    @SuppressWarnings("unchecked")
    private String parseGeminiResponse(Map<?, ?> response) {
        try {
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
            Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
            List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
            return (String) parts.get(0).get("text");
        } catch (Exception e) {
            return "Обрати внимание на формулу и проверь знаки при вычислениях!";
        }
    }
}