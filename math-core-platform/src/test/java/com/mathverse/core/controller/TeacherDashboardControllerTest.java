package com.mathverse.core.controller;

import com.mathverse.core.dto.TeacherDashboardResponse;
import com.mathverse.core.service.TeacherAnalyticsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TeacherDashboardControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TeacherAnalyticsService teacherAnalyticsService;

    @InjectMocks
    private TeacherDashboardController teacherDashboardController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(teacherDashboardController).build();
    }

    @Test
    void getDashboard_shouldReturn200AndDashboardResponse() throws Exception {
        TeacherDashboardResponse response = new TeacherDashboardResponse();
        when(teacherAnalyticsService.getDashboard()).thenReturn(response);

        mockMvc.perform(get("/api/teacher/dashboard"))
                .andExpect(status().isOk());
    }
}
