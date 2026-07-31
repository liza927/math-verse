package com.mathverse.core.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mathverse.core.dto.CreateTaskTemplateRequest;
import com.mathverse.core.entity.TaskTemplate;
import com.mathverse.core.service.TaskTemplateService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TaskTemplateControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TaskTemplateService taskTemplateService;

    @InjectMocks
    private TaskTemplateController taskTemplateController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(taskTemplateController).build();
    }

    @Test
    void createTaskTemplate_shouldReturn201AndTemplate() throws Exception {
        CreateTaskTemplateRequest request = new CreateTaskTemplateRequest();
        TaskTemplate template = new TaskTemplate();
        template.setId(1L);

        when(taskTemplateService.createTaskTemplate(any(CreateTaskTemplateRequest.class))).thenReturn(template);

        mockMvc.perform(post("/api/teacher/task-templates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void readTaskTemplate_shouldReturn200AndList() throws Exception {
        TaskTemplate template = new TaskTemplate();
        template.setId(1L);

        when(taskTemplateService.getAllTaskTemplates()).thenReturn(List.of(template));

        mockMvc.perform(get("/api/task-templates"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }
}