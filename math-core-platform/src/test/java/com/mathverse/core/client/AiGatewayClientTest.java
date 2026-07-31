package com.mathverse.core.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mathverse.core.dto.GenerateHintRequest;
import com.mathverse.core.dto.GenerateHintResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

class AiGatewayClientTest {

    private AiGatewayClient aiGatewayClient;
    private MockRestServiceServer mockServer;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        RestClient.Builder builder = RestClient.builder().baseUrl("http://localhost:8081");
        mockServer = MockRestServiceServer.bindTo(builder).build();

        // Передаем собранный RestClient с привязанным мок-сервером
        aiGatewayClient = new AiGatewayClient(builder.build());
    }

    @Test
    @DisplayName("Успешное получение подсказки от AI Gateway")
    void fetchHint_Success() throws Exception {
        GenerateHintRequest request = new GenerateHintRequest("2x + 5 = 9", List.of("x = 1"));
        GenerateHintResponse mockResponse = new GenerateHintResponse("Попробуйте перенести 5 в правую часть.");

        mockServer.expect(requestTo("http://localhost:8081/api/ai/generate-hint"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andRespond(withSuccess(objectMapper.writeValueAsString(mockResponse), MediaType.APPLICATION_JSON));

        String hint = aiGatewayClient.fetchHint(request);

        assertNotNull(hint);
        assertEquals("Попробуйте перенести 5 в правую часть.", hint);
        mockServer.verify();
    }

    @Test
    @DisplayName("Обработка ошибки при недоступности AI Gateway (Returns Null)")
    void fetchHint_ServerDown_ReturnsNull() {
        GenerateHintRequest request = new GenerateHintRequest("2x + 5 = 9", List.of("x = 1"));

        mockServer.expect(requestTo("http://localhost:8081/api/ai/generate-hint"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withServerError());

        String hint = aiGatewayClient.fetchHint(request);

        assertNull(hint);
        mockServer.verify();
    }
}
