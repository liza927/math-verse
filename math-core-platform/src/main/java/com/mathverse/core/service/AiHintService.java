package com.mathverse.core.service;

import com.mathverse.core.client.AiGatewayClient;
import com.mathverse.core.dto.GenerateHintRequest;
import com.mathverse.core.entity.AiHint;
import com.mathverse.core.entity.Attempt;
import com.mathverse.core.entity.User;
import com.mathverse.core.repository.AiHintRepository;
import com.mathverse.core.repository.AttemptRepository;
import lombok.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AiHintService {

    private final AttemptRepository attemptRepository;
    private final AiGatewayClient aiGatewayClient;
    private final AiHintRepository aiHintRepository;

    @Async
    public void checkAndGenerateHintAsync(Attempt currentAttempt) {
        User user = currentAttempt.getUser();
        Long topicId = currentAttempt.getTaskTemplate().getTopic().getId();

        List<Attempt> lastFour = attemptRepository.findTop4ByUser_IdAndTaskTemplate_Topic_IdOrderByTimeAnswerDesc(
                user.getId(), topicId
        );

        boolean lastThreeAllWrong = lastFour.size() >= 3
                && lastFour.subList(0, 3).stream().allMatch(a -> Boolean.FALSE.equals(a.getCorrect()));

        boolean fourthWasWrongToo = lastFour.size() == 4 && Boolean.FALSE.equals(lastFour.get(3).getCorrect());

        if (lastThreeAllWrong && !fourthWasWrongToo) {
            List<String> wrongAnswers = lastFour.subList(0, 3).stream()
                    .map(Attempt::getStudentAnswer)
                    .toList();

            GenerateHintRequest hintRequest = new GenerateHintRequest(
                    currentAttempt.getGeneratedTask(),
                    wrongAnswers
            );

            String generatedHint = aiGatewayClient.fetchHint(hintRequest);

            if (generatedHint != null) {
                AiHint aiHint = new AiHint();
                aiHint.setAttempt(currentAttempt);
                aiHint.setHintText(generatedHint);
                aiHint.setCreatedAt(LocalDateTime.now());
                aiHintRepository.save(aiHint);
            }
        }
    }
}
