package com.mathverse.core.service;

import com.mathverse.core.dto.StartAttemptRequest;
import com.mathverse.core.entity.*;
import com.mathverse.core.generator.GeneratedTask;
import com.mathverse.core.generator.TaskGenerator;
import com.mathverse.core.generator.TaskGeneratorFactory;
import com.mathverse.core.repository.AttemptRepository;
import com.mathverse.core.repository.TaskTemplateRepository;
import com.mathverse.core.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AttemptServiceTest {

    @InjectMocks
    private AttemptService attemptService;

    @Mock
    private TaskGenerator taskGenerator;

    @Mock
    private TaskTemplateRepository taskTemplateRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AttemptRepository attemptRepository;

    @Mock
    private TaskGeneratorFactory taskGeneratorFactory;


    @Test
    void successStart(){
        when(taskGeneratorFactory.getGenerator(Operation.FIND_DETERMINANT)).thenReturn(taskGenerator);
        when(taskGenerator.generate(anyInt(), anyString())).thenReturn(new GeneratedTask("условие", "ответ"));
        when(attemptRepository.save(any(Attempt.class))).thenAnswer(invocation -> invocation.getArgument(0));
        TaskTemplate taskTemplate = new TaskTemplate();
        taskTemplate.setComplexity(1);
        taskTemplate.setGenerationParam("1");
        taskTemplate.setOperation(Operation.FIND_DETERMINANT);
        User user = new User();
        user.setId(2L);
        user.setRole(Role.STUDENT);
        user.setPassword("qwerty");
        user.setEmail("test@example.com");
        when(taskTemplateRepository.findById(1L)).thenReturn(Optional.of(taskTemplate));
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        StartAttemptRequest request = new StartAttemptRequest(1L);
        Attempt result = attemptService.startAttempt(request,"test@example.com");
        assertThat(result).isNotNull();
        assertThat(result.getGeneratedTask()).isEqualTo("условие");
        assertThat(result.getCorrectAnswer()).isEqualTo("ответ");
    }

}
