package com.mathverse.core.controller;

import com.mathverse.core.dto.TeacherDashboardResponse;
import com.mathverse.core.service.TeacherAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
public class TeacherDashboardController {

    private final TeacherAnalyticsService teacherAnalyticsService;

    @GetMapping("/dashboard")
    public ResponseEntity<TeacherDashboardResponse> getDashboard() {
        TeacherDashboardResponse response = teacherAnalyticsService.getDashboard();
        return ResponseEntity.ok(response);
    }
}