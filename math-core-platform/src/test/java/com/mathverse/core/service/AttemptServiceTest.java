package com.mathverse.core.service;

import com.mathverse.core.client.AiGatewayClient;
import com.mathverse.core.dto.GenerateHintRequest;
import com.mathverse.core.dto.StartAttemptRequest;
import com.mathverse.core.dto.SubmitAnswerRequest;
import com.mathverse.core.dto.TopicStatsDto;
import com.mathverse.core.entity.AiHint;
import com.mathverse.core.entity.Attempt;
import com.mathverse.core.entity.TaskTemplate;
import com.mathverse.core.entity.Topic;
import com.mathverse.core.entity.User;
import com.mathverse.core.generator.GeneratedTask;
import com.mathverse.core.generator.TaskGenerator;
import com.mathverse.core.generator.TaskGeneratorFactory;
import com.mathverse.core.repository.AiHintRepository;
import com.mathverse.core.repository.AttemptRepository;
import com.mathverse.core.repository.TaskTemplateRepository;
import com.mathverse.core.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AttemptServiceTest {

    @Mock
    private AttemptRepository attemptRepository;

    @Mock
    private TaskTemplateRepository taskTemplateRepository;

    @Mock
    private TaskGeneratorFactory taskGeneratorFactory;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AiHintService aiHintService;

    @InjectMocks
    private AttemptService attemptService;

    private User user;
    private TaskTemplate taskTemplate;
    private Topic topic;
    private Attempt attempt;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        topic = new Topic();
        topic.setId(10L);
        topic.setNameTopic("Матрицы");

        taskTemplate = new TaskTemplate();
        taskTemplate.setId(100L);
        taskTemplate.setComplexity(5);
        taskTemplate.setGenerationParam("{}");
        taskTemplate.setTopic(topic);

        attempt = new Attempt();
        attempt.setId(500L);
        attempt.setUser(user);
        attempt.setTaskTemplate(taskTemplate);
        attempt.setCorrectAnswer("42");
        attempt.setGeneratedTask("Найдите ответ");
    }

    @Test
    void startAttempt_success() {
        StartAttemptRequest request = new StartAttemptRequest(100L);
        TaskGenerator generatorMock = mock(TaskGenerator.class);
        GeneratedTask generatedTask = new GeneratedTask("Найдите ответ", "42");

        when(taskTemplateRepository.findById(100L)).thenReturn(Optional.of(taskTemplate));
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(taskGeneratorFactory.getGenerator(any())).thenReturn(generatorMock);
        when(generatorMock.generate(5, "{}")).thenReturn(generatedTask);
        when(attemptRepository.save(any(Attempt.class))).thenAnswer(i -> i.getArgument(0));

        Attempt result = attemptService.startAttempt(request, "test@example.com");

        assertThat(result).isNotNull();
        assertThat(result.getGeneratedTask()).isEqualTo("Найдите ответ");
        assertThat(result.getCorrectAnswer()).isEqualTo("42");
    }

    @Test
    void submitAnswer_correctAnswer_shouldNotTriggerAiHint() {
        SubmitAnswerRequest request = new SubmitAnswerRequest(500L, "42");
        when(attemptRepository.findById(500L)).thenReturn(Optional.of(attempt));
        when(attemptRepository.save(any(Attempt.class))).thenAnswer(i -> i.getArgument(0));

        Attempt result = attemptService.submitAnswer(request);

        assertThat(result.getCorrect()).isTrue();
        verifyNoInteractions(aiHintService);
    }

    @Test
    void submitAnswer_wrongAnswer_shouldCallAiHintService() {
        SubmitAnswerRequest request = new SubmitAnswerRequest(500L, "99");

        when(attemptRepository.findById(500L)).thenReturn(Optional.of(attempt));
        when(attemptRepository.save(any(Attempt.class))).thenAnswer(i -> i.getArgument(0));

        Attempt result = attemptService.submitAnswer(request);

        assertThat(result.getCorrect()).isFalse();
        verify(aiHintService, times(1)).checkAndGenerateHintAsync(any(Attempt.class));
    }

    @Test
    void getTopicStats_success() {
        attempt.setCorrect(true);
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(attemptRepository.findByUser_IdOrderByTimeAnswerDesc(1L)).thenReturn(List.of(attempt));

        List<TopicStatsDto> stats = attemptService.getTopicStats("test@example.com");

        assertThat(stats).hasSize(1);
        assertThat(stats.get(0).getTopicName()).isEqualTo("Матрицы");
        assertThat(stats.get(0).getCorrectPercentage()).isEqualTo(100.0);
        assertThat(stats.get(0).getTotalAttempts()).isEqualTo(1L);
    }
}
