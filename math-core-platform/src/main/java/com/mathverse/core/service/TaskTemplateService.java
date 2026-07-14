package com.mathverse.core.service;

import com.mathverse.core.dto.CreateTaskTemplateRequest;
import com.mathverse.core.entity.TaskTemplate;
import com.mathverse.core.entity.Topic;
import com.mathverse.core.repository.TaskTemplateRepository;
import com.mathverse.core.repository.TopicRepository;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskTemplateService {

    private final TaskTemplateRepository taskTemplateRepository;
    private final TopicRepository topicRepository;

    public TaskTemplate createTaskTemplate(CreateTaskTemplateRequest  request){
        Optional<Topic> foundTopic = topicRepository.findById(request.getTopicId());
        if (!foundTopic.isPresent()) {
            throw new RuntimeException("Такой темы не существует");
        }
        Topic topic = foundTopic.get();
        TaskTemplate taskTemplate = new TaskTemplate();
        taskTemplate.setTopic(topic);
        taskTemplate.setOperation(request.getOperation());
        taskTemplate.setComplexity(request.getComplexity());
        taskTemplate.setGenerationParam(request.getGenerationParam());
        return taskTemplateRepository.save(taskTemplate);
    }

    public List<TaskTemplate> getAllTaskTemplates(){
        return taskTemplateRepository.findAll();
    }
}
