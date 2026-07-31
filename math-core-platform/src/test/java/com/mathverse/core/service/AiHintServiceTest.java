package com.mathverse.core.service;

import com.mathverse.core.client.AiGatewayClient;
import com.mathverse.core.dto.GenerateHintRequest;
import com.mathverse.core.entity.*;
import com.mathverse.core.repository.AiHintRepository;
import com.mathverse.core.repository.AttemptRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AiHintServiceTest {

    @Mock
    private AttemptRepository attemptRepository;

    @Mock
    private AiGatewayClient aiGatewayClient;

    @Mock
    private AiHintRepository aiHintRepository;

    @InjectMocks
    private AiHintService aiHintService;

    private Attempt buildAttempt(Long id, Boolean correct, User user, Topic topic) {
        TaskTemplate template = new TaskTemplate();
        template.setTopic(topic);

        Attempt attempt = new Attempt();
        attempt.setId(id);
        attempt.setUser(user);
        attempt.setTaskTemplate(template);
        attempt.setCorrect(correct);
        attempt.setStudentAnswer("wrong" + id);
        attempt.setGeneratedTask("condition" + id);
        return attempt;
    }

    @Test
    void checkAndGenerateHintAsync_shouldGenerateHint_whenExactlyThreeWrongInARow() {
        User user = new User();
        user.setId(1L);
        Topic topic = new Topic();
        topic.setId(2L);

        Attempt current = buildAttempt(3L, false, user, topic);
        Attempt second = buildAttempt(2L, false, user, topic);
        Attempt first = buildAttempt(1L, false, user, topic);

        when(attemptRepository.findTop4ByUser_IdAndTaskTemplate_Topic_IdOrderByTimeAnswerDesc(1L, 2L))
                .thenReturn(List.of(current, second, first));
        when(aiGatewayClient.fetchHint(any(GenerateHintRequest.class))).thenReturn("Подумай ещё раз");

        aiHintService.checkAndGenerateHintAsync(current);

        verify(aiHintRepository).save(any());
    }

    @Test
    void checkAndGenerateHintAsync_shouldNotGenerateHint_whenFourthAttemptAlsoWrong(){
        User user = new User();
        user.setId(1L);
        Topic topic = new Topic();
        topic.setId(2L);

        Attempt current = buildAttempt(3L, false, user, topic);
        Attempt second = buildAttempt(2L, false, user, topic);
        Attempt first = buildAttempt(1L, false, user, topic);
        Attempt fourth = buildAttempt(1L, false, user, topic);


        when(attemptRepository.findTop4ByUser_IdAndTaskTemplate_Topic_IdOrderByTimeAnswerDesc(1L, 2L))
                .thenReturn(List.of(current, second, first, fourth));
        aiHintService.checkAndGenerateHintAsync(current);

        verify(aiHintRepository,never()).save(any());
    }

    @Test
    void checkAndGenerateHintAsync_shouldNotGenerateHint_whenLessThanThreeAttempts(){
        User user = new User();
        user.setId(1L);
        Topic topic = new Topic();
        topic.setId(2L);

        Attempt current = buildAttempt(3L, false, user, topic);
        Attempt second = buildAttempt(2L, false, user, topic);

        when(attemptRepository.findTop4ByUser_IdAndTaskTemplate_Topic_IdOrderByTimeAnswerDesc(1L, 2L))
                .thenReturn(List.of(current, second));
        aiHintService.checkAndGenerateHintAsync(current);

        verify(aiHintRepository,never()).save(any());
    }
}