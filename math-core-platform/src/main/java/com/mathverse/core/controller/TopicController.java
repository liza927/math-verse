package com.mathverse.core.controller;

import com.mathverse.core.dto.CreateTopicRequest;
import com.mathverse.core.entity.Topic;
import com.mathverse.core.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @PostMapping("/teacher/topics")
    public ResponseEntity<Topic> createTopic(@RequestBody CreateTopicRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(topicService.createTopic(request.getName()));
    }

    @GetMapping("/topics")
    public ResponseEntity<List<Topic>> getTopic(){
        return ResponseEntity.status(HttpStatus.OK).body(topicService.readTopic());
    }
}
