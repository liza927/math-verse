package org.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AiTestController {
    @GetMapping("/api/ai/test")
    public String getMessage(){
        return "ИИ-шлюз онлайн!";
    }
}
