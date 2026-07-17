package com.mathverse.core.service;

import com.mathverse.core.dto.CreateTaskTemplateRequest;
import com.mathverse.core.entity.Operation;
import com.mathverse.core.entity.TaskTemplate;
import com.mathverse.core.entity.Topic;
import com.mathverse.core.repository.TaskTemplateRepository;
import com.mathverse.core.repository.TopicRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class TaskTemplateServiceTest {

    @InjectMocks
    private TaskTemplateService taskTemplateService;

    @Mock
    private TaskTemplateRepository taskTemplateRepository;

    @Mock
    private TopicRepository topicRepository;

    @Test
    void createTaskTemplate_shouldSaveNewTemplate_whenTopicExists(){
        when(topicRepository.findById(1L)).thenReturn(Optional.of(new Topic()));
        when(taskTemplateRepository.save(any(TaskTemplate.class))).thenAnswer(invocation -> invocation.getArgument(0));
        CreateTaskTemplateRequest createTaskTemplateRequest = new CreateTaskTemplateRequest();
        createTaskTemplateRequest.setTopicId(1L);
        createTaskTemplateRequest.setOperation(Operation.MULTIPLY_TWO_MATRICES);
        createTaskTemplateRequest.setComplexity(5);
        createTaskTemplateRequest.setGenerationParam("{}");
        TaskTemplate taskTemplate = taskTemplateService.createTaskTemplate(createTaskTemplateRequest);
        assertThat(taskTemplate.getTopic()).isNotNull();
    }

    @Test
    void createTaskTemplate_shouldThrowException_whenTopicNotFound(){
        when(topicRepository.findById(1L)).thenReturn(Optional.empty());
        CreateTaskTemplateRequest createTaskTemplateRequest = new CreateTaskTemplateRequest();
        createTaskTemplateRequest.setTopicId(1L);
        createTaskTemplateRequest.setOperation(Operation.MULTIPLY_TWO_MATRICES);
        createTaskTemplateRequest.setComplexity(5);
        createTaskTemplateRequest.setGenerationParam("{}");
        assertThatThrownBy(() -> taskTemplateService.createTaskTemplate(createTaskTemplateRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Такой темы не существует");
    }
}
