package com.mathverse.core.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mathverse.core.dto.StartAttemptRequest;
import com.mathverse.core.dto.SubmitAnswerRequest;
import com.mathverse.core.dto.TopicStatsDto;
import com.mathverse.core.entity.Attempt;
import com.mathverse.core.service.AttemptService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AttemptControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AttemptService attemptService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AttemptController attemptController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(attemptController).build();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    private void mockUserEmail(String email) {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(email);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void startAttempt_shouldReturnStartAttemptResponse() throws Exception {
        mockUserEmail("student@mathverse.com");

        StartAttemptRequest request = new StartAttemptRequest();
        Attempt attempt = new Attempt();
        attempt.setId(10L);
        attempt.setGeneratedTask("2 + 2 = ?");

        when(attemptService.startAttempt(any(StartAttemptRequest.class), eq("student@mathverse.com")))
                .thenReturn(attempt);

        mockMvc.perform(post("/api/attempts/start")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.attemptId").value(10)) // Из-за @JsonProperty("attemptId")
                .andExpect(jsonPath("$.task").value("2 + 2 = ?")); // Поле называется task
    }

    @Test
    void submitAnswer_shouldReturnAttempt() throws Exception {
        SubmitAnswerRequest request = new SubmitAnswerRequest();
        Attempt attempt = new Attempt();
        attempt.setId(10L);

        when(attemptService.submitAnswer(any(SubmitAnswerRequest.class))).thenReturn(attempt);

        mockMvc.perform(post("/api/attempts/submit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10));
    }

    @Test
    void getTopicStats_shouldReturnTopicStatsList() throws Exception {
        mockUserEmail("student@mathverse.com");

        TopicStatsDto statsDto = new TopicStatsDto("Матрицы", 10, 8, 80.0);
        when(attemptService.getTopicStats("student@mathverse.com")).thenReturn(List.of(statsDto));

        mockMvc.perform(get("/api/attempts/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }
}
