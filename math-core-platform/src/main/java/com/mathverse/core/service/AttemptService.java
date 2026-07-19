package com.mathverse.core.service;

import com.mathverse.core.dto.StartAttemptRequest;
import com.mathverse.core.dto.SubmitAnswerRequest;
import com.mathverse.core.dto.TopicStatsDto;
import com.mathverse.core.entity.Attempt;
import com.mathverse.core.entity.TaskTemplate;
import com.mathverse.core.entity.User;
import com.mathverse.core.generator.GeneratedTask;
import com.mathverse.core.generator.TaskGenerator;
import com.mathverse.core.generator.TaskGeneratorFactory;
import com.mathverse.core.repository.AttemptRepository;
import com.mathverse.core.repository.TaskTemplateRepository;
import com.mathverse.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttemptService {

    private final AttemptRepository attemptRepository;
    private final TaskTemplateRepository taskTemplateRepository;
    private final TaskGeneratorFactory taskGeneratorFactory;
    private final UserRepository userRepository;

    public Attempt startAttempt(StartAttemptRequest startAttemptRequest, String email){
        Optional<TaskTemplate> foundTemplate =
                taskTemplateRepository.findById(startAttemptRequest.getTaskTemplateId());
        if(!foundTemplate.isPresent()){
            throw new RuntimeException("Шаблон задачи не найден");
        }
        Optional<User> foundUser =
                userRepository.findByEmail(email);
        if (!foundUser.isPresent()) {
            throw new RuntimeException("Email не найден");
        }
        User user = foundUser.get();
        TaskTemplate taskTemplate = foundTemplate.get();
        TaskGenerator taskGenerator = taskGeneratorFactory.getGenerator(taskTemplate.getOperation());
        GeneratedTask generatedTask = taskGenerator.generate(taskTemplate.getComplexity(), taskTemplate.getGenerationParam());
        Attempt attempt = new Attempt();
        attempt.setUser(user);
        attempt.setTaskTemplate(taskTemplate);
        attempt.setGeneratedTask(generatedTask.getTaskCondition());
        attempt.setCorrectAnswer(generatedTask.getCorrectAnswer());
        attempt.setTimeAnswer(LocalDateTime.now());
        return attemptRepository.save(attempt);
    }

    public Attempt submitAnswer(SubmitAnswerRequest request){
        Optional<Attempt> foundAttempt =
                attemptRepository.findById(request.getAttemptId());
        if(!foundAttempt.isPresent()){
            throw new RuntimeException("Попытка не найдена");
        }
        Attempt attempt = foundAttempt.get();
        boolean result = request.getStudentAnswer().equals(attempt.getCorrectAnswer());
        attempt.setStudentAnswer(request.getStudentAnswer());
        attempt.setCorrect(result);
        return attemptRepository.save(attempt);

    }

    public List<TopicStatsDto> getTopicStats(String email){
        Optional<User> foundUser = userRepository.findByEmail(email);
        if (!foundUser.isPresent()) {
            throw new RuntimeException("Email не найден");
        }
        User user = foundUser.get();
        List<Attempt> attempts = attemptRepository.findByUser_IdOrderByTimeAnswerDesc(user.getId());
        Map<String,List<Attempt>> grouped = attempts.stream()
                .collect(Collectors.groupingBy(a->a.getTaskTemplate().getTopic().getNameTopic()));
        List<TopicStatsDto> stats = grouped.entrySet().stream()
                .map(entry->{
                    String topicName = entry.getKey();
                    List<Attempt> topicAttempts = entry.getValue();
                    long total = topicAttempts.size();
                    long correct = topicAttempts.stream()
                            .filter(a->Boolean.TRUE.equals(a.getCorrect()))
                            .count();
                    double percentage = total == 0 ? 0 :(correct*100.0/total);
                    return new TopicStatsDto(topicName,total,correct,percentage);
                })
                .collect(Collectors.toList());
        return stats;
    }

    public List<TopicStatsDto> getOverallTopicStats() {
        List<Attempt> attempts = attemptRepository.findAll();
        Map<String, List<Attempt>> grouped = attempts.stream()
                .collect(Collectors.groupingBy(a -> a.getTaskTemplate().getTopic().getNameTopic()));
        List<TopicStatsDto> stats = grouped.entrySet().stream()
                .map(entry -> {
                    String topicName = entry.getKey();
                    List<Attempt> topicAttempts = entry.getValue();
                    long total = topicAttempts.size();
                    long correct = topicAttempts.stream()
                            .filter(a -> Boolean.TRUE.equals(a.getCorrect()))
                            .count();
                    double percentage = total == 0 ? 0 : (correct * 100.0 / total);
                    return new TopicStatsDto(topicName, total, correct, percentage);
                })
                .collect(Collectors.toList());
        return stats;
    }
}
