package com.mathverse.core.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mathverse.core.dto.CreateTopicRequest;
import com.mathverse.core.entity.Topic;
import com.mathverse.core.service.TopicService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TopicControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TopicService topicService;

    @InjectMocks
    private TopicController topicController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(topicController).build();
    }

    @Test
    void createTopic_shouldReturn201AndTopic() throws Exception {
        CreateTopicRequest request = new CreateTopicRequest();
        request.setName("Линейная алгебра");

        Topic createdTopic = new Topic();
        createdTopic.setId(1L);
        createdTopic.setNameTopic("Линейная алгебра");

        when(topicService.createTopic(anyString())).thenReturn(createdTopic);

        mockMvc.perform(post("/api/teacher/topics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nameTopic").value("Линейная алгебра"));
    }

    @Test
    void getTopic_shouldReturn200AndTopicList() throws Exception {
        Topic topic = new Topic();
        topic.setId(1L);
        topic.setNameTopic("Матрицы");

        when(topicService.readTopic()).thenReturn(List.of(topic));

        mockMvc.perform(get("/api/topics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nameTopic").value("Матрицы"));
    }
}
