package com.mathverse.core.controller;

import com.mathverse.core.dto.CreateTopicRequest;
import com.mathverse.core.entity.Topic;
import com.mathverse.core.service.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Темы", description = "Управление темами для задач")
public class TopicController {

    private final TopicService topicService;


    @Operation(summary = "Создание новой темы", description = "Создаёт новую тему с ролью TEACHER")
    @PostMapping("/teacher/topics")
    public ResponseEntity<Topic> createTopic(@RequestBody CreateTopicRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(topicService.createTopic(request.getName()));
    }

    @Operation(summary = "Cписок тем", description = "Чтение списка разрешено всем")
    @GetMapping("/topics")
    public ResponseEntity<List<Topic>> getTopic(){
        return ResponseEntity.status(HttpStatus.OK).body(topicService.readTopic());
    }
}
