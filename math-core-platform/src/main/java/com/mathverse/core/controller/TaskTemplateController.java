package com.mathverse.core.controller;

import com.mathverse.core.dto.CreateTaskTemplateRequest;
import com.mathverse.core.entity.TaskTemplate;
import com.mathverse.core.service.TaskTemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Шаблон задач", description = "Управление шаблонами задач")
public class TaskTemplateController {

    private final TaskTemplateService taskTemplateService;

    @Operation(summary = "Создание шаблона задач")
    @PostMapping("/teacher/task-templates")
    public ResponseEntity<TaskTemplate> createTaskTemplate(@RequestBody CreateTaskTemplateRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(taskTemplateService.createTaskTemplate(request));
    }

    @Operation(summary = "Список шаблонов задач")
    @GetMapping("/task-templates")
    public ResponseEntity<List<TaskTemplate>> readTaskTemplate(){
        return ResponseEntity.status(HttpStatus.OK).body(taskTemplateService.getAllTaskTemplates());
    }
}
