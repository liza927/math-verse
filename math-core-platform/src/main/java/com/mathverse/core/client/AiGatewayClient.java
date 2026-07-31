package com.mathverse.core.client;

import com.mathverse.core.dto.GenerateHintRequest;
import com.mathverse.core.dto.GenerateHintResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Component
public class AiGatewayClient {

    private final RestClient restClient;

    // Явно указываем Spring использовать этот конструктор для внедрения зависимостей
    @Autowired
    public AiGatewayClient(RestClient.Builder restClientBuilder,
                           @Value("${ai.gateway.url:http://localhost:8081}") String gatewayUrl) {
        ClientHttpRequestFactorySettings settings = ClientHttpRequestFactorySettings.DEFAULTS
                .withConnectTimeout(Duration.ofSeconds(3))
                .withReadTimeout(Duration.ofSeconds(10));

        this.restClient = restClientBuilder
                .baseUrl(gatewayUrl)
                .requestFactory(ClientHttpRequestFactories.get(settings))
                .build();
    }

    // Этот конструктор остаётся только для unit-тестов
    public AiGatewayClient(RestClient restClient) {
        this.restClient = restClient;
    }

    public String fetchHint(GenerateHintRequest request) {
        try {
            GenerateHintResponse response = restClient.post()
                    .uri("/api/ai/generate-hint")
                    .body(request)
                    .retrieve()
                    .body(GenerateHintResponse.class);

            return response != null ? response.getHint() : null;
        } catch (Exception e) {
            return null;
        }
    }
}